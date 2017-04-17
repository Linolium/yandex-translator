package com.github.linolium.yandex_translator.common;

import io.realm.RealmObject;
import lombok.Getter;

/**
 * Created by Linolium on 17.04.2017.
 */

public class LongWrapper extends RealmObject {

    @Getter
    private long value;

    public LongWrapper(long value) {
        this.value = value;
    }

    public LongWrapper() {

    }
}
