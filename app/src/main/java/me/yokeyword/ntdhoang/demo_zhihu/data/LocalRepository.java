package me.yokeyword.ntdhoang.demo_zhihu.data;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Item;

public interface LocalRepository {
    Flowable<List<Hero>> getHeros();

    Single<Hero> getHeroById(int id);

    void insertHero(Hero Hero);

    void deleteAllHeros();

    Flowable<List<Item>> getItems();

    Single<Item> getItemById(int id);

    void insertItem(Item item);

    void deleteAllItems();
}
