package com.caitou.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.caitou.aidlservice.aidl.IPerson;

/**
 * @className:
 * @classDescription:
 * @Author: Guangzhao Cai
 * @createTime: 2016-08-17.
 */
public class AidlServer extends Service {
    private String TAG = "AidlServer";

    private IPerson.Stub stub = new IPerson.Stub() {
        @Override
        public void hello() throws RemoteException {
            Log.d(TAG, "hello: is called!");
        }

        @Override
        public String greet(String strData) throws RemoteException {
            return "hello " + strData;
        }

        @Override
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: is called!");
        return stub;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: is called!");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: is called!");
    }
}
