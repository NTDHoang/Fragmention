package me.yokeyword.ntdhoang.demo_zhihu.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import javax.inject.Named;
import me.yokeyword.ntdhoang.demo_zhihu.data.LocalRepository;
import me.yokeyword.ntdhoang.demo_zhihu.di.LeagueScope;

@LeagueScope
public class LeagueViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    LocalRepository localRepository;
    @Inject
    @Named("vm")
    CompositeDisposable compositeDisposable;

    @Inject
    public LeagueViewModelFactory() {
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LeagueViewModel.class)) {
            return (T) new LeagueViewModel(localRepository, compositeDisposable);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}
