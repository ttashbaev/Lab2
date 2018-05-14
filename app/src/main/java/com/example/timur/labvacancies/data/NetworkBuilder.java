package com.example.timur.labvacancies.data;

import com.example.timur.labvacancies.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Timur on 30.04.2018.
 */

public class NetworkBuilder {
    private static AuService service = null;



    public static AuService initService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (service == null) {
            service = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(AuService.class);
        }
        return service;
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request()
                                .newBuilder()
                                .addHeader("Accept", "application/json;versions=1");
                        return chain.proceed(builder.build());
                    }

                })
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
