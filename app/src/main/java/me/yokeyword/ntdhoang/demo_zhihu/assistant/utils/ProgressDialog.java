package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import javax.inject.Inject;
import me.yokeyword.ntdhoang.R;

/**
 * Created by phanvanlinh on 01/03/2017.
 * phanvanlinh.94vn@gmail.com
 */

public class ProgressDialog {
    private Dialog dialog;
    private Context mContext;

    @Inject
    public ProgressDialog(Context context) {
        this.mContext = context;
    }

    public void show() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        if(dialog == null) {
            dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_progress_dialog);
            dialog.setCancelable(false);
        }
        dialog.show();
    }

    public void hide() {
        if (dialog != null && dialog.isShowing()) {
            dialog.hide();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
