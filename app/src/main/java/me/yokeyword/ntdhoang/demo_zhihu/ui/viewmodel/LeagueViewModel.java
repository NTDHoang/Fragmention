package me.yokeyword.ntdhoang.demo_zhihu.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import me.yokeyword.ntdhoang.demo_zhihu.data.LocalRepository;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;

public class LeagueViewModel extends ViewModel {

    private LocalRepository localRepository;

    private CompositeDisposable compositeDisposable;

    public LeagueViewModel(LocalRepository localRepo, CompositeDisposable disposable) {
        localRepository = localRepo;
        compositeDisposable = disposable;
    }

    public Flowable<List<Hero>> getLeagues() {
        return localRepository.getHeros();
    }

    public Single<Hero> getHeroById(String id) {
        return localRepository.getHeroById(Integer.parseInt(id));
    }

    public Single<Hero> getOneLeague() {
        return localRepository.getOneHero();
    }

    public void insertLeague(Hero League) {
        localRepository.insertHero(League);
    }

    public void deleteAllLeagues() {
        localRepository.deleteAllHeros();
    }

    @Override
    public void onCleared() {
        //prevents memory leaks by disposing pending observable objects
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}
