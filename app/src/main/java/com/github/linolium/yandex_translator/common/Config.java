package com.github.linolium.yandex_translator.common;

/**
 * Created by Linolium on 08.04.2017.
 */

public class Config {
    public static final String apiURL = "https://translate.yandex.net/api/v1.5/tr.json/";
    // ключ для доступа к yandex api
    public static final String API_KEY = "trnsl.1.1.20170408T132438Z.34ee36c1bd39884c.d7bcd16d7ce61d111fe1601acfc33ab5606ffe96";
    // задержка при показе сплэш экрана в миллисекундах
    public static final int SPLASH_DELAY_MILLIS = 3000;
    // имя файла для http кэша
    public static final String CACHE_FILE_NAME = "yandex_translate_cache";
    // время хранения кэша в минутах
    public static final int CACHE_TIME = 1;
    // размер файла для кэша
    public static final long CACHE_FILE_SIZE = 10 * 1024 * 1024; // 10mb
    public static final String FROM_LANG_POS = "fromLangPosition";
    public static final String TO_LANG_POS = "toLangPosition";
}
