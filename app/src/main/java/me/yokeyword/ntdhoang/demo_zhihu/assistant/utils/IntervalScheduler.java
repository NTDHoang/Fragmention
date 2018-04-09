package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;

/**
 * Created by PhanVanLinh on 09/01/2018.
 * phanvanlinh.94vn@gmail.com
 */

public class IntervalScheduler {
    private long delay;
    private long period;
    private Timer timer;
    private List<SchedulerListener> schedulerListeners = new ArrayList<>();

    public interface SchedulerListener {
        void onSchedule();
    }

    @Inject
    public IntervalScheduler() {
    }

    public IntervalScheduler delay(Duration duration) {
        this.delay = duration.duration();
        return this;
    }

    public IntervalScheduler period(Duration duration) {
        this.period = duration.duration();
        return this;
    }

    public Duration period(){
        return Duration.from(period);
    }

    public void schedule() {
        validate();
        cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onSchedule();
            }
        }, delay, period);
    }

    private void cancel() {
        if (timer != null) timer.cancel();
        timer = null;
    }

    public void observe(SchedulerListener listener) {
        if (!schedulerListeners.contains(listener)) {
            schedulerListeners.add(listener);
        }
    }

    public void unObserve(SchedulerListener listener) {
        schedulerListeners.remove(listener);
        if (schedulerListeners.isEmpty() && timer != null) {
            cancel();
        }
    }

    private void validate() {
        if (period <= 0) {
            throw new IllegalArgumentException("period must not <= 0");
        }
    }

    private void onSchedule() {
        for (SchedulerListener listener : schedulerListeners) {
            listener.onSchedule();
        }
    }

}
