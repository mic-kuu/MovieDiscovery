package com.michalkubiak.moviediscovery;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.michalkubiak.moviediscovery.network.APIcontract;
import com.michalkubiak.moviediscovery.network.AsyncResponse;
import com.michalkubiak.moviediscovery.network.MovieDetailsParser;
import com.michalkubiak.moviediscovery.network.MovieListParser;
import com.michalkubiak.moviediscovery.network.RetrieveMovieDBAPI;
import com.michalkubiak.moviediscovery.pojo.MovieDetails;
import com.michalkubiak.moviediscovery.pojo.MovieItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncResponse, MovieListFragment.MovieListInterface {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<MovieItem> movieList;
    private RetrieveMovieDBAPI retrieveMovieDBAPI = new RetrieveMovieDBAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (movieList != null) addMovieListFragment();
        else retrieveMovieDBAPI();
    }

    private void retrieveMovieDBAPI(){
        retrieveMovieDBAPI.delegate = this;
        retrieveMovieDBAPI.setAPIEndpoint(APIcontract.getDiscoverMoviesURL(5));
        retrieveMovieDBAPI.setResponseType(retrieveMovieDBAPI.MOVIEGRID);
        if (isOnline()){
            retrieveMovieDBAPI.execute();
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.all_error_nointernet), Toast.LENGTH_SHORT).show();
            //TODO: Additional error handling
        }
    }


    private void addMovieListFragment(){
        Fragment movieListFragment = MovieListFragment.newInstance(movieList);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.gridViewPlaceholder, movieListFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void processOutput(String output) {
        MovieListParser movieListParser = new MovieListParser(output);

        if (movieListParser.parse()){
            movieList = movieListParser.getResultList();
            addMovieListFragment();

        } else {

            Toast.makeText(this, "There was a problem parsing json", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void processDetailed(String output) {
        MovieDetailsParser movieDetailsParser = new MovieDetailsParser(output);

        if (movieDetailsParser.parse()){
            MovieDetails movieDetails = movieDetailsParser.getMovieDetails();
            Toast.makeText(MainActivity.this, "DONE!!!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(MainActivity.this, "There was a problem parsing json", Toast.LENGTH_SHORT).show();
        }


    }

    //TODO: move isOnline to a better place
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onDetailedViewSelected(int movieId) {

    }
}