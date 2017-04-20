package com.github.linolium.yandex_translator.di.components;

import com.github.linolium.yandex_translator.di.ActivityScope;
import com.github.linolium.yandex_translator.di.modules.SplashModule;
import com.github.linolium.yandex_translator.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created by linolium on 20.04.17.
 */

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = SplashModule.class
)
public interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
