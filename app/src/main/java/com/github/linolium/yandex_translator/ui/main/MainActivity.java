package com.github.linolium.yandex_translator.ui.main;

import android.os.Bundle;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.app.BaseActivity;
import com.github.linolium.yandex_translator.di.HasComponent;
import com.github.linolium.yandex_translator.di.components.AppComponent;
import com.github.linolium.yandex_translator.di.components.DaggerMainComponent;
import com.github.linolium.yandex_translator.di.components.MainComponent;
import com.github.linolium.yandex_translator.di.modules.MainModule;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements HasComponent<MainComponent>, MainView {

    @Inject
    MainPresenter presenter;

    private MainComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        component = DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build();
        component.inject(this);
    }

    // HasComponent implement method =========
    @Override
    public MainComponent getComponent() {
        return component;
    }
}
