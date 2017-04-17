package com.github.linolium.yandex_translator.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.github.linolium.yandex_translator.app.App;
import com.github.linolium.yandex_translator.common.Config;
import com.github.linolium.yandex_translator.common.LongWrapper;
import com.github.linolium.yandex_translator.common.eventbus.Bus;
import com.github.linolium.yandex_translator.network.NetworkService;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Linolium on 07.04.2017.
 */

@Module
public class AppModule {
    private final App app;
    private static final String PREF_NAME = "preferences";

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        Type token = new TypeToken<RealmList<LongWrapper>>(){}.getType();
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(token, new TypeAdapter<RealmList<LongWrapper>>() {

                    @Override
                    public void write(JsonWriter out, RealmList<LongWrapper> value) throws IOException {
                        out.beginArray();
                        for(LongWrapper longWrapper : value) {
                            out.value(longWrapper.getValue());
                        }
                        out.endArray();
                    }

                    @Override
                    public RealmList<LongWrapper> read(JsonReader in) throws IOException {
                        RealmList<LongWrapper> list = new RealmList<>();

                        in.beginArray();
                        while (in.hasNext()) {
                            list.add(new LongWrapper(in.nextLong()));
                        }
                        in.endArray();
                        return list;
                    }
                })
                .create();
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache() {
        File httpCacheDirectory = new File(app.getApplicationContext().getCacheDir(), Config.CACHE_FILE_NAME);
        return new Cache(httpCacheDirectory, Config.CACHE_FILE_SIZE);
    }

    @Provides
    @Named("cached")
    @Singleton
    NetworkService provideCachedMobukService(Cache cache,Gson gson) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // включаем нужные хэдеры в ответе
        builder.interceptors().add(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Accept", "application/json")
                    .cacheControl(new CacheControl.Builder()
                            .maxStale(Config.CACHE_TIME, TimeUnit.MINUTES) // кэш
                            .build())
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });

        // logging для http клиента TODO закомментировать в продакшен
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(interceptor);

        // устанавливаем кэш
        builder.cache(cache);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Config.apiURL)
                .client(builder.build())
                .build();

        return retrofit.create(NetworkService.class);
    }

    @Provides
    @Named("no_cached")
    @Singleton
    NetworkService provideMobukService(Gson gson) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // logging для http клиента TODO закомментировать в продакшен
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Config.apiURL)
                .client(builder.build())
                .build();

        return retrofit.create(NetworkService.class);
    }
}
