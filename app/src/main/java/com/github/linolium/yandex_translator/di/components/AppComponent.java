package com.github.linolium.yandex_translator.di.components;

import android.app.Application;
import android.content.SharedPreferences;

import com.github.linolium.yandex_translator.app.App;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.di.modules.AppModule;
import com.github.linolium.yandex_translator.network.NetworkService;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import okhttp3.Cache;

/**
 * Created by Linolium on 07.04.2017.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(App app);

    Application app();

    SharedPreferences sharedPreferences();

    Bus eventBus();

    Gson gson();

    Cache cache();

    @Named("cached")
    NetworkService mobukCachedService();

    @Named("no_cached")
    NetworkService mobukNoCachedService();

}
