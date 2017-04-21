package com.github.linolium.yandex_translator.common.eventbus.events.translator;

import com.github.linolium.yandex_translator.domain.TranslateText;

/**
 * Created by Linolium on 21.04.2017.
 */

public class HistoryEvent {

    final private TranslateText translateText;

    public HistoryEvent(TranslateText translateText) {
        this.translateText = translateText;
    }

    public TranslateText getTranslateText() {
        return translateText;
    }
}
