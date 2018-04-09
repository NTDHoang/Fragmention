package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.databinding.BaseObservable;
import android.text.TextUtils;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 22/11/2016.
 */

public class BindableText extends BaseObservable {
    private String value;

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (!TextUtils.equals(this.value, value)) {
            this.value = value;
            notifyChange();
        }
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }
}
