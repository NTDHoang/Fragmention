package me.yokeyword.ntdhoang.demo_zhihu.base;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T, R extends BaseViewHolder> extends RecyclerView.Adapter<R> {
    protected List<T> mData;
    protected OnItemClickListener mOnItemClickListener;

    public BaseRecyclerAdapter(List<T> data) {
        mData = data != null ? data : new ArrayList<T>();
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mOnItemClickListener = mListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public List<T> getItems() {
        return mData;
    }
}
