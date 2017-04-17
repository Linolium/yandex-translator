package com.github.linolium.yandex_translator.common.eventbus;

/**
 * Created by Linolium on 08.04.2017.
 */

import javax.inject.Inject;

import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

public class Bus extends SerializedSubject<Object, Object> {

    @Inject
    public Bus() {
        super(PublishSubject.create());
    }

    public void send(Object o) {
        this.onNext(o);
    }
}
