package com.quintype.moviebuff.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.quintype.moviebuff.R;
import com.quintype.moviebuff.activity.BaseActivity;
import com.quintype.moviebuff.activity.MovieBuffActivity;
import com.quintype.moviebuff.adapter.MovieListAdapter;
import com.quintype.moviebuff.model.MovieDetailsModel;
import com.quintype.moviebuff.parser.MovieResponseParser;
import com.quintype.moviebuff.util.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pachouri on 21/1/16.
 */
public class MovieBuffFragment extends Fragment implements MovieListAdapter.OnMovieItemClickedListener {

    @InjectView(R.id.edit_text_movie_name)
    protected EditText editTextSearchMovie;
    @InjectView(R.id.list_view_movie)
    protected RecyclerView listViewMovies;
    @InjectView(R.id.progress_view)
    protected ProgressBar progressBar;
    @InjectView(R.id.text_view_header_recent_search)
    protected TextView textViewHeaderRecentSearch;
    @InjectView(R.id.text_view_movie_title)
    protected TextView textViewTitle;
    @InjectView(R.id.image_view_movie_cover)
    protected SimpleDraweeView imageViewMoviePoster;
    @InjectView(R.id.layout_recent_search)
    protected RelativeLayout layoutRecentSearch;


    private static final String TAG = MovieBuffFragment.class.getSimpleName();
    private Context context;
    private MovieListAdapter adapter;
    private ArrayList<MovieResponseParser> movieParserList = new ArrayList<>();
    private String searchedMovieTitle = "";
    private View viewSnackBar;
    private boolean isSearchPerformed = false;

    public MovieBuffFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_buff, container, false);
        context = getActivity();
        ButterKnife.inject(this, view);
        initialiseViews();
        fetchMoviesFromDatabase();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Utils.ACTION_DONE);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    private void initialiseViews() {
        viewSnackBar = ((BaseActivity) context).findViewById(android.R.id.content);
        editTextSearchMovie.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Utils utils = new Utils();
                    utils.hideKeyboard(editTextSearchMovie, getActivity());
                    String movieTitle = editTextSearchMovie.getText().toString().trim();
                    if (movieTitle.isEmpty() || movieTitle.equals("")) {
                        ((BaseActivity) getActivity()).showSnackBar(getActivity(), viewSnackBar, getResources().getString(R.string.enter_title));
                    } else {
                        if (progressBar != null && getActivity() != null) {
                            progressBar.setVisibility(View.VISIBLE);
                            utils.fetchMoviesFromApi(getActivity(), movieTitle);
                        }

                        isSearchPerformed = true;
                    }
                    return true;
                }
                return false;
            }
        });


    }

    private void setAdapter() {
        if (progressBar != null) {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            } else {
                adapter = new MovieListAdapter(context, movieParserList, this);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                listViewMovies.setLayoutManager(manager);
                listViewMovies.setAdapter(adapter);
            }//
            progressBar.setVisibility(View.GONE);
        }
    }

    private void fetchMoviesFromDatabase() {
        if (progressBar != null) {
            ArrayList<MovieResponseParser> movieResponseParsers = MovieDetailsModel.fetchRecentSearch();

            if (movieResponseParsers != null && !movieResponseParsers.isEmpty()) {
                movieParserList.clear();
                movieParserList.addAll(movieResponseParsers);
                setAdapter();
            } else {
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
            }
            if (isSearchPerformed) {
                setValuesInCurrentSearch(movieResponseParsers);
            } else {
                hideCurrentSearchHeader();
            }
        }


    }

    private void setValuesInCurrentSearch(ArrayList<MovieResponseParser> movieResponseParsers) {
        if (movieResponseParsers != null && !movieResponseParsers.isEmpty()) {
            if (Utils.isResponse) {
                showCurrentSearchHeader();
                textViewHeaderRecentSearch.setText(Utils.RECENT_SEARCH);
                textViewTitle.setText(movieResponseParsers.get(0).getTitle());
                Uri uri = Uri.parse(movieResponseParsers.get(0).getPoster());
                imageViewMoviePoster.setImageURI(uri);
            } else {
                hideCurrentSearchHeader();
                textViewHeaderRecentSearch.setVisibility(View.VISIBLE);
                textViewHeaderRecentSearch.setText(getResources().getString(R.string.not_found));
            }

        }

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "in broadcast receiver");

            fetchMoviesFromDatabase();
        }
    };

    private void hideCurrentSearchHeader() {
        textViewHeaderRecentSearch.setVisibility(View.GONE);
        textViewTitle.setVisibility(View.GONE);
        imageViewMoviePoster.setVisibility(View.GONE);
        layoutRecentSearch.setVisibility(View.GONE);
    }

    private void showCurrentSearchHeader() {
        textViewHeaderRecentSearch.setVisibility(View.VISIBLE);
        textViewTitle.setVisibility(View.VISIBLE);
        imageViewMoviePoster.setVisibility(View.VISIBLE);
        layoutRecentSearch.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layout_recent_search)
    public void onLayoutClicked() {
        openMovieDetailsFragment(0);
    }

    @Override
    public void onMovieItemClicked(int position) {
        Log.v(TAG, "clicked: " + position);
        openMovieDetailsFragment(position);

    }

    private void openMovieDetailsFragment(int position) {
        MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(position);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content_view, fragment, MovieBuffActivity.FRAGMENT_TAG);
        transaction.addToBackStack(MovieBuffActivity.FRAGMENT_TAG);
        transaction.commit();
    }
}
