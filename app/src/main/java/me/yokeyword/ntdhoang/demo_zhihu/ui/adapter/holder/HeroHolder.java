package me.yokeyword.ntdhoang.demo_zhihu.ui.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import me.yokeyword.ntdhoang.R;
import me.yokeyword.ntdhoang.demo_zhihu.base.BaseViewHolder;
import me.yokeyword.ntdhoang.demo_zhihu.base.OnItemClickListener;

public class HeroHolder extends BaseViewHolder {
    public TextView name;
    public ImageView image;
    public ImageView heroType;

    public HeroHolder(View itemView, OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void initView(View itemView) {
        name = (TextView) itemView.findViewById(R.id.name);
        image = (ImageView) itemView.findViewById(R.id.img);
    }
}
