package com.github.linolium.yandex_translator.di.modules;

import android.app.Application;

import com.github.linolium.yandex_translator.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Linolium on 07.04.2017.
 */

@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }
}
