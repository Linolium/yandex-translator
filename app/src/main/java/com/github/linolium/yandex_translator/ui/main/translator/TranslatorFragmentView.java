package com.github.linolium.yandex_translator.ui.main.translator;

import com.github.linolium.yandex_translator.common.MessageType;
import com.github.linolium.yandex_translator.domain.Lang;

import java.util.List;

/**
 * Created by Linolium on 11.04.2017.
 */

public interface TranslatorFragmentView {
    void showProgress();
    void hideProgress();
    void showMessage(int message, @MessageType int type);
    void updateLangs(List<Lang> langList);
}
