package me.yokeyword.ntdhoang.demo_zhihu.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private OnItemClickListener listener;

    public BaseViewHolder(View itemView) {
        this(itemView, null);
    }

    public BaseViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);
        initView(itemView);
    }

    protected abstract void initView(View itemView);

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClick(v, getLayoutPosition());
        }
    }
}
