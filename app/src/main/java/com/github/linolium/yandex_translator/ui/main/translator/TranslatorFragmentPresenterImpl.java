package com.github.linolium.yandex_translator.ui.main.translator;

import android.content.SharedPreferences;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.common.Config;
import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.common.eventbus.events.HttpErrorEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.ThrowableEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.translator.FavouriteEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.translator.HistoryEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.translator.LoadLangsEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.translator.TranslateEvent;
import com.github.linolium.yandex_translator.common.rx.RxUtil;
import com.github.linolium.yandex_translator.domain.Lang;
import com.github.linolium.yandex_translator.domain.LangResponse;
import com.github.linolium.yandex_translator.domain.TranslateText;
import com.github.linolium.yandex_translator.domain.TranslateTextResponse;
import com.github.linolium.yandex_translator.network.NetworkService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Linolium on 11.04.2017.
 */

public class TranslatorFragmentPresenterImpl implements TranslatorFragmentPresenter {

    private TranslatorFragmentView view;

    @Override
    public void init(TranslatorFragmentView view) {
        this.view = view;
    }

    @Inject
    public TranslatorFragmentPresenterImpl() {
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
                    } else if (event instanceof LoadLangsEvent) {
                        view.hideProgress();
                        final List<Lang> langList = ((LoadLangsEvent) event).getLangList();
                        view.updateSpinners(langList);
                    } else if (event instanceof TranslateEvent) {
                        view.hideProgress();
                        final List<TranslateText> textList = ((TranslateEvent) event).getTextList();
                        view.updateRecyclerView(textList);
                    } else if (event instanceof FavouriteEvent) {
                        view.hideProgress();
                        realm.beginTransaction();
                        TranslateText translateText = realm.createObject(TranslateText.class, TranslateText.getNextKey(realm));
                        translateText.setEnteredText(((FavouriteEvent) event).getTranslateText().getEnteredText());
                        translateText.setTranslatedText(((FavouriteEvent) event).getTranslateText().getTranslatedText());
                        translateText.setFromToCode(((FavouriteEvent) event).getTranslateText().getFromToCode().toUpperCase());
                        translateText.setFavourite(true);
                        realm.commitTransaction();
                        view.showMessage(R.string.toast_successfully_added, MessageType.INFO);
                    } else if (event instanceof HistoryEvent) {
                        view.hideProgress();
                        realm.beginTransaction();
                        TranslateText translateText = realm.createObject(TranslateText.class, TranslateText.getNextKey(realm));
                        translateText.setEnteredText(((HistoryEvent) event).getTranslateText().getEnteredText());
                        translateText.setTranslatedText(((HistoryEvent) event).getTranslateText().getTranslatedText());
                        translateText.setFromToCode(((HistoryEvent) event).getTranslateText().getFromToCode().toUpperCase());
                        translateText.setFavourite(false);
                        realm.commitTransaction();
                    }
                });
    }

    @Override
    public void loadLangs(NetworkService networkService, Bus bus, SharedPreferences preferences) {
        view.showProgress();
        Observable<Response<LangResponse>> responseObservable = networkService.getLangs(Config.API_KEY, "ru");
        responseObservable.compose(RxUtil.applySchedulersAndRetry())
                .subscribe(response -> {
                   int responseCode = response.code();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        LangResponse langResponse = response.body();
                        List<Lang> langList = new ArrayList<>();
                        for (Map.Entry<String, String> entry : langResponse.getLangs().entrySet()) {
                            langList.add(new Lang(entry.getKey(), entry.getValue()));
                        }

                        final int fromPosition = preferences.getInt(Config.FROM_LANG_POS, -1);
                        final int toPosition = preferences.getInt(Config.TO_LANG_POS, -1);

                        if (fromPosition == -1 && toPosition == -1) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt(Config.FROM_LANG_POS, 47);
                            editor.putInt(Config.TO_LANG_POS, 49);
                            editor.apply();
                        }
                        bus.send(new LoadLangsEvent(langList));
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    bus.send(new ThrowableEvent(throwable));
                });

    }

    @Override
    public void loadTranslatedList(NetworkService networkService, Bus bus, String lang, String text) {
        view.showProgress();
        Observable<Response<TranslateTextResponse>> responseObservable = networkService.translate(lang, Config.API_KEY, text);
        responseObservable.compose(RxUtil.applySchedulersAndRetry())
                .subscribe(response -> {
                   int responseCode = response.code();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        TranslateTextResponse textResponse = response.body();
                        List<TranslateText> textList = new ArrayList<>();
//                        textResponse.getTranslatedText().forEach( translatedText -> textList.add(new TranslateText(translatedText)));
                        for (String translatedText : textResponse.getText()) {
                            textList.add(new TranslateText(translatedText, text, lang));
                        }
                        bus.send(new TranslateEvent(textList));
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    bus.send(new ThrowableEvent(throwable));
                });
    }

    @Override
    public void setDefaultLangs(String spinnerConfig, int pos, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(spinnerConfig, pos);
        editor.apply();
    }
}
