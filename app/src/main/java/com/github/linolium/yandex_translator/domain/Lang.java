package com.github.linolium.yandex_translator.domain;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Linolium on 14.04.2017.
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Lang extends RealmObject implements Serializable {
    private String key;
    private String value;
}
