package com.github.linolium.yandex_translator.common;

/**
 * Created by Linolium on 08.04.2017.
 */

public class Config {
    public static final String apiURL = "https://translate.yandex.net/api/v1.5/tr.json/";
    // URL Получение списка направлений перевода, поддерживаемых сервисом.
    public static final String GET_LANGS_URL = "https://translate.yandex.net/api/v1.5/tr.json/getLangs";
    // URL Определения языка, на котором написан заданный текст.
    public static final String DETECT_LANG_URL = "https://translate.yandex.net/api/v1.5/tr.json/detect";
    // URL Перевод текста на заданный язык.
    public static final String TRANSLATE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    // ключ для доступа к yandex api
    public static final String ACCESS_TOKEN = "trnsl.1.1.20170408T132438Z.34ee36c1bd39884c.d7bcd16d7ce61d111fe1601acfc33ab5606ffe96";
    // задержка при показе сплэш экрана в миллисекундах
    public static final int SPLASH_DELAY_MILLIS = 1000;
    // имя файла для http кэша
    public static final String CACHE_FILE_NAME = "yandex_translate_cache";
    // время хранения кэша в минутах
    public static final int CACHE_TIME = 1;
    // размер файла для кэша
    public static final long CACHE_FILE_SIZE = 10 * 1024 * 1024; // 10mb
}
