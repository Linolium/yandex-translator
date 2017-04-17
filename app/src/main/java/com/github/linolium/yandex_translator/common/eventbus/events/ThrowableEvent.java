package com.github.linolium.yandex_translator.common.eventbus.events;

/**
 * Created by Linolium on 14.04.2017.
 */

public class ThrowableEvent {

    private final Throwable trowable;

    public ThrowableEvent(Throwable trowable) {
        this.trowable = trowable;
    }

    public Throwable getTrowable() {
        return trowable;
    }
}
