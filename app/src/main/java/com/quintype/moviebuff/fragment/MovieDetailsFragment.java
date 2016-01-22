package com.quintype.moviebuff.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.quintype.moviebuff.R;
import com.quintype.moviebuff.model.MovieDetailsModel;
import com.quintype.moviebuff.parser.MovieResponseParser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pachouri on 21/1/16.
 */
public class MovieDetailsFragment extends Fragment {

    @InjectView(R.id.image_view_movie_cover)
    protected ImageView imageViewMovieCover;
    @InjectView(R.id.text_view_movie_title)
    protected TextView textViewTitle;
    @InjectView(R.id.text_view_plot)
    protected TextView textViewPlot;
    @InjectView(R.id.text_view_rating)
    protected TextView textViewRating;
    @InjectView(R.id.text_view_actors)
    protected TextView textViewActor;
    @InjectView(R.id.text_view_released)
    protected TextView textViewReleased;
    @InjectView(R.id.text_view_genre)
    protected TextView textViewGenre;
    @InjectView(R.id.text_view_director)
    protected TextView textViewDirector;
    @InjectView(R.id.text_view_writer)
    protected TextView textViewWriter;

    private static final String TAG = MovieDetailsFragment.class.getSimpleName();
    private Context context;
    private ArrayList<MovieResponseParser> movieParserList = new ArrayList<>();
    private int selectedPosition;
    private String movieTitle = "";
    private String release = "";
    private String genre = "";
    private String director = "";
    private String writer = "";
    private String actors = "";
    private String plot = "";
    private String poster = "";
    private String imdbRating = "";

    public static String BUNDLE_KEY_POSITION = "moviePosition";

    public MovieDetailsFragment() {

    }

    public static MovieDetailsFragment newInstance(int position) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle data = new Bundle();
        data.putInt(MovieDetailsFragment.BUNDLE_KEY_POSITION, position);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle data = getArguments();
        selectedPosition = data.getInt(MovieDetailsFragment.BUNDLE_KEY_POSITION);
        Log.v(TAG, "Position: " + selectedPosition);
        context = getActivity();
        ButterKnife.inject(this, view);
        fetchMovieDetailsFromDatabase();
        setValuesInControls();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private void setValuesInControls() {
        textViewTitle.setText(movieTitle);
        textViewReleased.setText(release);
        textViewGenre.setText(genre);
        textViewDirector.setText(director);
        textViewWriter.setText(writer);
        textViewActor.setText(actors);
        textViewPlot.setText(plot);
        textViewRating.setText(imdbRating);

        Picasso.with(context).load(poster).placeholder(android.R.drawable.ic_menu_gallery).error(android.R
                .drawable.ic_menu_close_clear_cancel).fit().centerCrop().into(imageViewMovieCover);

    }


    private void fetchMovieDetailsFromDatabase() {
        List<MovieDetailsModel> moviesList = new Select().from(MovieDetailsModel.class).execute();
        if (moviesList != null && !moviesList.isEmpty()) {
            int listSize = moviesList.size() - 1;
            Log.v(TAG, "Size: " + listSize);
            movieTitle = moviesList.get(listSize - selectedPosition).getTitle();
            release = moviesList.get(listSize - selectedPosition).getReleased();
            genre = moviesList.get(listSize - selectedPosition).getGenre();
            director = moviesList.get(listSize - selectedPosition).getDirector();
            writer = moviesList.get(listSize - selectedPosition).getWriter();
            actors = moviesList.get(listSize - selectedPosition).getActors();
            plot = moviesList.get(listSize - selectedPosition).getPlot();
            poster = moviesList.get(listSize - selectedPosition).getPoster();
            imdbRating = moviesList.get(listSize - selectedPosition).getImdbRating();
        }

    }


}
