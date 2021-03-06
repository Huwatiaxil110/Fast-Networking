package com.squareup.hello;

import android.text.TextUtils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by zc on 2017/12/29.
 */

public class SectorEntity<T> {
    private static final StringBuilder KEY_BUILDER = new StringBuilder();
    long enterpriseID;
    long sectorID;
    WeakReference<T> target;

    String key;

    public SectorEntity(long enterpriseID, long sectorID) {
        this.enterpriseID = enterpriseID;
        this.sectorID = sectorID;

        this.key = createKey();
    }

    public long getEnterpriseID() {
        return enterpriseID;
    }

    public long getSectorID() {
        return sectorID;
    }

    public boolean isSame(SectorEntity entity){
        if(enterpriseID == entity.getEnterpriseID() && sectorID == entity.getSectorID()){
            return true;
        }else{
            return false;
        }
    }
    protected String createKey() {
        String result = KEY_BUILDER.append("EID: ").append(enterpriseID).append("; SID: " + sectorID).toString();
        KEY_BUILDER.setLength(0);

        return result;
    }

    public String getKey() {
        if(TextUtils.isEmpty(key)){
            key = createKey();
        }
        return key;
    }

    public WeakReference<T> getTarget() {
        return target;
    }

    public void setTarget(WeakReference<T> target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "SectorEntity{" +
                "enterpriseID=" + enterpriseID +
                ", sectorID=" + sectorID +
                '}';
    }
}
