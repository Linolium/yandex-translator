package com.github.linolium.yandex_translator.ui.main.translator;

import android.content.SharedPreferences;

import io.realm.Realm;

import com.github.linolium.yandex_translator.app.BaseFragmentPresenter;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.domain.TranslateText;
import com.github.linolium.yandex_translator.network.NetworkService;

import rx.Subscription;

/**
 * Created by Linolium on 11.04.2017.
 */

public interface TranslatorFragmentPresenter extends BaseFragmentPresenter<TranslatorFragmentView> {
    Subscription subscribeToBus(Bus bus, SharedPreferences preferences, Realm realm);
    void loadLangs(NetworkService networkService, Bus bus, SharedPreferences preferences);
    void loadTranslatedList(NetworkService networkService, Bus bus, String lang, String text);
    void setDefaultLangs(String spinnerConfig, int pos, SharedPreferences preferences);
    void updateHistory(Bus bus, Realm realm, TranslateText translateText);
}
