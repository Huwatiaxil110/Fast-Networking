package com.squareup.hello;

import android.os.Looper;

import java.util.HashMap;

/**
 * Created by zc on 2017/12/29.
 */

public class TextLoaderUtil {
    private static final HashMap<String, Integer> COUNT_MAP = new HashMap<>();
    private static final HashMap<Object, SectorEntity> SECTOR_MAP = new HashMap<>();
    private static final HashMap<String, TextHunter> TEXT_HUNTER_MAP = new HashMap<>();

    protected static int getCountByKey(String key){
        return COUNT_MAP.get(key);
    }

    protected static HashMap<String, Integer> getCountMap(){
        return COUNT_MAP;
    }

    protected static HashMap<Object, SectorEntity> getSectorMap(){
        return SECTOR_MAP;
    }

    protected static HashMap<String, TextHunter> getTextHunterMap(){
        return TEXT_HUNTER_MAP;
    }

    protected static void checkMain() {
        if (!isMain()) {
            throw new IllegalStateException("Method call should happen from the main thread.");
        }
    }

    protected static boolean isMain() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

}




















