// IPerson.aidl
package com.caitou.aidlservice.aidl;

// Declare any non-default types here with import statements

interface IPerson {
    void hello();

    String greet(String strData);

    int add(int a, int b);
}
