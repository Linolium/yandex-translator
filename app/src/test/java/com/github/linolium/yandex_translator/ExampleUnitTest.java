package com.github.linolium.yandex_translator;

import com.github.linolium.yandex_translator.common.Config;
import com.github.linolium.yandex_translator.domain.Lang;
import com.github.linolium.yandex_translator.domain.LangResponse;
import com.github.linolium.yandex_translator.network.NetworkService;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        List<Lang> langList = new ArrayList<Lang>();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Config.apiURL)
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);

        Observable<Response<LangResponse>> responseObservable = networkService.getLangs(Config.API_KEY, "ru");
        responseObservable
                .subscribe(response -> {
                    int responseCode = response.code();
                    System.out.println(responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        LangResponse langResponse = response.body();
                        langResponse.getLangs().forEach((k, v) -> langList.add(new Lang(k, v)));
                        System.out.println(1);
                    }
                });
        System.out.println(langList);
    }
}