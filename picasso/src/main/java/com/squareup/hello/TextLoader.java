package com.squareup.hello;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.concurrent.Future;

/**
 * Created by zc on 2017/12/29.
 */

public class TextLoader {
    private static final String TAG = TextLoader.class.getSimpleName();
    private static final int MSG_SUCCEED = 0x101;
    private static final int MSG_FAILED = 0x102;
    private static final int DEFAULT_COUNT = 0;
    private static volatile TextLoader mInstance;
    TextExecutor mTextExecutor = new TextExecutor();

    protected static final Handler HANDLER = new Handler(Looper.getMainLooper()) {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCEED:

                    break;
                case MSG_FAILED:

                    break;
                default:
                    throw new AssertionError("Unknown handler message received: " + msg.what);
            }
        }
    };

    public TextLoader getInstance(){
        if(mInstance == null){
            synchronized (TextLoader.class){
                if(mInstance == null){
                    mInstance = new TextLoader();
                }
            }
        }

        return mInstance;
    }

    public void load(long enterpriseID, long sectorID, TextView tvTarget){
        SectorEntity entity = new SectorEntity(enterpriseID, sectorID);

        if(!isEnterpriseAndSector(entity)){     //是否是企业部门
            tvTarget.setText(DEFAULT_COUNT+"");
            return;
        }

        String key = entity.getKey();  //更具企业部门创建Key
        HashMap<String, Integer> countMap = TextLoaderUtil.getCountMap();
        if(countMap.containsKey(key)){
            tvTarget.setText(TextLoaderUtil.getCountByKey(key)+"");
            return;
        }

        HashMap<Object, SectorEntity> sectorMap = TextLoaderUtil.getSectorMap();
        if(tvTarget != null && !sectorMap.get(tvTarget).isSame(entity)){    //目前这个企业部门是否和上次设置的相同
            cancelExist(entity);
            sectorMap.put(tvTarget, entity);
        }

        HashMap<String, TextHunter> hunterMap = TextLoaderUtil.getTextHunterMap();  //是否有任务
        TextHunter hunter = hunterMap.get(key);
        if (hunter != null) {
            hunter.setSectorEntity(entity);
            tvTarget.setText(DEFAULT_COUNT+"");
            return;
        }

        if(mTextExecutor.isShutdown()){
            Log.e(TAG, "mTextExecutor.isShutdown() == true");
            tvTarget.setText(DEFAULT_COUNT+"");
            return;
        }

        hunter = new TextHunter(entity);
        Future<?> future = mTextExecutor.submit(hunter);
        hunter.setFuture(future);
        hunterMap.put(key, hunter);
    }

    private void cancelExist(SectorEntity entity){
        String key = entity.getKey();
        HashMap<String, TextHunter> hunterMap = TextLoaderUtil.getTextHunterMap();
        TextHunter hunter = hunterMap.get(key);
        if (hunter != null) {
            hunter.setSectorEntity(null);
            if (hunter.cancel()) {
                hunterMap.remove(key);
            }
        }
    }

    private boolean isEnterpriseAndSector(SectorEntity entity){
        if(entity.getEnterpriseID() == 0 || entity.getSectorID() == 0){
            Log.e(TAG, "Error: enterpriseID == 0 || sectorID == 0");
            return false;
        }

        return true;
    }

}


































