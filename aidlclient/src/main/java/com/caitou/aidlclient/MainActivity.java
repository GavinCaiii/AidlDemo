package com.caitou.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.caitou.aidlservice.aidl.IPerson;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    private Button bindBtn;
    private Button greetBtn;
    private Button addBtn;
    private Button unbindBtn;

    private IPerson person;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            person = IPerson.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        bindBtn = (Button) findViewById(R.id.bind);
        greetBtn = (Button) findViewById(R.id.greet);
        addBtn = (Button) findViewById(R.id.sum);
        unbindBtn = (Button) findViewById(R.id.unbind);

        bindBtn.setOnClickListener(this);
        greetBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        unbindBtn.setOnClickListener(this);

        greetBtn.setClickable(false);
        addBtn.setClickable(false);
        unbindBtn.setClickable(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind:
                // 5.0之后只能显式启动服务
                Intent intent = new Intent();
                intent.setAction("android.intent.action.AIDLSERVICE");
                intent.setPackage("com.caitou.aidlservice");
                if (bindService(intent, connection, Context.BIND_AUTO_CREATE)) {
                    Toast.makeText(MainActivity.this, "bind service success!", Toast.LENGTH_SHORT).show();
                    bindBtn.setClickable(false);
                    greetBtn.setClickable(true);
                    addBtn.setClickable(true);
                    unbindBtn.setClickable(true);
                } else {
                    Toast.makeText(MainActivity.this, "bind service failed!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.greet:

                try {
                    person.hello();
                    String str = person.greet("caitou");
                    Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.sum:
                try {
                    int sum = person.add(1, 2);
                    Toast.makeText(MainActivity.this, "1 + 2 = " + sum, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.unbind:
                unbindService(connection);

                bindBtn.setClickable(true);
                greetBtn.setClickable(false);
                addBtn.setClickable(false);
                unbindBtn.setClickable(false);
                break;
        }
    }
}
