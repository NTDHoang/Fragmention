package me.yokeyword.ntdhoang.demo_zhihu.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import me.yokeyword.ntdhoang.R;
import me.yokeyword.ntdhoang.demo_zhihu.MainActivity;


public class DABaseDialog extends Dialog {

    protected Context mContext;
    private Handler mHandler;
    protected MainActivity mActivity;

    public DABaseDialog(Context context) {
        super(context);
        mContext = context;
        mActivity=(MainActivity) context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mContext, R.color.cmn_transparent)));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
    }

    protected Handler getMainHandler() {
        return mHandler;
    }

}
