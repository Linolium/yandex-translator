package com.github.linolium.yandex_translator.di.modules;

import com.github.linolium.yandex_translator.ui.main.MainActivity;
import com.github.linolium.yandex_translator.ui.main.MainPresenter;
import com.github.linolium.yandex_translator.ui.main.MainPresenterImpl;
import com.github.linolium.yandex_translator.ui.main.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Linolium on 07.04.2017.
 */

@Module
public class MainModule {
    private MainActivity activity;

    public MainModule(MainActivity activity) {
        this.activity = activity;
    }

    /** Provide MainView */
    @Provides
    MainView provideMainView() {
        return activity;
    }

    /** Provide MainPresenterImpl */
    @Provides
    MainPresenter provideMainPresenterImpl(MainView view) {
        return new MainPresenterImpl(view);
    }
}
