package me.yokeyword.ntdhoang.demo_zhihu.data;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import java.util.concurrent.Executor;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Item;
import me.yokeyword.ntdhoang.demo_zhihu.data.source.local.HeroDAO;
import me.yokeyword.ntdhoang.demo_zhihu.data.source.local.ItemDAO;

public class LocalRepositoryImpl implements LocalRepository {
    private HeroDAO HeroDAO;
    private ItemDAO itemDAO;
    private Executor executor;

    public LocalRepositoryImpl(HeroDAO cpnDAO, ItemDAO idnDao, Executor exec) {
        HeroDAO = cpnDAO;
        itemDAO = idnDao;
        executor = exec;
    }

    public Flowable<List<Hero>> getHeros() {
        return HeroDAO.getHeros();
    }

    public Single<Hero> getHeroById(int id) {
        return HeroDAO.getHeroById(id);
    }

    public void insertHero(Hero Hero) {
        executor.execute(() -> {
            HeroDAO.insertHero(Hero);
        });
    }

    public void deleteAllHeros() {
        executor.execute(() -> {
            HeroDAO.deleteAllHeros();
        });
    }

    @Override
    public Flowable<List<Item>> getItems() {
        return itemDAO.getItems();
    }

    @Override
    public Single<Item> getItemById(int id) {
        return itemDAO.getItemById(id);
    }

    @Override
    public void insertItem(Item item) {
        executor.execute(() -> {
            itemDAO.insertItem(item);
        });
    }

    @Override
    public void deleteAllItems() {
        itemDAO.deleteAllItems();
    }
}
