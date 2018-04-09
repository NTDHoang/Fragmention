package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by framgia on 17/11/2016.
 */

public class PermissionHandler {

    public static final int REQUEST_CODE_GRANTED_PERMISSION = 0x1234;
    private FragmentActivity activity;
    private Context context;
    private boolean isRequesting;

    @Inject
    public PermissionHandler(Context context) {
        this.context = context;
    }

    public PermissionHandler with(FragmentActivity activity) {
        this.activity = activity;
        return this;
    }

    public boolean checkPermission(String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public void requestPermission(String... permissions) {
        validate();
        isRequesting = true;
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE_GRANTED_PERMISSION);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults, PermissionGrantedListener grantedListener) {
        onRequestPermissionsResult(requestCode, permissions, grantResults, grantedListener, null);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults, PermissionDeniedListener deniedListener) {
        onRequestPermissionsResult(requestCode, permissions, grantResults, null, deniedListener);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults, PermissionGrantedListener grantedListener,
            PermissionDeniedListener deniedListener) {
        validate();
        isRequesting = false;
        if (requestCode != REQUEST_CODE_GRANTED_PERMISSION) return;
        List<String> granted = new ArrayList<>();
        List<Pair<String, Boolean>> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(permissions[i]);
            } else {
                denied.add(new Pair<>(permissions[i],
                        !ActivityCompat.shouldShowRequestPermissionRationale(activity,
                                permissions[i])));
            }
        }

        if (grantedListener != null && !granted.isEmpty()) grantedListener.onGranted(granted);
        if (deniedListener != null && !denied.isEmpty()) deniedListener.onDenied(denied);
    }

    public boolean isRequesting() {
        return isRequesting;
    }

    private void validate() {
        if (activity == null) throw new IllegalArgumentException("Must pass an activity instance");
    }

    public interface PermissionGrantedListener {

        void onGranted(List<String> permissions);
    }

    public interface PermissionDeniedListener {
        void onDenied(List<Pair<String, Boolean>> permissions);
    }
}
