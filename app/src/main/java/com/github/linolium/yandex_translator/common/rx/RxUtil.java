package com.github.linolium.yandex_translator.common.rx;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Linolium on 14.04.2017.
 */

public class RxUtil {

    @NonNull
    public static <T> Observable.Transformer<T, T> applySchedulersAndRetry() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2000));
    }
}
