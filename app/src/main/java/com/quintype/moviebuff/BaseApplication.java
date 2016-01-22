package com.quintype.moviebuff;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quintype.moviebuff.service.AppService;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by pachouri on 21/1/16.
 */
public class BaseApplication extends Application {
    private static final String BASE_URL = "http://www.omdbapi.com";

    public RestAdapter restAdapter;
    public AppService service;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        restAdapter = initializeRestAdapter();
        service = initializeService();
    }

    private RestAdapter initializeRestAdapter() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(BASE_URL);
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        } else {
            builder.setLogLevel(RestAdapter.LogLevel.NONE);
        }
        builder.setClient(new OkClient(okHttpClient));
        builder.setConverter(new GsonConverter(gson));
        return builder.build();
    }

    public AppService initializeService() {
        service = restAdapter.create(AppService.class);
        return service;
    }
}
