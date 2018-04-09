package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.text.TextUtils;
import com.google.gson.annotations.Expose;

/**
 * Created by hieupham on 18/05/2017.
 */

public class CompanyNotice {

    @Expose
    private String action;

    @Expose
    private String name;

    public CompanyNotice save(String action, String name) {
        this.action = action;
        this.name = name;
        return this;
    }

    public void clear() {
        this.action = "";
        this.name = "";
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(action) && TextUtils.isEmpty(name);
    }

    public String getAction() {
        return action;
    }

    public String getName() {
        return name;
    }
}
