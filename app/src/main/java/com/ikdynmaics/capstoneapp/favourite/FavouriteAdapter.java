package com.ikdynmaics.capstoneapp.favourite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ikdynmaics.capstoneapp.R;
import com.ikdynmaics.capstoneapp.data.cash.PlaceFirebaseModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private Context context;
    private List<PlaceFirebaseModel> mList;



    public FavouriteAdapter(Context context, List<PlaceFirebaseModel> list) {
        this.context = context;
        mList = new ArrayList<>();
        mList = list;
     }

    // Create new views
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(context).inflate(
                R.layout.item_favourite, parent, false);
        return new FavouriteAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(FavouriteAdapter.ViewHolder holder, final int position) {

        Picasso.get().load(mList.get(position).getIcon()).fit().into(holder.placeTypeImageView);
        holder.placeNameTextView.setText(mList.get(position).getPlaceName());
        holder.placeAddressTextView.setText(mList.get(position).getPlaceAddress());
        holder.placeRatingBar.setRating((mList.get(position).getRate()).floatValue());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView placeNameTextView, placeAddressTextView;
        ImageView placeTypeImageView;
        RatingBar placeRatingBar;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            placeTypeImageView = itemLayoutView.findViewById(R.id.iv_place_type);
            placeNameTextView = itemLayoutView.findViewById(R.id.tv_place_name);
            placeAddressTextView = itemLayoutView.findViewById(R.id.tv_place_address);
            placeRatingBar = itemLayoutView.findViewById(R.id.place_rating_bar);

         }

    }
}