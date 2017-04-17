package com.github.linolium.yandex_translator.common.eventbus.events.translator;

import com.github.linolium.yandex_translator.domain.Lang;

import java.util.List;

/**
 * Created by Linolium on 14.04.2017.
 */

public class LoadLangsEvent {
    private final List<Lang> langList;

    public LoadLangsEvent(List<Lang> langList) {
        this.langList = langList;
    }

    public List<Lang> getLangList() {
        return langList;
    }
}
