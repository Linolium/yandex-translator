package com.github.linolium.yandex_translator.di.modules;

import com.github.linolium.yandex_translator.ui.splash.SplashActivity;
import com.github.linolium.yandex_translator.ui.splash.SplashPresenter;
import com.github.linolium.yandex_translator.ui.splash.SplashPresenterImpl;
import com.github.linolium.yandex_translator.ui.splash.SplashView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by linolium on 20.04.17.
 */

@Module
public class SplashModule {

    private SplashActivity activity;

    public SplashModule(SplashActivity activity) {
        this.activity = activity;
    }

    /** Provide SplashView */
    @Provides
    SplashView provideSplashView() {
        return activity;
    }

    /** Provide SplashPresenterImpl */
    @Provides
    SplashPresenter provideSplashPresenterImpl(SplashView view) {
        return new SplashPresenterImpl(view);
    }
}
