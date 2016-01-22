package com.quintype.moviebuff.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.quintype.moviebuff.BaseApplication;
import com.quintype.moviebuff.R;
import com.quintype.moviebuff.activity.BaseActivity;
import com.quintype.moviebuff.model.MovieDetailsModel;
import com.quintype.moviebuff.parser.MovieResponseParser;
import com.quintype.moviebuff.service.AppService;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by pachouri on 21/1/16.
 */
public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    public static final String ACTION_DONE = "Utils#ACTION_DONE";
    public static final String INTENT_BROAD_CAST = "intent_broad_cast";

    public static final String RECENT_SEARCH = "Recent Search: ";
    public static final String PREVIOUS_SEARCH = "Search History: ";
    public static boolean isResponse = false;

    public void fetchMoviesFromApi(Context context, String movieTitle) {
        isResponse = false;
        if (isNetworkAvailable(context)) {
            AppService service;
            if (context instanceof BaseActivity) {
                service = ((BaseActivity) context).getService();
            } else {
                service = ((BaseApplication) context).service;
            }

            Map<String, String> mapParameters = new HashMap<>();
            mapParameters.put("t", movieTitle);
            mapParameters.put("y", "");
            mapParameters.put("plot", "");
            mapParameters.put("r", "");
            mapParameters.put("type", "movie");

            service.getMovieDetails(mapParameters, getCallBack(context));
        } else if (context instanceof BaseActivity) {
            View view = ((BaseActivity) context).findViewById(android.R.id.content);
            ((BaseActivity) context).showSnackBar(context, view, context.getString(R.string
                    .network_error));
        }
    }


    private Callback<MovieResponseParser> getCallBack(final Context context) {
        return new Callback<MovieResponseParser>() {
            @Override
            public void success(MovieResponseParser movieResponseParser, Response response) {
                View view = ((BaseActivity) context).findViewById(android.R.id.content);
                if (movieResponseParser != null) {

                    if (movieResponseParser.getResponse().equalsIgnoreCase("false")) {
                        isResponse = false;
                        ((BaseActivity) context).showSnackBar(context, view, context.getString(R.string
                                .not_found));
                    } else {
                        isResponse = true;
                        saveMovieDetailsInDB(movieResponseParser);
                    }

                    Gson gson = new Gson();
                    Intent intent = new Intent();
                    intent.setAction(ACTION_DONE);
                    intent.putExtra(INTENT_BROAD_CAST, gson.toJson(movieResponseParser));

                    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
                    manager.sendBroadcast(intent);

                } else if (context instanceof BaseActivity) {

                    ((BaseActivity) context).showSnackBar(context, view, context.getString(R.string
                            .error_message));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context instanceof BaseActivity) {
                    View view = ((BaseActivity) context).findViewById(android.R.id.content);
                    ((BaseActivity) context).showSnackBar(context, view, context.getString(R.string
                            .error_message));
                }
            }
        };
    }

    private void saveMovieDetailsInDB(final MovieResponseParser movieResponseParser) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MovieDetailsModel.saveMovieDetails(movieResponseParser);
                return null;
            }
        }.execute();
    }

    public boolean isNetworkAvailable(Context context) {

        boolean isNetworkConnected = false;
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            if (connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo()
                    .isAvailable() && connManager.getActiveNetworkInfo().isConnected()) {
                isNetworkConnected = true;
            }
        } catch (Exception ex) {
            isNetworkConnected = false;
        }
        return isNetworkConnected;
    }

    public void hideKeyboard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
