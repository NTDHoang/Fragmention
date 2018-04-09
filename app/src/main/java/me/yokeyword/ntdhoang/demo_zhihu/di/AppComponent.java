package me.yokeyword.ntdhoang.demo_zhihu.di;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {
 LeagueComponent getLeagueComponent(LeagueModule leagueModule);
}
