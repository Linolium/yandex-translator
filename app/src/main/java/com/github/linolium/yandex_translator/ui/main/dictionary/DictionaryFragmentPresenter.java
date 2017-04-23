package com.github.linolium.yandex_translator.ui.main.dictionary;

import android.content.SharedPreferences;

import com.github.linolium.yandex_translator.app.BaseFragmentPresenter;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.domain.TranslateText;

import io.realm.Realm;
import rx.Subscription;

/**
 * Created by Linolium on 21.04.2017.
 */

public interface DictionaryFragmentPresenter extends BaseFragmentPresenter<DictionaryFragmentView> {
    Subscription subscribeToBus(Bus bus, SharedPreferences preferences, Realm realm);
    void getSavedTexts(Bus bus, Realm realm);
    void clearFavourite(Realm realm);
    void clearSingleFavourite(Realm realm, TranslateText translateText);
}
