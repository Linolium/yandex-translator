package com.github.linolium.yandex_translator.ui.main;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.app.BaseActivity;
import com.github.linolium.yandex_translator.di.HasComponent;
import com.github.linolium.yandex_translator.di.components.AppComponent;
import com.github.linolium.yandex_translator.di.components.DaggerMainComponent;
import com.github.linolium.yandex_translator.di.components.MainComponent;
import com.github.linolium.yandex_translator.di.modules.MainModule;
import com.github.linolium.yandex_translator.ui.main.dictionary.DictionaryFragment;
import com.github.linolium.yandex_translator.ui.main.translator.TranslatorFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements HasComponent<MainComponent>, MainView {

    @Inject
    MainPresenter presenter;

    private MainComponent component;

    private BottomBar bottomBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottom_navigation_main, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.action_translate) {
                    TranslatorFragment f = new TranslatorFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, f).commit();
                }
                if (menuItemId == R.id.action_dictionary) {
                    DictionaryFragment f = new DictionaryFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, f).commit();
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
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
