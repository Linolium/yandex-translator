package com.github.linolium.yandex_translator.ui.main.dictionary;

import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.domain.TranslateText;

import java.util.List;

/**
 * Created by Linolium on 21.04.2017.
 */

public interface DictionaryFragmentView {
    void showProgress();
    void hideProgress();
    void showMessage(int message, @MessageType int type);
    void getTranslatedTexts(List<TranslateText> translateTexts);
}
