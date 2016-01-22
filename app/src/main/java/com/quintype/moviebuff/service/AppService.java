package com.quintype.moviebuff.service;

import com.quintype.moviebuff.parser.MovieResponseParser;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by pachouri on 21/1/16.
 */
public interface AppService {
    @GET("/?")
    void getMovieDetails(@QueryMap Map<String, String> mapParameters, Callback<MovieResponseParser> vocabCallback);
}