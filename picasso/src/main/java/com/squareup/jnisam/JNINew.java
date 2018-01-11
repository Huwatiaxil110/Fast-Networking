package com.squareup.jnisam;

/**
 * Created by Administrator on 2018/1/11.
 */

public class JNINew {

    static {
        System.loadLibrary("test");
    }

    public native String getTian();
}
