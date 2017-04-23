package com.github.linolium.yandex_translator.domain;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by linolium on 20.04.17.
 */
@Getter
@Setter
@ToString
public class TranslateText extends RealmObject implements Serializable {
    @PrimaryKey
    @Required
    private Integer id;
    private String translatedText;
    private String enteredText;
    private String fromToCode;
    private boolean isFavourite;

    public TranslateText(String translatedText, String enteredText, String fromToCode) {
        this.translatedText = translatedText;
        this.enteredText = enteredText;
        this.fromToCode = fromToCode;
    }

    public TranslateText() {
    }

    public static Integer getNextKey(Realm realm)
    {
        try {
            if (realm.where(TranslateText.class).max("id") == null) {
                return 1;
            }
            return realm.where(TranslateText.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e)
        { return 0; }
    }
}
