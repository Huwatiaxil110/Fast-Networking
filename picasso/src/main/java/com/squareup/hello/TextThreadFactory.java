package com.squareup.hello;

import android.os.Process;
import java.util.concurrent.ThreadFactory;
import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/**
 * Created by zc on 2017/12/29.
 */

public class TextThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new TextThread(r);
    }

    private static class TextThread extends Thread {
        TextThread(Runnable r) {
            super(r);
        }

        @Override
        public void run() {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND);
            super.run();
        }
    }
}
