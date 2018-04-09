package me.yokeyword.ntdhoang.demo_zhihu.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;

@Database(entities = {Hero.class}, version = 1)
public abstract class LeagueDatabase extends RoomDatabase {
    public abstract HeroDAO heroDAO();
}
