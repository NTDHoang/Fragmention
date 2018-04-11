package me.yokeyword.ntdhoang.demo_zhihu.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import me.yokeyword.ntdhoang.demo_zhihu.data.LocalRepository;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Item;

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


    public void insertLeague(Hero League) {
        localRepository.insertHero(League);
    }

    public void deleteAllLeagues() {
        localRepository.deleteAllHeros();
    }

    public Flowable<List<Item>> getItems() {
        return localRepository.getItems();
    }

    public void insertItem(Item item) {
        localRepository.insertItem(item);
    }

    public void deleteAllItems() {
        localRepository.deleteAllItems();
    }

    @Override
    public void onCleared() {
        //prevents memory leaks by disposing pending observable objects
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}
