package com.quintype.moviebuff.model;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;
import com.quintype.moviebuff.parser.MovieResponseParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pachouri on 21/1/16.
 */
public class MovieDetailsModel extends Model {
    private static final String TAG = MovieDetailsModel.class.getSimpleName();

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private String year;

    @Column(name = "rated")
    private String rated;

    @Column(name = "released")
    private String released;

    @Column(name = "runtime")
    private String runtime;

    @Column(name = "genre")
    private String genre;

    @Column(name = "director")
    private String director;

    @Column(name = "writer")
    private String writer;

    @Column(name = "actors")
    private String actors;

    @Column(name = "plot")
    private String plot;

    @Column(name = "language")
    private String language;

    @Column(name = "country")
    private String country;

    @Column(name = "awards")
    private String awards;

    @Column(name = "poster")
    private String poster;

    @Column(name = "meta_score")
    private String metaScore;

    @Column(name = "imdb_rating")
    private String imdbRating;

    @Column(name = "imdb_votes")
    private String imdbVotes;

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "type")
    private String type;

    @Column(name = "response")
    private String response;

    public MovieDetailsModel() {
    }

    public MovieDetailsModel(MovieResponseParser movieResponseParser) {
        this.title = movieResponseParser.getTitle();
        this.year = movieResponseParser.getYear();
        this.rated = movieResponseParser.getRated();
        this.released = movieResponseParser.getReleased();
        this.runtime = movieResponseParser.getRuntime();
        this.genre = movieResponseParser.getGenre();
        this.director = movieResponseParser.getDirector();
        this.writer = movieResponseParser.getWriter();
        this.actors = movieResponseParser.getActors();
        this.plot = movieResponseParser.getPlot();
        this.language = movieResponseParser.getLanguage();
        this.country = movieResponseParser.getCountry();
        this.awards = movieResponseParser.getAwards();
        this.poster = movieResponseParser.getAwards();
        this.metaScore = movieResponseParser.getMetascore();
        this.imdbRating = movieResponseParser.getImdbRating();
        this.imdbVotes = movieResponseParser.getImdbVotes();
        this.imdbId = movieResponseParser.getImdbID();
        this.type = movieResponseParser.getType();
        this.response = movieResponseParser.getResponse();
    }

    public static void saveMovieDetails(MovieResponseParser movieResponseParser) {
        if (movieResponseParser != null) {
            ActiveAndroid.beginTransaction();
            try {
                new MovieDetailsModel(movieResponseParser).save();
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
        }
    }

    public static ArrayList<MovieResponseParser> fetchRecentSearch() {
        ArrayList<MovieResponseParser> movieResponseParsers = new ArrayList<>();
        List<MovieDetailsModel> moviesList = new Select().from(MovieDetailsModel.class).execute();

        if (moviesList != null && !moviesList.isEmpty()) {
            int size = moviesList.size();
            for (int loop = size - 1; loop >= 0; loop--) {
                Log.i(TAG, "in for loop " + loop);
                movieResponseParsers.add(new MovieResponseParser(moviesList.get(loop)));
            }
            Log.i(TAG, " model list size " + moviesList.size());
        }


        return movieResponseParsers;
    }

    public static String getTag() {
        return TAG;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(String metaScore) {
        this.metaScore = metaScore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
