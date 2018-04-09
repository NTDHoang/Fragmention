package me.yokeyword.ntdhoang.demo_zhihu.data;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;

public interface LocalRepository {
    Flowable<List<Hero>> getHeros();

    Single<Hero> getHeroById(int id);

    Single<Hero> getOneHero();

    void insertHero(Hero Hero);

    void deleteAllHeros();
}
