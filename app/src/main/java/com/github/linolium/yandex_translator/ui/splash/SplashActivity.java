package com.github.linolium.yandex_translator.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.app.BaseActivity;
import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.di.HasComponent;
import com.github.linolium.yandex_translator.di.components.AppComponent;
import com.github.linolium.yandex_translator.di.components.DaggerSplashComponent;
import com.github.linolium.yandex_translator.di.components.SplashComponent;
import com.github.linolium.yandex_translator.di.modules.SplashModule;
import com.github.linolium.yandex_translator.ui.main.MainActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements HasComponent<SplashComponent>, SplashView {

    @Inject
    public SplashPresenterImpl presenter;

    private SplashComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter.onCreate();
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        component = DaggerSplashComponent.builder()
                .appComponent(appComponent)
                .splashModule(new SplashModule(this))
                .build();
        component.inject(this);
    }

    @Override
    public void showToast(int message, @MessageType int type) {
        super.showToast(message, type);
    }

    @Override
    public SplashComponent getComponent() {
        return component;
    }

    @Override
    public void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void finishActivity() {
        finish();
        // включаем анимацию при закрытии заставки - исчезновение
        //overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
        overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
    }


}
