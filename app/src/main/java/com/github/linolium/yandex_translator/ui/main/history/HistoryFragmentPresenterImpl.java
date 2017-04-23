package com.github.linolium.yandex_translator.ui.main.history;

import android.content.SharedPreferences;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.common.eventbus.events.HttpErrorEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.ThrowableEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.history.DisplayHistoryEvent;
import com.github.linolium.yandex_translator.domain.TranslateText;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Linolium on 21.04.2017.
 */

public class HistoryFragmentPresenterImpl implements HistoryFragmentPresenter {

    private HistoryFragmentView view;

    @Override
    public void init(HistoryFragmentView view) {
        this.view = view;
    }

    @Inject
    public HistoryFragmentPresenterImpl() {

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
    public void getHistoryTexts(Bus bus, Realm realm) {
        view.showProgress();
        realm.where(TranslateText.class).equalTo("isFavourite", false).findAll()
                .asObservable()
                .subscribe(result -> {
                    List<TranslateText> translatedTexts = realm.copyFromRealm(result);
                    view.getHistoryTexts(translatedTexts);
                    view.hideProgress();
                });
    }

    @Override
    public void clearHistory(Realm realm) {
        realm.executeTransaction(transaction -> {
            transaction.where(TranslateText.class)
                    .equalTo("isFavourite", false)
                    .findAll()
                    .deleteAllFromRealm();
        });
    }
}
