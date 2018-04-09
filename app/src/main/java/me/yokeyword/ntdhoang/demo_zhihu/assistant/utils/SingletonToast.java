package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.widget.Toast;
import javax.inject.Inject;

/**
 * Created by longcb on 1/20/17.
 * Email: topcbl@gmail.com
 */
public class SingletonToast {

    @IntDef({ Duration.LENGTH_LONG, Duration.LENGTH_SHORT })
    public @interface Duration {
        int LENGTH_LONG = 3500;
        int LENGTH_SHORT = 2000;
    }

    private Toast mToast;
    private Context mContext;

    @Inject
    public SingletonToast(Context context) {
        mContext = context;
    }

    public void toast(String text) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void toast(String text, int duration) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(mContext, text, duration);
        mToast.show();
    }

    public void toast(@StringRes int text) {
        toast(mContext.getString(text));
    }
}
