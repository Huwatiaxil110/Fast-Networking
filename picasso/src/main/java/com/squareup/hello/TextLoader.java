package com.squareup.hello;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.Future;

/**
 * Created by zc on 2017/12/29.
 */

public class TextLoader {
    private static final String TAG = TextLoader.class.getSimpleName();
    private static final int DEFAULT_COUNT = 0;
    private static volatile TextLoader mInstance;
    TextExecutor mTextExecutor = new TextExecutor();

    protected static final Handler HANDLER = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TextConst.MSG_SUCCEED:
                    SectorEntity entity = (SectorEntity)msg.obj;
                    TextView tvShow = (TextView) entity.getTarget().get();
                    if (tvShow != null) {
                        tvShow.setText(entity.toString());
                        Log.e(TAG, entity.toString());

                        TextLoaderUtil.getCountMap().put(entity.getKey(), (int) entity.getEnterpriseID());
                    } else {
                        Log.e(TAG, "tvShow == null");
                    }

                    TextLoaderUtil.getTextHunterMap().remove(entity.getKey());
                    break;
                case TextConst.MSG_FAILED:
                    Log.e(TAG, "Const.MSG_FAILED");
                    break;
                default:
                    throw new AssertionError("Unknown handler message received: " + msg.what);
            }
        }
    };

    public static TextLoader getInstance(){
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
        entity.setTarget(new WeakReference(tvTarget));

        if(!isEnterpriseAndSector(entity)){     //是否是企业部门
            tvTarget.setText(DEFAULT_COUNT+"");
            return;
        }

        String key = entity.getKey();  //更具企业部门创建Key
        HashMap<String, Integer> countMap = TextLoaderUtil.getCountMap();
        if(countMap.containsKey(key)){
            tvTarget.setText(TextLoaderUtil.getCountByKey(key)+"");

            SectorEntity tempEntity = TextLoaderUtil.getSectorMap().get(tvTarget);
            if (tempEntity != null) {
                if (!tempEntity.isSame(entity)) {     //目前这个企业部门是否和上次设置的相同
                    cancelExist(tempEntity);
                }
            }
            return;
        }

        HashMap<Object, SectorEntity> sectorMap = TextLoaderUtil.getSectorMap();
        if(tvTarget != null){
            SectorEntity tempEntity = sectorMap.get(tvTarget);
            if(tempEntity != null){
                if(!tempEntity.isSame(entity)){     //目前这个企业部门是否和上次设置的相同
                    cancelExist(tempEntity);
                }
            }
            sectorMap.put(tvTarget, entity);
        }

        if(mTextExecutor.isShutdown()){
            Log.e(TAG, "mTextExecutor.isShutdown() == true");
            tvTarget.setText(DEFAULT_COUNT+"");
            return;
        }

        HashMap<String, TextHunter> hunterMap = TextLoaderUtil.getTextHunterMap();  //是否有任务
        TextHunter hunter = hunterMap.get(key);
        if (hunter != null) {
            hunter.setSectorEntity(entity);
        }else{
            hunter = new TextHunter(entity, HANDLER);
        }

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
                Log.e(TAG, "hunter.cancel() == true" + "; 取消Entity：" + entity.toString());
                hunterMap.remove(key);
            }else{
                Log.e(TAG, "hunter.cancel() == false");
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


































