package me.yokeyword.ntdhoang.demo_zhihu.di;

import dagger.Subcomponent;
import me.yokeyword.ntdhoang.demo_zhihu.MainActivity;

@LeagueScope
@Subcomponent(modules = LeagueModule.class)
public interface LeagueComponent {
    void inject(MainActivity mainActivity);
}
