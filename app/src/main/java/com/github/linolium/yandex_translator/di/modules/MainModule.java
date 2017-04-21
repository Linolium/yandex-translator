package com.github.linolium.yandex_translator.di.modules;

import com.github.linolium.yandex_translator.ui.main.MainActivity;
import com.github.linolium.yandex_translator.ui.main.MainPresenter;
import com.github.linolium.yandex_translator.ui.main.MainPresenterImpl;
import com.github.linolium.yandex_translator.ui.main.MainView;
import com.github.linolium.yandex_translator.ui.main.dictionary.DictionaryFragmentPresenter;
import com.github.linolium.yandex_translator.ui.main.dictionary.DictionaryFragmentPresenterImpl;
import com.github.linolium.yandex_translator.ui.main.history.HistoryFragment;
import com.github.linolium.yandex_translator.ui.main.history.HistoryFragmentPresenter;
import com.github.linolium.yandex_translator.ui.main.history.HistoryFragmentPresenterImpl;
import com.github.linolium.yandex_translator.ui.main.translator.TranslatorFragmentPresenter;
import com.github.linolium.yandex_translator.ui.main.translator.TranslatorFragmentPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Linolium on 07.04.2017.
 */

@Module
public class MainModule {
    private MainActivity activity;

    public MainModule(MainActivity activity) {
        this.activity = activity;
    }

    /** Provide MainView */
    @Provides
    MainView provideMainView() {
        return activity;
    }

    /** Provide MainPresenterImpl */
    @Provides
    MainPresenter provideMainPresenterImpl(MainView view) {
        return new MainPresenterImpl(view);
    }

    /**
     * Provide TranslatorFragmentPresenterImpl
     */
    @Provides
    TranslatorFragmentPresenter provideTranslatorFragmentPresenterImpl() {
        return new TranslatorFragmentPresenterImpl();
    }

    /**
     * Provide DictionaryFragmentPresenterImpl
     */
    @Provides
    DictionaryFragmentPresenter provideDictionaryFragmentPresenterImpl() {
        return new DictionaryFragmentPresenterImpl();
    }

    /**
     * Provide HistoryFragmentPresenterImpl
     */
    @Provides
    HistoryFragmentPresenter provideHistoryFragmentPresenterImpl() {
        return new HistoryFragmentPresenterImpl();
    }
}
