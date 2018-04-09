package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.support.annotation.NonNull;

/**
 * Created by hieupham on 01/08/2017.
 */

public interface ActivityManagement extends Application.ActivityLifecycleCallbacks {

    /**
     * Determine that app is visible to user (in foreground)
     */
    boolean isAppVisible();

    /**
     * Determine that app is launched at least one time, still running in background or foreground
     */
    boolean isAppActive();

    /**
     * Determine that {@link Activity} is visible to user or not
     */
    boolean isActivityVisible(Class<? extends Activity> activity);

    /**
     * Set listener to listen the Application life cycle callback.
     * If in {@link Activity}, It should be implemented before {@link Activity#onStart()} is called,
     * if in {@link Fragment}, It should be implemented before {@link Fragment#onStart()} is called
     * to avoid lack
     * notification from {@link ActivityManagement}
     */
    void setApplicationLifeCycleClient(@NonNull ApplicationLifeCycleClient client);

    /**
     * Remove listener from {@link ActivityManagement}
     * If in {@link Activity}, It should be implemented after {@link Activity#onStop()} ()} is
     * called,
     * if in {@link Fragment}, It should be implemented after {@link Fragment#onStop()} is called
     * to avoid lack notification from {@link ActivityManagement}
     */
    void removeApplicationLifeCycleClient(@NonNull ApplicationLifeCycleClient client);

    interface ApplicationLifeCycleClient {

        void onBackground();

        void onForeground();
    }
}
