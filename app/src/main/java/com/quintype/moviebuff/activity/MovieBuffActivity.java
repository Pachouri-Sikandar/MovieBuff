package com.quintype.moviebuff.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.quintype.moviebuff.R;
import com.quintype.moviebuff.fragment.MovieBuffFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MovieBuffActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    public static final String FRAGMENT_TAG = "fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_buff);
        ButterKnife.inject(this);
        setToolbar();
        setFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
    }

    private void setFragment() {
        MovieBuffFragment fragment = new MovieBuffFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content_view, fragment, FRAGMENT_TAG);
        transaction.commit();
    }
}
