package com.squareup.hello;

import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

/**
 * Created by zc on 2017/12/29.
 */

public class TextThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(@NonNull Runnable r) {
        return new Thread(r);
    }

}
