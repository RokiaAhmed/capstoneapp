package com.ikdynmaics.capstoneapp.favourite;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.ikdynmaics.capstoneapp.R;
import com.ikdynmaics.capstoneapp.data.cash.PlaceFirebaseModel;
import com.ikdynmaics.capstoneapp.data.places_api.places_models.Result;
import com.ikdynmaics.capstoneapp.places.PlacesAdapter;
import com.ikdynmaics.capstoneapp.places.PlacesFragment;
import com.ikdynmaics.capstoneapp.utills.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;
    private FavouriteAdapter favouriteAdapter;


    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);
        mRecyclerView = rootView.findViewById(R.id.fav_places_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);

        getFavouritePlaces();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

    private void getFavouritePlaces() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener favouriteListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<PlaceFirebaseModel> placesList = new ArrayList<PlaceFirebaseModel>();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    placesList.add(child.getValue(PlaceFirebaseModel.class));
                }
                favouriteAdapter = new FavouriteAdapter(getActivity(), placesList);
                mRecyclerView.setAdapter(favouriteAdapter);
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
                Log.e("test", placesList.size() + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mDatabase.addValueEventListener(favouriteListener);
    }
}
