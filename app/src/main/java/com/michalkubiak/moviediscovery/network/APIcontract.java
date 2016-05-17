package com.michalkubiak.moviediscovery.network;

import com.michalkubiak.moviediscovery.BuildConfig;

/**
 * API contract returns URL to specific API's endpoints
 */
public class APIcontract {

    //TODO: Implement all API endpoints
    private static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;

    private static final String BASE_ADDRESS = "http://api.themoviedb.org/3/";
    private static final String BASE_IMAGES = "http://image.tmdb.org/t/p/";

    public static String getDiscoverMoviesURL(int pages) {

        String discoverMovieURL = BASE_ADDRESS + "discover/movie?page=" + pages + "&";

        return addAPIKey(discoverMovieURL);

    }

    /**
     * w185 is part of API and describes size of poster
     * @return returns full URL to a poster image
     */

    public static String getPosterImageURL (String imageEndpoint) {

        return BASE_IMAGES + "w185/" + imageEndpoint;

    }

    private static String addAPIKey (String noKeyURL) {

        String tagApiKey = "api_key";

        return noKeyURL + tagApiKey + "=" + API_KEY;

    }


}
