package com.github.linolium.yandex_translator.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by linolium on 20.04.17.
 */
@Getter
@Setter
@ToString
public class TranslateText {
   private String text;

    public TranslateText(String text) {
        this.text = text;
    }
}
