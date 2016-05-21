package com.michalkubiak.moviediscovery;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.michalkubiak.moviediscovery.pojo.MovieItem;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieListInterface} interface
 * to handle interaction events.
 * Use the {@link MovieListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieListFragment extends Fragment  {
    private static final String MOVIE_LIST = "movieList";

    private MovieListInterface mListener;

    private ArrayList<MovieItem> movieList;
    private GridViewAdapter gridViewAdapter;
    private GridView gridView;



    public MovieListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param movieItems An {@link MovieItem} object to populate grid view
     * @return A new instance of fragment MovieListFragment.
     */
    public static MovieListFragment newInstance(ArrayList<MovieItem> movieItems) {
        MovieListFragment fragment = new MovieListFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(MOVIE_LIST, movieItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieList = getArguments().getParcelableArrayList(MOVIE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);

        if (gridViewAdapter == null) {
            gridViewAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, movieList);
            gridView.setAdapter(gridViewAdapter);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int movieId) {
        if (mListener != null) {
            mListener.onDetailedViewSelected(movieId);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MovieListInterface) {
            mListener = (MovieListInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MovieListInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface MovieListInterface {

        void onDetailedViewSelected(int movieId);
    }
}
