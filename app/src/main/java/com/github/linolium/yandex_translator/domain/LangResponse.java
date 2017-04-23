package com.github.linolium.yandex_translator.domain;

import java.util.HashMap;
import java.util.List;

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
public class LangResponse {

    private List<String> dirs;
    private HashMap<String, String> langs;
}
