package com.michalkubiak.moviediscovery;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import com.michalkubiak.moviediscovery.network.AsyncResponse;
import com.michalkubiak.moviediscovery.network.JsonParser;
import com.michalkubiak.moviediscovery.network.RetrieveAPITask;
import com.michalkubiak.moviediscovery.pojo.MovieItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<MovieItem> movieList;
    private GridViewAdapter gridViewAdapter;
    private GridView gridView;

    private RetrieveAPITask retrieveAPITask = new RetrieveAPITask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);

        retrieveAPITask.delegate = this;
        if (isOnline()){
            retrieveAPITask.execute();

        } else {

            Toast.makeText(MainActivity.this, getString(R.string.all_error_nointernet), Toast.LENGTH_SHORT).show();
            //TODO: Additional error handling
        }

    }

    @Override
    public void processOutput(String output) {
        JsonParser jsonParser = new JsonParser(output);
        jsonParser.parse();
        movieList = jsonParser.getResultList();
        updateImages();
    }


    private void updateImages() {

        if (gridViewAdapter == null) {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, movieList);
            gridView.setAdapter(gridViewAdapter);
        }

    }
    //TODO: move isOnline to a better place
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}