package com.github.linolium.yandex_translator.common.eventbus.events.translator;

import com.github.linolium.yandex_translator.domain.TranslateText;

import java.util.List;

/**
 * Created by Linolium on 14.04.2017.
 */

public class TranslateEvent {

    private List<TranslateText> textList;

    public TranslateEvent(List<TranslateText> textList) {
        this.textList = textList;
    }

    public List<TranslateText> getTextList() {
        return textList;
    }
}
