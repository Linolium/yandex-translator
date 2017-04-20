package com.github.linolium.yandex_translator.ui.splash;

import android.content.SharedPreferences;
import android.os.Handler;

import com.github.linolium.yandex_translator.common.Config;
import com.github.linolium.yandex_translator.network.NetworkService;

import javax.inject.Inject;

/**
 * Created by linolium on 20.04.17.
 */

public class SplashPresenterImpl implements SplashPresenter {

    private SplashView view;

    @Inject
    public SplashPresenterImpl(SplashView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        new Handler().postDelayed(() -> {
            view.startMain();
            view.finishActivity();
        }, Config.SPLASH_DELAY_MILLIS);
    }
}
