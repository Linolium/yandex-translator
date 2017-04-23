package com.github.linolium.yandex_translator.common.eventbus.events.dictionary;

import com.github.linolium.yandex_translator.domain.TranslateText;

/**
 * Created by linolium on 23.04.17.
 */

public class ShowClearDialogEvent {
    final private TranslateText translateText;

    public ShowClearDialogEvent(TranslateText translateText) {
        this.translateText = translateText;
    }

    public TranslateText getTranslateText() {
        return translateText;
    }
}
