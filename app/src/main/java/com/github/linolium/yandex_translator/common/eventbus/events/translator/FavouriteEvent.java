package com.github.linolium.yandex_translator.common.eventbus.events.translator;

/**
 * Created by linolium on 20.04.17.
 */

public class FavouriteEvent {

    final private String text;

    public FavouriteEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
