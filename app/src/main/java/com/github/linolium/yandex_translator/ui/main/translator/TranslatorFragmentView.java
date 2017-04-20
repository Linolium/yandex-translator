package com.github.linolium.yandex_translator.ui.main.translator;

import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.domain.Lang;
import com.github.linolium.yandex_translator.domain.TranslateText;

import java.util.List;

/**
 * Created by Linolium on 11.04.2017.
 */

public interface TranslatorFragmentView {
    void showProgress();
    void hideProgress();
    void showMessage(int message, @MessageType int type);
    void updateSpinners(List<Lang> langs);
    void updateRecyclerView(List<TranslateText> textList);
}
