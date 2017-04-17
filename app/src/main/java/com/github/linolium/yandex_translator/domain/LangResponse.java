package com.github.linolium.yandex_translator.domain;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
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
public class LangResponse extends RealmObject implements Serializable {

    @PrimaryKey
    private long id;

    private RealmList<Lang> langs;
}
