package com.github.linolium.yandex_translator.ui.main.translator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.app.BaseFragment;
import com.github.linolium.yandex_translator.common.Config;
import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.common.adapters.LangAdapter;
import com.github.linolium.yandex_translator.di.components.MainComponent;
import com.github.linolium.yandex_translator.domain.Lang;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding.widget.RxAdapter;
import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.util.List;

import javax.inject.Inject;

import okhttp3.Cache;
import rx.Subscription;


/**
 * Created by Linolium on 11.04.2017.
 */

public class TranslatorFragment extends BaseFragment implements TranslatorFragmentView {

    private ProgressBar progressBar;

    private Subscription eventSubscription;

    private LangAdapter langAdapter;
    private List<Lang> langs;
    private Activity activity;
    private Toolbar toolbar;

    @Inject
    TranslatorFragmentPresenter presenter;
    @Inject
    Cache cache;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = context instanceof Activity ? (Activity) context : null;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
        eventSubscription = presenter.subscribeToBus(bus);
    }

    @Override
    public void onPause() {
        if (eventSubscription != null && !eventSubscription.isUnsubscribed())
            eventSubscription.unsubscribe();
        super.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(MainComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translator, container, false);

        // toolbar
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setTitle("");
        }

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        Spinner spinner = (Spinner) view.findViewById(R.id.toolbar_spinner);

        langAdapter = new LangAdapter(activity, langs);
        spinner.setAdapter(langAdapter);
        RxAdapterView.itemSelections(spinner)
                .filter(integer -> langAdapter.getCount() != 0)
                .subscribe(integer -> {
                    Lang lang = langAdapter.getItem(integer);
                    presenter.updateLangs(preferences, bus,
                            cachedNetworkService, cache);
                    getActivity().invalidateOptionsMenu();
                });

        return view;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(int message, @MessageType int type) {
        showToast(message, type);
    }

    @Override
    public void updateLangs(List<Lang> langList) {
        langs = langList;
    }
}
