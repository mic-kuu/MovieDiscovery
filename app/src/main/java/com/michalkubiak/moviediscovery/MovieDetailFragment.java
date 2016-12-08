package com.michalkubiak.moviediscovery;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.michalkubiak.moviediscovery.network.APIcontract;
import com.michalkubiak.moviediscovery.pojo.MovieDetails;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnMovieDetailedInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MOVIE_DETAILS = "movie_details";

    // TODO: Rename and change types of parameters

    private MovieDetails movieDetails;

    private OnMovieDetailedInteractionListener mListener;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(MovieDetails movieDetails) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE_DETAILS, movieDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieDetails = getArguments().getParcelable(ARG_MOVIE_DETAILS);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        TextView movieTitle = (TextView) view.findViewById(R.id.movieTile);
        movieTitle.setText(movieDetails.getOrignalTitle());

        TextView budgetTV = (TextView) view.findViewById(R.id.budget_tv);
        TextView adultcontentTV = (TextView) view.findViewById(R.id.adultcontent_tv);
        TextView votecountTV = (TextView) view.findViewById(R.id.votecount_tv);
        TextView voteaverageTV = (TextView) view.findViewById(R.id.voteaverage_tv);
        TextView overviewTV = (TextView) view.findViewById(R.id.overview_tv);

        ImageView posterIV = (ImageView) view.findViewById(R.id.poster_id);

        budgetTV.setText(movieDetails.getBudget());
        adultcontentTV.setText(String.valueOf(movieDetails.isAdult()));
        votecountTV.setText(movieDetails.getVoteCount());
        voteaverageTV.setText(movieDetails.getVoteAverage());
        overviewTV.setText(movieDetails.getOverview());

        String imgURL = APIcontract.getPosterImageURL(movieDetails.getPosterPath());

        Picasso.with(getContext()).load(imgURL).fit().into(posterIV);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieDetailedInteractionListener) {
            mListener = (OnMovieDetailedInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMovieDetailedInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
