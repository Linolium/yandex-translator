package com.github.linolium.yandex_translator.domain;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by linolium on 20.04.17.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TranslateTextResponse {
    private int code;
    private String lang;
    private List<String> text;
}
