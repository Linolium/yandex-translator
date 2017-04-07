package com.github.linolium.yandex_translator.app;

/**
 * Created by Linolium on 07.04.2017.
 */

public interface BaseFragmentPresenter<T> {
    void init(T view);
}
