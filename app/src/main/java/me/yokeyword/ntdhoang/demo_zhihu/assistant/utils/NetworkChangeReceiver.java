package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by longcb on 12/12/16.
 * Email: topcbl@gmail.com
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private List<OnNetworkChangeListener> mListeners;
    private Context mContext;
    private SingletonToast mToastNetwork;
    private boolean mIsNetworkAvailable;

    public NetworkChangeReceiver(Context context, SingletonToast toastNetwork) {
        mContext = context;
        mListeners = new ArrayList<>();
        mToastNetwork = toastNetwork;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            onConnectivityChange(true);
            return;
        }
        onConnectivityChange(false);
    }

    public void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mContext.registerReceiver(this, intentFilter);
    }

    public void unregister() {
        try {
            mContext.unregisterReceiver(this);
        } catch (IllegalArgumentException ignored) {
        }
    }

    public void addOnNetworkChangeListener(OnNetworkChangeListener listener) {
        if (mListeners.contains(listener)) return;
        mListeners.add(listener);
    }

    public void removeOnNetworkChangeListener(OnNetworkChangeListener listener) {
        mListeners.remove(listener);
    }


    public boolean getIsNetworkAvailable() {
        return mIsNetworkAvailable;
    }

    private void onConnectivityChange(boolean isAvailable) {
        mIsNetworkAvailable = isAvailable;
        for (OnNetworkChangeListener listener : mListeners) {
            listener.onConnectivityChange(isAvailable);
        }
    }

    public interface OnNetworkChangeListener {
        void onConnectivityChange(boolean isAvailable);
    }
}
