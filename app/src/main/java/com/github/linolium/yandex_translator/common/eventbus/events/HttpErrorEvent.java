package com.github.linolium.yandex_translator.common.eventbus.events;

/**
 * Created by Linolium on 14.04.2017.
 */

public class HttpErrorEvent {

    private final int code;

    public HttpErrorEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
