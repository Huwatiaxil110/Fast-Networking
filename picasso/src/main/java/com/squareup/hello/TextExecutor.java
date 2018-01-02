package com.squareup.hello;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zc on 2017/12/29.
 */

public class TextExecutor extends ThreadPoolExecutor {
    private static final int DEFAULT_COUNT = 3;

    protected TextExecutor() {
        super(DEFAULT_COUNT, DEFAULT_COUNT, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), new TextThreadFactory());
    }

    @Override
    public Future<?> submit(Runnable task) {
        TextFutureTask ftask = new TextFutureTask((TextHunter) task);
        execute(ftask);
        return ftask;
    }

    private static final class TextFutureTask extends FutureTask<TextHunter> implements Comparable<TextFutureTask>{
        private final TextHunter hunter;

        TextFutureTask(TextHunter hunter) {
            super(hunter, null);
            this.hunter = hunter;
        }

        @Override
        public int compareTo(TextFutureTask other) {
            return hunter.sequence - other.hunter.sequence;
        }
    }
}
