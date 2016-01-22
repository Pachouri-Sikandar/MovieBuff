package com.quintype.moviebuff.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.quintype.moviebuff.BaseApplication;
import com.quintype.moviebuff.R;
import com.quintype.moviebuff.service.AppService;

/**
 * Created by pachouri on 21/1/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public AppService getService() {
        AppService service = ((BaseApplication) getApplicationContext()).service;
        if (service == null) {
            service = ((BaseApplication) getApplicationContext()).initializeService();
        }
        return service;
    }

    public void showSnackBar(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View v = snackbar.getView();
        v.setBackgroundColor(ContextCompat.getColor(context, R.color.snackBarColor));
        TextView textView = (TextView) v.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}