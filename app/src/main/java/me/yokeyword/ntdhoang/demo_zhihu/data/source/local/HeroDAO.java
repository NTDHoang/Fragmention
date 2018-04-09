package me.yokeyword.ntdhoang.demo_zhihu.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;

@Dao
public interface HeroDAO {
    @Query("SELECT * FROM Hero")
    Flowable<List<Hero>> getHeros();

    @Query("SELECT * FROM Hero WHERE id = :id ")
    Single<Hero> getHeroById(int id);

    @Query("SELECT * FROM Hero LIMIT 1")
    Single<Hero> getOneHero();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHero(Hero Hero);

    @Query("DELETE FROM Hero")
    void deleteAllHeros();
}
