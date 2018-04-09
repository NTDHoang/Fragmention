package me.yokeyword.ntdhoang.demo_zhihu.data;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import java.util.concurrent.Executor;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;
import me.yokeyword.ntdhoang.demo_zhihu.data.source.local.HeroDAO;

public class LocalRepositoryImpl implements LocalRepository {
    private HeroDAO HeroDAO;
    private Executor executor;

    public LocalRepositoryImpl(HeroDAO cpnDAO, Executor exec) {
        HeroDAO = cpnDAO;
        executor = exec;
    }

    public Flowable<List<Hero>> getHeros() {
        return HeroDAO.getHeros();
    }

    public Single<Hero> getHeroById(int id) {
        return HeroDAO.getHeroById(id);
    }

    public Single<Hero> getOneHero() {
        return HeroDAO.getOneHero();
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
}
