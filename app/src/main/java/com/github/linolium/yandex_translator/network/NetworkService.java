package com.github.linolium.yandex_translator.network;

import com.github.linolium.yandex_translator.domain.LangResponse;
import com.github.linolium.yandex_translator.domain.TranslateTextResponse;

import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Linolium on 14.04.2017.
 */

public interface NetworkService {

    @POST("getLangs")
    Observable<Response<LangResponse>> getLangs(@Query("key") String apiKey, @Query("ui") String langCode);

    @POST("translate")
    Observable<Response<TranslateTextResponse>> translate(@Query("lang") String lang,
                                                          @Query("key") String apiKey,
                                                          @Query("text") String text);
}
