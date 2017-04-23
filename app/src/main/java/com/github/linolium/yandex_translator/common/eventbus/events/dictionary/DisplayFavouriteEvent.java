package com.github.linolium.yandex_translator.common.eventbus.events.dictionary;

import com.github.linolium.yandex_translator.domain.TranslateText;

import java.util.List;

/**
 * Created by Linolium on 22.04.2017.
 */

public class DisplayFavouriteEvent {
    private List<TranslateText> textList;

    public DisplayFavouriteEvent(List<TranslateText> textList) {
        this.textList = textList;
    }

    public List<TranslateText> getTextList() {
        return textList;
    }
}
