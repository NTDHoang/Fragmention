package me.yokeyword.ntdhoang.demo_zhihu.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import java.util.concurrent.Executor;
import javax.inject.Named;
import me.yokeyword.ntdhoang.demo_zhihu.data.source.local.HeroDAO;
import me.yokeyword.ntdhoang.demo_zhihu.data.source.local.LeagueDatabase;
import me.yokeyword.ntdhoang.demo_zhihu.data.LocalRepository;
import me.yokeyword.ntdhoang.demo_zhihu.data.LocalRepositoryImpl;

@Module
public class LeagueModule {

    private Context context;

    public LeagueModule(Context ctx) {
        context = ctx;
    }

    @LeagueScope
    @Provides
    public HeroDAO getHeroDAO(LeagueDatabase leagueDatabase) {
        return leagueDatabase.heroDAO();
    }

    @LeagueScope
    @Provides
    public LeagueDatabase getLeagueDatabase() {
        return Room.databaseBuilder(context.getApplicationContext(), LeagueDatabase.class,
                "leagues.db").build();
    }

    @LeagueScope
    @Provides
    public LocalRepository getLocalRepo(HeroDAO heroDAO, Executor exec) {
        return new LocalRepositoryImpl(heroDAO, exec);
    }

    @LeagueScope
    @Provides
    @Named("activity")
    public CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

    @LeagueScope
    @Provides
    @Named("vm")
    public CompositeDisposable getVMCompositeDisposable() {
        return new CompositeDisposable();
    }
}
