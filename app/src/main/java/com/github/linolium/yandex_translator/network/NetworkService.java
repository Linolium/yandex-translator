package com.github.linolium.yandex_translator.network;

import com.github.linolium.yandex_translator.domain.LangResponse;

import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Linolium on 14.04.2017.
 */

public interface NetworkService {

    @POST
    Observable<Response<LangResponse>> getLangs(@Query("key") String apiKey, @Query("ui") String langCode);
}
