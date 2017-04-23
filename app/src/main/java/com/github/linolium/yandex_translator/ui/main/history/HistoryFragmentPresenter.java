package com.github.linolium.yandex_translator.ui.main.history;

import android.content.SharedPreferences;

import com.github.linolium.yandex_translator.app.BaseFragmentPresenter;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.domain.TranslateText;


import io.realm.Realm;
import rx.Subscription;

/**
 * Created by Linolium on 21.04.2017.
 */

public interface HistoryFragmentPresenter extends BaseFragmentPresenter<HistoryFragmentView> {
    Subscription subscribeToBus(Bus bus, SharedPreferences preferences, Realm realm);
    void getHistoryTexts(Bus bus, Realm realm);
    void clearHistory(Realm realm);
    void clearSingleHistory(Realm realm, TranslateText translateText);
}
