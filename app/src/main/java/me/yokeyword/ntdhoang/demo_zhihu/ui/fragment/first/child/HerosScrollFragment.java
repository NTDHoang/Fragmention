package me.yokeyword.ntdhoang.demo_zhihu.ui.fragment.first.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.Orientation;

import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.ntdhoang.R;
import me.yokeyword.ntdhoang.demo_zhihu.adapter.HeroAdapter;
import me.yokeyword.ntdhoang.demo_zhihu.base.DiscreteScrollViewOptions;
import me.yokeyword.ntdhoang.demo_zhihu.entity.Hero;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class HerosScrollFragment extends SupportFragment implements DiscreteScrollView.OnItemChangedListener,
        View.OnClickListener {

    private List<Hero> data;

    private TextView currentItemName;
    private TextView currentItemPrice;
    private ImageView rateItemButton;
    private DiscreteScrollView itemPicker;
    private InfiniteScrollAdapter infiniteAdapter;


    public static HerosScrollFragment newInstance() {
        Bundle args = new Bundle();
        HerosScrollFragment fragment = new HerosScrollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scroll_all, container, false);
        initView(view);
        return view;
    }


    public void initView(View view) {
        currentItemName = (TextView) view.findViewById(R.id.item_name);
        currentItemPrice = (TextView) view.findViewById(R.id.item_price);
        rateItemButton = (ImageView) view.findViewById(R.id.item_btn_rate);

        data = new ArrayList<>();
        data.add(new Hero("1","HEHE"));

        itemPicker = (DiscreteScrollView) view.findViewById(R.id.item_picker);
        itemPicker.setOrientation(Orientation.HORIZONTAL);
        itemPicker.addOnItemChangedListener(this);
        infiniteAdapter = InfiniteScrollAdapter.wrap(new HeroAdapter(data));
        itemPicker.setAdapter(infiniteAdapter);
        itemPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        onItemChanged(data.get(0));

        view.findViewById(R.id.item_btn_rate).setOnClickListener(this);
        view.findViewById(R.id.item_btn_buy).setOnClickListener(this);
        view.findViewById(R.id.item_btn_comment).setOnClickListener(this);

        view.findViewById(R.id.home).setOnClickListener(this);
        view.findViewById(R.id.btn_smooth_scroll).setOnClickListener(this);
        view.findViewById(R.id.btn_transition_time).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_btn_rate:
                int realPosition = infiniteAdapter.getRealPosition(itemPicker.getCurrentItem());
                Hero current = data.get(realPosition);

                break;
            case R.id.home:
                //finish();
                break;
            case R.id.btn_transition_time:
                DiscreteScrollViewOptions.configureTransitionTime(itemPicker);
                break;
            case R.id.btn_smooth_scroll:
                DiscreteScrollViewOptions.smoothScrollToUserSelectedPosition(itemPicker, v);
                break;
            default:
                showUnsupportedSnackBar();
                break;
        }
    }

    private void onItemChanged(Hero item) {
        currentItemName.setText(item.getName());
        currentItemPrice.setText(item.getId());
    }


    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int position) {
        int positionInDataSet = infiniteAdapter.getRealPosition(position);
        onItemChanged(data.get(positionInDataSet));
    }

    private void showUnsupportedSnackBar() {
        Snackbar.make(itemPicker, R.string.msg_unsupported_op, Snackbar.LENGTH_SHORT).show();
    }
}
