package com.github.linolium.yandex_translator.common.eventbus.events.translator;

/**
 * Created by Linolium on 14.04.2017.
 */

public class TranslateEvent {

    private String translatedWord;

    public TranslateEvent(String translatedWord) {
        this.translatedWord = translatedWord;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }
}
