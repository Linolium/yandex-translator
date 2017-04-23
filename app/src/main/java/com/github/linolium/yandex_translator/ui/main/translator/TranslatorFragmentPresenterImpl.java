package com.github.linolium.yandex_translator.ui.main.translator;

import android.content.SharedPreferences;
import android.util.Log;

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
                        realm.executeTransaction(transaction -> {
                            TranslateText translateText = ((FavouriteEvent) event).getTranslateText();
                            if (transaction.where(TranslateText.class)
                                    .equalTo("enteredText", translateText.getEnteredText())
                                    .equalTo("fromToCode", translateText.getFromToCode().toUpperCase()).findFirst() == null) {
                                translateText.setId(TranslateText.getNextKey(realm));
                                translateText.setFavourite(true);
                                translateText.setFromToCode(translateText.getFromToCode().toUpperCase());
                                transaction.copyToRealm(translateText);
                                view.showMessage(R.string.toast_successfully_added, MessageType.INFO);
                            } else {
                                view.clearEditText();
                                view.showMessage(R.string.wordIsAlreadyExists, MessageType.ERROR);
                            }
                        });
                    } else if (event instanceof HistoryEvent) {
                        view.hideProgress();
                    }
                });
    }

    @Override
    public void loadLangs(NetworkService networkService, Bus bus, SharedPreferences preferences, Realm realm) {
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

                        // кэшируем предыдущий ответ
                        realm.executeTransaction(transaction -> {
                            transaction.delete(Lang.class);
                            transaction.copyToRealm(langList);
                        });

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
                    // в случае ошибки достаем данные из кэша
                    realm.where(Lang.class).findAll().asObservable()
                            .first()
                            .subscribe(result -> {
                                List<Lang> langs = realm.copyFromRealm(result);
                                if (langs.isEmpty()) {
                                    view.showLangDialog();
                                    return;
                                }
                                bus.send(new LoadLangsEvent(langs));
                            });

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

    @Override
    public void updateHistory(Bus bus, Realm realm, TranslateText translateText) {
        realm.executeTransaction(transaction -> {
            translateText.setId(TranslateText.getNextKey(realm));
            translateText.setFavourite(false);
            translateText.setFromToCode(translateText.getFromToCode().toUpperCase());
            transaction.copyToRealm(translateText);
            bus.send(new HistoryEvent(translateText));
        });
    }
}
