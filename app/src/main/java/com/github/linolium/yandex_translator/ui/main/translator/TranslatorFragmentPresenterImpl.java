package com.github.linolium.yandex_translator.ui.main.translator;

import android.content.SharedPreferences;

import okhttp3.Cache;
import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.common.Config;
import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.common.eventbus.events.HttpErrorEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.ThrowableEvent;
import com.github.linolium.yandex_translator.common.eventbus.events.translator.LoadLangsEvent;
import com.github.linolium.yandex_translator.common.rx.RxUtil;
import com.github.linolium.yandex_translator.domain.Lang;
import com.github.linolium.yandex_translator.domain.LangResponse;
import com.github.linolium.yandex_translator.network.NetworkService;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Linolium on 11.04.2017.
 */

public class TranslatorFragmentPresenterImpl implements TranslatorFragmentPresenter {

    private TranslatorFragmentView view;

    public void init(TranslatorFragmentView view) {
        this.view = view;
    }

    @Inject
    public TranslatorFragmentPresenterImpl() {
    }

    @Override
    public Subscription subscribeToBus(Bus bus) {
        return bus.observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    view.hideProgress();
                    if (event instanceof ThrowableEvent) {
                        view.showMessage(R.string.toast_error, MessageType.ERROR);
                    } else if (event instanceof HttpErrorEvent) {
                        view.showMessage(R.string.toast_error, MessageType.ERROR);
                    } else if (event instanceof LoadLangsEvent) {
                        final List<Lang> langs = ((LoadLangsEvent) event).getLangList();
                        view.updateLangs(langs);
                    }
                });
    }

    @Override
    public void updateLangs(SharedPreferences preferences, Bus bus, NetworkService service, Cache cache) {
        try {
            view.showProgress();
            cache.evictAll();

            Observable<Response<LangResponse>> responseObservable = service.getLangs(Config.ACCESS_TOKEN, "ru");
            responseObservable.compose(RxUtil.applySchedulersAndRetry())
                    .subscribe(response -> {
                        int responseCode = response.code();

                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            LangResponse langResponse = response.body();

                            bus.send(new LoadLangsEvent(langResponse.getLangs()));
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                        bus.send(new ThrowableEvent(throwable));
                    });
        } catch (IOException ex) {
            ex.printStackTrace();
            bus.send(new ThrowableEvent(ex.getCause()));
        }
    }


}
