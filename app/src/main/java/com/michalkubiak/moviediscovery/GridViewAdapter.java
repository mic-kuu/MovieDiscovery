package com.michalkubiak.moviediscovery;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.michalkubiak.moviediscovery.network.APIcontract;
import com.michalkubiak.moviediscovery.network.AsyncResponse;
import com.michalkubiak.moviediscovery.network.RetrieveMovieDBAPI;
import com.michalkubiak.moviediscovery.pojo.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * The adapter responsible for populating the GridView with images
 * received from MovieDB API
 */
public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<MovieItem> data = new ArrayList<>();
    AsyncResponse delegate;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<MovieItem> data, AsyncResponse delegate) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.delegate = delegate;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        String imgURL = data.get(position).getPosterPath();
        Picasso.with(getContext()).load(imgURL).fit().into(holder.image);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrieveMovieDBAPI retrieveMovieDBAPI = new RetrieveMovieDBAPI();

                int movieID = data.get(position).getId();
                retrieveMovieDBAPI.setAPIEndpoint(APIcontract.getMoivieDetails(movieID));
                retrieveMovieDBAPI.setResponseType(RetrieveMovieDBAPI.MOVIEDETAIL);
                retrieveMovieDBAPI.delegate = delegate;
                retrieveMovieDBAPI.execute();
            }
        });

        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

}
