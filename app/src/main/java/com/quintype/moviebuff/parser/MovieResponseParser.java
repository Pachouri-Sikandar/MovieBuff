package com.quintype.moviebuff.parser;

import com.google.gson.annotations.SerializedName;
import com.quintype.moviebuff.model.MovieDetailsModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pachouri on 21/1/16.
 */

public class MovieResponseParser {

    @SerializedName("Title")
    private String Title;
    @SerializedName("Year")
    private String Year;
    @SerializedName("Rated")
    private String Rated;
    @SerializedName("Released")
    private String Released;
    @SerializedName("Runtime")
    private String Runtime;
    @SerializedName("Genre")
    private String Genre;
    @SerializedName("Director")
    private String Director;
    @SerializedName("Writer")
    private String Writer;
    @SerializedName("Actors")
    private String Actors;
    @SerializedName("Plot")
    private String Plot;
    @SerializedName("Language")
    private String Language;
    @SerializedName("Country")
    private String Country;
    @SerializedName("Awards")
    private String Awards;
    @SerializedName("Poster")
    private String Poster;
    @SerializedName("Metascore")
    private String Metascore;
    @SerializedName("imdbRating")
    private String imdbRating;
    @SerializedName("imdbVotes")
    private String imdbVotes;
    @SerializedName("imdbID")
    private String imdbID;
    @SerializedName("Type")
    private String Type;
    @SerializedName("Response")
    private String Response;

    public MovieResponseParser() {
    }

    public MovieResponseParser(MovieDetailsModel movieDetailsModel) {
        this.Title = movieDetailsModel.getTitle();
        this.Year = movieDetailsModel.getYear();
        this.Rated = movieDetailsModel.getRated();
        this.Released = movieDetailsModel.getReleased();
        this.Runtime = movieDetailsModel.getRuntime();
        this.Genre = movieDetailsModel.getGenre();
        this.Director = movieDetailsModel.getDirector();
        this.Writer = movieDetailsModel.getWriter();
        this.Actors = movieDetailsModel.getActors();
        this.Plot = movieDetailsModel.getPlot();
        this.Language = movieDetailsModel.getLanguage();
        this.Country = movieDetailsModel.getCountry();
        this.Awards = movieDetailsModel.getAwards();
        this.Poster = movieDetailsModel.getPoster();
        this.Metascore = movieDetailsModel.getMetaScore();
        this.imdbRating = movieDetailsModel.getImdbRating();
        this.imdbVotes = movieDetailsModel.getImdbVotes();
        this.imdbID = movieDetailsModel.getImdbId();
        this.Type = movieDetailsModel.getType();
        this.Response = movieDetailsModel.getResponse();
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setMetascore(String metascore) {
        Metascore = metascore;
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

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
