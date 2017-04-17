package com.github.linolium.yandex_translator.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Linolium on 12.04.2017.
 */

@IntDef({MessageType.INFO, MessageType.ERROR})
@Retention(RetentionPolicy.SOURCE)
public @interface MessageType {
    int INFO = 0;
    int ERROR = 1;
}
