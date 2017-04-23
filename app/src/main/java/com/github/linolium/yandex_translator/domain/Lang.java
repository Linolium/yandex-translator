package com.github.linolium.yandex_translator.domain;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Linolium on 14.04.2017.
 */

@Getter
@Setter
@ToString
public class Lang extends RealmObject implements Serializable {
    private String key;
    private String value;

    public Lang(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Lang() {
    }
}
