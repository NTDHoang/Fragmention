package me.yokeyword.ntdhoang.demo_zhihu.assistant.rx2;

import android.support.annotation.CallSuper;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FRAMGIA\tran.hoa.binh on 09/04/2018.
 */

public class DisposingObserver<T> implements Observer<T> {
    @Override
    @CallSuper
    public void onSubscribe(Disposable d) {
        DisposableManager.add(d);
    }

    @Override
    public void onNext(T next) {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }
}
