package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * ActivityLifecycleCallbacks.
 *
 * @author DaoLQ
 */
public class ActivityManagementImpl implements ActivityManagement {

    private List<Class<? extends Activity>> runningActivities;
    private List<Class<? extends Activity>> activeActivities;
    private List<ApplicationLifeCycleClient> clients;
    private Boolean isBackground;

    public ActivityManagementImpl() {
        runningActivities = new ArrayList<>();
        activeActivities = new ArrayList<>();
        clients = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        doOnActivityCreated(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        doOnActivityStarted(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        doOnActivityStopped(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        doOnActivityDestroyed(activity);
    }

    @Override
    public boolean isAppVisible() {
        return !runningActivities.isEmpty();
    }

    @Override
    public boolean isAppActive() {
        return !activeActivities.isEmpty();
    }

    @Override
    public boolean isActivityVisible(Class<? extends Activity> activity) {
        if (!isAppVisible()) return false;
        for (Class<? extends Activity> clazz : runningActivities) {
            if (clazz.isAssignableFrom(activity)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setApplicationLifeCycleClient(@NonNull ApplicationLifeCycleClient client) {
        if (clients.contains(client)) return;
        clients.add(client);
        onClientObserved(client);
    }

    @Override
    public void removeApplicationLifeCycleClient(@NonNull ApplicationLifeCycleClient client) {
        clients.remove(client);
    }

    private void onClientObserved(ApplicationLifeCycleClient client) {
        if (isBackground == null) return;
        if (isBackground) {
            client.onBackground();
        } else {
            client.onForeground();
        }
    }

    private void doOnActivityCreated(Activity activity) {
        if (activeActivities.contains(activity.getClass())) return;
        activeActivities.add(activity.getClass());
    }

    private void doOnActivityStarted(Activity activity) {
        Class<? extends Activity> activityClass = activity.getClass();
        if (!runningActivities.contains(activityClass)) runningActivities.add(activityClass);
        if (isBackground == null || isBackground) {
            doOnForeground();
        }
    }

    private void doOnActivityStopped(Activity activity) {
        Class<? extends Activity> activityClass = activity.getClass();
        runningActivities.remove(activityClass);
        if (!isAppVisible()) {
            doOnBackground();
        }
    }

    private void doOnActivityDestroyed(Activity activity) {
        Class<? extends Activity> activityClass = activity.getClass();
        activeActivities.remove(activityClass);
    }

    private void doOnBackground() {
        isBackground = true;
        notifyOnBackground();
    }

    private void doOnForeground() {
        isBackground = false;
        notifyOnForeground();
    }

    private void notifyOnForeground() {
        for (ApplicationLifeCycleClient client : clients) {
            client.onForeground();
        }
    }

    private void notifyOnBackground() {
        for (ApplicationLifeCycleClient client : clients) {
            client.onBackground();
        }
    }
}
