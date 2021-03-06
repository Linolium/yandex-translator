package com.github.linolium.yandex_translator.common.eventbus.events.translator;

import com.github.linolium.yandex_translator.domain.TranslateText;

/**
 * Created by linolium on 20.04.17.
 */

public class FavouriteEvent {

    final private TranslateText translateText;

    public FavouriteEvent(TranslateText translateText) {
        this.translateText = translateText;
    }

    public TranslateText getTranslateText() {
        return translateText;
    }
}
