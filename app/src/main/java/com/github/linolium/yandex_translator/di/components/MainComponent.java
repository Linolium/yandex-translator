package com.github.linolium.yandex_translator.di.components;

import com.github.linolium.yandex_translator.di.ActivityScope;
import com.github.linolium.yandex_translator.di.modules.MainModule;
import com.github.linolium.yandex_translator.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by Linolium on 07.04.2017.
 */

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = MainModule.class
)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
