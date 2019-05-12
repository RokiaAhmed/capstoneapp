package com.ikdynmaics.capstoneapp.places;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.ikdynmaics.capstoneapp.R;
import com.ikdynmaics.capstoneapp.data.cash.PlaceFirebaseModel;
import com.ikdynmaics.capstoneapp.data.cash.PreferenceUtils;
import com.ikdynmaics.capstoneapp.data.places_api.GetDataPlacesService;
import com.ikdynmaics.capstoneapp.data.places_api.PlacesRetrofitClientInstance;
import com.ikdynmaics.capstoneapp.data.places_api.places_models.Example;
import com.ikdynmaics.capstoneapp.data.places_api.places_models.Result;
import com.ikdynmaics.capstoneapp.utills.ConnectionDetector;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlacesFragment extends Fragment implements PlacesAdapter.ListItemClickListener {

    private RecyclerView mRecyclerView;
    private Gson gson;
    private ConnectionDetector connectionDetector;
    private ShimmerFrameLayout mShimmerViewContainer;
    private PlacesAdapter placesAdapter;

    public static String key = "place_name";
    private String mPlaceName;
    private List<Result> restaurantList;


    public PlacesFragment() {
        // Required empty public constructor
    }

    public static PlacesFragment newInstance(String place) {
        PlacesFragment fragment = new PlacesFragment();
        Bundle args = new Bundle();
        args.putString(key, place);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlaceName = getArguments().getString(key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_place, container, false);
        gson = new Gson();
        mRecyclerView = rootView.findViewById(R.id.place_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        placesAdapter = new PlacesAdapter(getActivity(), this);
        mRecyclerView.setAdapter(placesAdapter);
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
        connectionDetector = new ConnectionDetector(getActivity());
        if (connectionDetector.isConnectingToInternet()) {
            new PlacesAsyncTask().execute();
        } else {
            Toast.makeText(getActivity(), getString(R.string.network_fail), Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onSaveItemClick(int clickedItemIndex, ImageView iconReference) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Result item = restaurantList.get(clickedItemIndex);
        PlaceFirebaseModel place = new PlaceFirebaseModel(item.getName(),
                item.getFormattedAddress(), item.getIcon(), item.getRating());

        if (iconReference.getTag().equals(getString(R.string.un_save))) {
            // Write a message to the database
            mDatabase.child(item.getId()).setValue(place);
            iconReference.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark));
            iconReference.setTag(getString(R.string.save));
        } else {
            mDatabase.child(item.getId()).removeValue();
            iconReference.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_border));
            iconReference.setTag(getString(R.string.un_save));
        }

    }

    private class PlacesAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            getPlacesList();
            return null;
        }
    }

    private void getPlacesList() {
//        progressFrame.setVisibility(View.VISIBLE);


        GetDataPlacesService service = PlacesRetrofitClientInstance.getRetrofitInstance().create(GetDataPlacesService.class);

        final String cityName = PreferenceUtils.getCityName(getActivity());
        String params = mPlaceName + "+" + "in" + "+" + cityName;

        Call<Example> call = service.getPlaces(params, getString(R.string.places_key));
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code() == 404) {
//                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), getString(R.string.error_404), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
//                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), getString(R.string.error_401), Toast.LENGTH_SHORT).show();
                } else {
                    restaurantList = response.body().getResults();
//                    PreferenceUtils.setPlacesList(getContext(), mPlaceName, new Gson().toJson(restaurantList));
//                    restaurantList = Arrays.asList(gson.fromJson(PreferenceUtils.getPlacesList(getActivity(), mPlaceName), Result[].class));
                    placesAdapter.setList(restaurantList);
                    placesAdapter.notifyDataSetChanged();
                    // stop animating Shimmer and hide the layout
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }
        });
    }

}
