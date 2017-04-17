package com.github.linolium.yandex_translator.app;

import android.app.Application;
import android.content.Context;

import com.github.linolium.yandex_translator.di.components.AppComponent;
import com.github.linolium.yandex_translator.di.components.DaggerAppComponent;
import com.github.linolium.yandex_translator.di.modules.AppModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Linolium on 07.04.2017.
 */

public class App extends Application {

    private AppComponent appComponent;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildObjectGraphAndInject();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public void buildObjectGraphAndInject() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
