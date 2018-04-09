package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by jollyjoker992 on 15/02/2018.
 */

public class Duration {

    private long durationMillis;

    public static Duration from(long durationMillis) {
        return new Duration(durationMillis);
    }

    public static Duration from(long duration, TimeUnit unit) {
        return new Duration(unit.toMillis(duration));
    }

    private Duration(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public long duration() {
        return durationMillis;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Duration && ((Duration) obj).durationMillis == durationMillis;
    }
}
