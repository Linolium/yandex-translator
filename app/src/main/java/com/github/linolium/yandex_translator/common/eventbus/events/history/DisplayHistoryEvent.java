package com.github.linolium.yandex_translator.common.eventbus.events.history;

import com.github.linolium.yandex_translator.domain.TranslateText;

import java.util.List;

/**
 * Created by Linolium on 22.04.2017.
 */

public class DisplayHistoryEvent {

    private List<TranslateText> textList;

    public DisplayHistoryEvent(List<TranslateText> textList) {
        this.textList = textList;
    }

    public List<TranslateText> getTextList() {
        return textList;
    }
}
