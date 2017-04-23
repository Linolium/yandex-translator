package com.github.linolium.yandex_translator.ui.main.dictionary;

import android.content.SharedPreferences;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.common.eventbus.events.HttpErrorEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.ThrowableEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.dictionary.DisplayFavouriteEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.translator.TranslateEvent;
import com.github.linolium.yandex_translator.domain.TranslateText;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Linolium on 21.04.2017.
 */

public class DictionaryFragmentPresenterImpl implements DictionaryFragmentPresenter {

    private DictionaryFragmentView view;

    @Override
    public void init(DictionaryFragmentView view) {
        this.view = view;
    }

    @Inject
    public DictionaryFragmentPresenterImpl() {

    }

    @Override
    public Subscription subscribeToBus(Bus bus, SharedPreferences preferences, Realm realm) {
        return bus.observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    view.hideProgress();
                    if (event instanceof ThrowableEvent) {
                        view.showMessage(R.string.toast_error, MessageType.ERROR);
                    } else if (event instanceof HttpErrorEvent) {
                        view.showMessage(R.string.toast_error, MessageType.ERROR);
                    }
                });
    }

    @Override
    public void getSavedTexts(Bus bus, Realm realm) {
        view.showProgress();
        realm.where(TranslateText.class).equalTo("isFavourite", true).findAll()
                .asObservable()
                .subscribe(result -> {
                    List<TranslateText> translatedTexts = realm.copyFromRealm(result);
                    view.getTranslatedTexts(translatedTexts);
                    view.hideProgress();
                });
    }

    @Override
    public void clearFavourite(Realm realm) {
        realm.executeTransaction(transaction -> {
            transaction.where(TranslateText.class)
                    .equalTo("isFavourite", true)
                    .findAll()
                    .deleteAllFromRealm();
        });
    }
}
