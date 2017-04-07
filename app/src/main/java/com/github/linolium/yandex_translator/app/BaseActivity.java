package com.github.linolium.yandex_translator.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.linolium.yandex_translator.di.components.AppComponent;

/**
 * Created by Linolium on 07.04.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(App.get(this).getAppComponent());
    }

    protected abstract void setupComponent(AppComponent appComponent);
}
