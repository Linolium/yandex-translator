package com.github.linolium.yandex_translator.di.components;

import android.app.Application;

import com.github.linolium.yandex_translator.app.App;
import com.github.linolium.yandex_translator.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Linolium on 07.04.2017.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(App app);

    Application app();
}
