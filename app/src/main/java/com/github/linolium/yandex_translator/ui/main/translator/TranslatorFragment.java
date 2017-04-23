package com.github.linolium.yandex_translator.ui.main.translator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.github.linolium.yandex_translator.R;
import com.github.linolium.yandex_translator.app.BaseFragment;
import com.github.linolium.yandex_translator.common.Config;
import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.common.adapters.LangAdapter;
import com.github.linolium.yandex_translator.common.adapters.TranslateTextAdapter;
import com.github.linolium.yandex_translator.di.components.MainComponent;
import com.github.linolium.yandex_translator.domain.Lang;
import com.github.linolium.yandex_translator.domain.TranslateText;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import okhttp3.Cache;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Linolium on 11.04.2017.
 */

public class TranslatorFragment extends BaseFragment implements TranslatorFragmentView {

    private ProgressBar progressBar;

    private Subscription eventSubscription;

    private LangAdapter langAdapter;
    private TranslateTextAdapter textAdapter;
    private Activity activity;
    private Spinner fromLang;
    private ImageButton switchLangButton;
    private Spinner toLang;
    private EditText enterTextArea;
    private RecyclerView recyclerView;

    @Inject
    TranslatorFragmentPresenter presenter;
    @Inject
    Cache cache;
    Realm realm;

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
        realm = Realm.getDefaultInstance();
        presenter.init(this);
        eventSubscription = presenter.subscribeToBus(bus, preferences, realm);
        presenter.loadLangs(networkService, bus, preferences, realm);
    }

    @Override
    public void onPause() {
        if (eventSubscription != null && !eventSubscription.isUnsubscribed())
            eventSubscription.unsubscribe();
//        if (realm != null) realm.close();
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
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        fromLang = (Spinner) view.findViewById(R.id.from_lang);
        switchLangButton = (ImageButton) view.findViewById(R.id.switchLangButton);
        toLang = (Spinner) view.findViewById(R.id.to_lang);
        enterTextArea = (EditText) view.findViewById(R.id.translateEditText);


        RxView.clicks(switchLangButton).subscribe(aVoid -> {
            int tempPos = fromLang.getSelectedItemPosition();
            fromLang.setSelection(toLang.getSelectedItemPosition());
            toLang.setSelection(tempPos);
            presenter.setDefaultLangs(Config.FROM_LANG_POS, fromLang.getSelectedItemPosition(), preferences);
            presenter.setDefaultLangs(Config.TO_LANG_POS, toLang.getSelectedItemPosition(), preferences);
            if (enterTextArea.getText().length() > 0) {
                presenter.loadTranslatedList(
                        networkService,
                        bus,
                        ((Lang)fromLang.getSelectedItem()).getKey() + "-" + ((Lang)toLang.getSelectedItem()).getKey(),
                        enterTextArea.getText().toString());
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.rvTranslate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        enterTextArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (enterTextArea.getText().length() > 0) {
                    presenter.loadTranslatedList(
                            networkService,
                            bus,
                            ((Lang)fromLang.getSelectedItem()).getKey() + "-" + ((Lang)toLang.getSelectedItem()).getKey(),
                            enterTextArea.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        enterTextArea.setOnFocusChangeListener((view1, hasFocus) -> {
            if (!hasFocus && textAdapter != null) {
                presenter.updateHistory(bus, realm, textAdapter.getItem(0));
            }
        });

        fromLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.setDefaultLangs(Config.FROM_LANG_POS, fromLang.getSelectedItemPosition(), preferences);
                if (enterTextArea.getText().length() > 0) {
                    presenter.loadTranslatedList(
                            networkService,
                            bus,
                            ((Lang)fromLang.getSelectedItem()).getKey() + "-" + ((Lang)toLang.getSelectedItem()).getKey(),
                            enterTextArea.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.setDefaultLangs(Config.TO_LANG_POS, toLang.getSelectedItemPosition(), preferences);
                if (enterTextArea.getText().length() > 0) {
                    presenter.loadTranslatedList(
                            networkService,
                            bus,
                            ((Lang)fromLang.getSelectedItem()).getKey() + "-" + ((Lang)toLang.getSelectedItem()).getKey(),
                            enterTextArea.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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
    public void updateSpinners(List<Lang> langs) {
        langAdapter = new LangAdapter(activity, langs);
        fromLang.setAdapter(langAdapter);
        toLang.setAdapter(langAdapter);
        fromLang.setSelection(preferences.getInt(Config.FROM_LANG_POS, 0));
        toLang.setSelection(preferences.getInt(Config.TO_LANG_POS, 1));
    }

    @Override
    public void updateRecyclerView(List<TranslateText> textList) {
        textAdapter = new TranslateTextAdapter(textList, activity, bus);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(textAdapter);
    }

    @Override
    public void clearEditText() {
        enterTextArea.setText("");
        recyclerView.setAdapter(null);
    }

    @Override
    public void showLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity)
                .setTitle(R.string.error)
                .setMessage(R.string.langError)
                .setCancelable(false)
                .setPositiveButton(R.string.repeat, (dialog, which) -> presenter.loadLangs(networkService, bus, preferences, realm));

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
