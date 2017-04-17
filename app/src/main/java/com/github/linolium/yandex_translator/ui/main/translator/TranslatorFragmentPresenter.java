package com.github.linolium.yandex_translator.ui.main.translator;

import android.content.SharedPreferences;
import okhttp3.Cache;

import com.github.linolium.yandex_translator.app.BaseFragmentPresenter;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.domain.Lang;
import com.github.linolium.yandex_translator.network.NetworkService;

import rx.Subscription;

/**
 * Created by Linolium on 11.04.2017.
 */

public interface TranslatorFragmentPresenter extends BaseFragmentPresenter<TranslatorFragmentView> {
    Subscription subscribeToBus(Bus bus);
    void updateLangs(SharedPreferences preferences, Bus bus, NetworkService service, Cache cache);
}
