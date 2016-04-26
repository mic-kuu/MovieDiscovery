package com.michalkubiak.moviediscovery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.michalkubiak.moviediscovery.network.RetriveAPITask;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<HashMap<String, String>> movieList;
    RetriveAPITask retriveAPITask = new RetriveAPITask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retriveAPITask.delegate = this;
        retriveAPITask.execute();

    }

    @Override
    public void processOutput(String output) {
        JsonParser jsonParser = new JsonParser(output);
        jsonParser.parse();

        movieList = jsonParser.getResultList();

        Toast.makeText(MainActivity.this, "pobrałem odpowiedź od api", Toast.LENGTH_SHORT).show();
        for (HashMap<String, String> element : movieList ){
            Log.d(TAG,"tytuł: " + element.get(JsonParser.TAG_ORIGINAL_TITLE));
            Log.d(TAG,"id filmu: " + element.get(JsonParser.TAG_ID));
            Log.d(TAG,"url filmu: " + element.get(JsonParser.TAG_POSTER_PATH));
            Log.d(TAG,"średnia ocen: " + element.get(JsonParser.TAG_VOTE_AVERAGE));
        }
    }
//        String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
//        ImageView ivBasicImage = (ImageView) findViewById(R.id.imageView);
//        boolean test = isOnline();
//        Log.d(TAG, "status: " + String.valueOf(test));
//
//        Picasso.with(this).load(imageUri).into(ivBasicImage, new Callback() {
//
//            @Override
//            public void onSuccess() {
//                Log.d(TAG, "sukces");
//
//            }
//
//            @Override
//            public void onError() {
//                Log.d(TAG, "fail");
//
//            }
//        });
//    }
//
//
//    public boolean isOnline() {
//        ConnectivityManager cm =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//    }




}

