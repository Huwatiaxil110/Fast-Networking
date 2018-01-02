package com.squareup.hello;

import android.os.Handler;
import android.os.Message;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zc on 2017/12/29.
 */

public class TextHunter implements Runnable{
    private static final AtomicInteger SEQUENCE_GENERATOR = new AtomicInteger();
    SectorEntity sectorEntity;
    Future<?> future;
    Handler mHandler;
    int sequence;

    public TextHunter(SectorEntity sectorEntity, Handler mHandler) {
        this.sequence = SEQUENCE_GENERATOR.incrementAndGet();
        this.sectorEntity = sectorEntity;
        this.mHandler = mHandler;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            
        }

        if(sectorEntity == null){
            sendMSG(TextConst.MSG_FAILED);
        }else{
            sendMSG(TextConst.MSG_SUCCEED);
        }
    }

    private void sendMSG(int what){
        Message message = new Message();
        message.obj = sectorEntity;
        message.what = what;
        mHandler.sendMessage(message);
    }

    public void setSectorEntity(SectorEntity sectorEntity) {
        this.sectorEntity = sectorEntity;
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }

    boolean cancel() {
        return sectorEntity == null && future.cancel(true);
    }
}
