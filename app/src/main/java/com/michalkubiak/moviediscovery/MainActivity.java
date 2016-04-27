package com.michalkubiak.moviediscovery;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.michalkubiak.moviediscovery.network.RetriveAPITask;


import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<HashMap<String, String>> movieList;
    private ArrayList<String> posterThumbnails;
    private GridViewAdapter gridViewAdapter;
    private GridView gridView;

    RetriveAPITask retriveAPITask = new RetriveAPITask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);

        retriveAPITask.delegate = this;
        if (isOnline()){
            retriveAPITask.execute();

        } else {

            Toast.makeText(MainActivity.this, getString(R.string.all_error_nointernet), Toast.LENGTH_SHORT).show();
            //TODO: Additional error handling
        }

    }

    @Override
    public void processOutput(String output) {
        JsonParser jsonParser = new JsonParser(output);
        jsonParser.parse();

        posterThumbnails = jsonParser.getPosterThumbnails();
        movieList = jsonParser.getResultList();
        updateImages();
       /* for (HashMap<String, String> element : movieList ){
            Log.d(TAG,"tytuł: " + element.get(JsonParser.TAG_ORIGINAL_TITLE));
            Log.d(TAG,"id filmu: " + element.get(JsonParser.TAG_ID));
            Log.d(TAG,"url filmu: " + element.get(JsonParser.TAG_POSTER_PATH));
            Log.d(TAG,"średnia ocen: " + element.get(JsonParser.TAG_VOTE_AVERAGE));
        }*/
    }


    private void updateImages() {

        if (gridViewAdapter == null) {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, posterThumbnails);
            gridView.setAdapter(gridViewAdapter);
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}