package com.michalkubiak.moviediscovery.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.michalkubiak.moviediscovery.network.APIcontract;

/**
 * A simple pojo class representing a movie
 */
public class MovieItem implements APIElement, Parcelable{
    //TODO: finish parcelable implementation
    private int id;
    private String posterPath;
    private String originalTitle;
    private String voteAverage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = APIcontract.getPosterImageURL(posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(voteAverage);
    }
    public MovieItem(){

    }

    private MovieItem (Parcel in){
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.originalTitle = in.readString();
        this.voteAverage = in.readString();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new  MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
