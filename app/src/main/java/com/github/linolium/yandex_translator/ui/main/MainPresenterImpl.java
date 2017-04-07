package com.github.linolium.yandex_translator.ui.main;

import javax.inject.Inject;

/**
 * Created by Linolium on 07.04.2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView view;

    @Inject
    public MainPresenterImpl(MainView view) {
        this.view = view;
    }


}
