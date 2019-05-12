package com.ikdynmaics.capstoneapp.places;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ikdynmaics.capstoneapp.R;
import com.ikdynmaics.capstoneapp.data.places_api.places_models.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("DefaultFileTemplate")
public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private Context context;
    private List<Result> mList;
    private ListItemClickListener mOnClickListener;


    public interface ListItemClickListener {
        void onSaveItemClick(int clickedItemIndex, ImageView iconReference);
    }

    public PlacesAdapter(Context context, ListItemClickListener listener) {
        this.context = context;
        mList = new ArrayList<>();
        mOnClickListener = listener;
    }

    // Create new views
    @Override
    public PlacesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(context).inflate(
                R.layout.item_restaurant, parent, false);
        return new ViewHolder(itemLayoutView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String imageReference = mList.get(position).getPhotos().get(0).getPhotoReference();
        String imageFullPath = "https://maps.googleapis.com/maps/api/place/photo" +
                "?maxwidth=400" +
                "&photoreference= " + imageReference +
                "&key=AIzaSyAfDHQhe4fmz5FAitOnTjrOFPesb88GXFE";
        Picasso.get().load(imageFullPath).fit().into(holder.placeImageView);
        holder.placeNameTextView.setText(mList.get(position).getName());
        holder.placeRatingBar.setRating((mList.get(position).getRating()).floatValue());
    }

    private void isItemFavourite(){
//      DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        ValueEventListener favouriteListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<PlaceFirebaseModel> placesList = new ArrayList<PlaceFirebaseModel>();
//                for (DataSnapshot child: dataSnapshot.getChildren()) {
//                    placesList.add(child.getValue(PlaceFirebaseModel.class));
//                }
//                favouriteAdapter = new FavouriteAdapter(getActivity(), placesList);
//                mRecyclerView.setAdapter(favouriteAdapter);
//                mShimmerViewContainer.stopShimmer();
//                mShimmerViewContainer.setVisibility(View.GONE);
//                Log.e("test", placesList.size() + "");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        };
//
//        mDatabase.addValueEventListener(favouriteListener);
    }

    public void setList(List<Result> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView placeNameTextView;
        ImageView placeImageView, saveImageView;
        RatingBar placeRatingBar;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            placeImageView = itemLayoutView.findViewById(R.id.iv_place);
            saveImageView = itemLayoutView.findViewById(R.id.iv_save);
            placeNameTextView = itemLayoutView.findViewById(R.id.tv_place_name);
            placeRatingBar = itemLayoutView.findViewById(R.id.place_rating_bar);

            saveImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onSaveItemClick(clickedPosition, saveImageView);

        }
    }
}