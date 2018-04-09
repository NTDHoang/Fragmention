package me.yokeyword.ntdhoang.demo_zhihu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import me.yokeyword.ntdhoang.R;
import me.yokeyword.ntdhoang.demo_zhihu.ui.adapter.holder.HeroHolder;
import me.yokeyword.ntdhoang.demo_zhihu.base.BaseRecyclerAdapter;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;

public class YoutubeAdapter extends BaseRecyclerAdapter<Hero, HeroHolder> implements Filterable {
    private List<Hero> filtered;
    private Context mContext;

    public YoutubeAdapter(List<Hero> heroes, Context current) {
        super(heroes);
        filtered = mData;
        this.mContext = current;
    }

    @Override
    public int getItemCount() {
        return filtered.size();
    }

    @Override
    public Hero getItem(int position) {
        return filtered.get(position);
    }

    @Override
    public HeroHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_row, parent, false);
        return new HeroHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(HeroHolder holder, int position) {
        Hero hero = getItem(position);
        holder.name.setText(hero.getName());

        Context context = holder.name.getContext();
        Glide.with(context).load(hero.getImgRes()).into(holder.image);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Hero> filteredHeroes = new ArrayList<Hero>();
                if (constraint == null) {
                    filterResults.values = mData;
                    filterResults.count = mData.size();
                    return filterResults;
                }
                String lowerConstr = constraint.toString().toLowerCase();
                for (Hero hero : mData) {
                    if (hero.getName().toLowerCase().contains(lowerConstr) || hero.getName()
                            .toLowerCase()
                            .contains(lowerConstr)) {
                        filteredHeroes.add(hero);
                    }
                }
                filterResults.count = filteredHeroes.size();
                filterResults.values = filteredHeroes;
                return filterResults;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filtered = (ArrayList<Hero>) results.values;
                if (filtered == null) {
                    filtered = new ArrayList<Hero>();
                }
                notifyDataSetChanged();
            }
        };
    }
}
