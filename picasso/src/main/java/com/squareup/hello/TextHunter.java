package com.squareup.hello;

import android.os.Handler;

import java.util.concurrent.Future;

/**
 * Created by zc on 2017/12/29.
 */

public class TextHunter implements Runnable{
    SectorEntity sectorEntity;
    Future<?> future;
    Handler mHandler;

    public TextHunter(SectorEntity sectorEntity, Handler mHandler) {
        this.sectorEntity = sectorEntity;
        this.mHandler = mHandler;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }

    }

    public void setSectorEntity(SectorEntity sectorEntity) {
        this.sectorEntity = sectorEntity;
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }

    boolean cancel() {
        return sectorEntity == null && future.cancel(false);
    }
}
