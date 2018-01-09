package com.squareup.jnisam;

/**
 * Created by zc on 2018/1/9.
 */

public class JNITest {
    static{
        System.loadLibrary("test");
    }

    public native String getHello();
}
