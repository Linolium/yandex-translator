package com.github.linolium.yandex_translator.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.di.components.AppComponent;
import com.github.linolium.yandex_translator.network.NetworkService;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;

/**
 * Created by Linolium on 07.04.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    public Gson gson;
    @Inject
    public Bus bus;
//    @Inject
//    public Realm realm;
    @Inject
    public SharedPreferences preferences;
    @Inject @Named("no_cached")
    public NetworkService networkService;
    @Inject @Named("cached")
    public NetworkService cachedNetworkService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(App.get(this).getAppComponent());
    }

    protected abstract void setupComponent(AppComponent appComponent);

    public void showToast(int message, @MessageType int type) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        toastView.setBackgroundResource(type == MessageType.ERROR ? R.drawable.toast_error_bg : R.drawable.toast_info_bg);
        toast.show();
    }
}
