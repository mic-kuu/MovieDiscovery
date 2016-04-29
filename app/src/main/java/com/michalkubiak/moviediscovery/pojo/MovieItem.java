package com.michalkubiak.moviediscovery.pojo;

import com.michalkubiak.moviediscovery.network.APIcontract;

/**
 * A simple pojo class representing a movie
 */
public class MovieItem {

    private String id;
    private String posterPath;
    private String orginalTitle;
    private String voteAverage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = APIcontract.getPosterImageURL(posterPath);
    }

    public String getOrginalTitle() {
        return orginalTitle;
    }

    public void setOrginalTitle(String orginalTitle) {
        this.orginalTitle = orginalTitle;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }
}
