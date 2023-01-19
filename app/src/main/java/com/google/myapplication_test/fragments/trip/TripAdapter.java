package com.google.myapplication_test.fragments.trip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.myapplication_test.R;
import com.google.myapplication_test.activities.EditTripActivity;
import com.google.myapplication_test.activities.LoginActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {

    private List<Trip> tripList;
    private Context context;
    private String email;

    public TripAdapter(Context context, String email){
        this.context = context;
        this.email = email;
    }

    public void setTripList(List<Trip> trips){
        tripList = trips;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row,parent,false);
        View itemView = LayoutInflater.from(context).inflate(R.layout.custom_row,parent,false);
        return new TripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        if(tripList != null){
            Trip currentTrip = tripList.get(position);
            holder.getImageView2().setImageURI(Uri.parse(currentTrip.getImageUrl()));
            holder.getBookmarkIconCustomRow().setImageResource(
                    currentTrip.isBookmarked() ? R.drawable.ic_baseline_bookmark__gold_24 : R.drawable.ic_baseline_bookmark_24
            );
            holder.getTripNameCustomRow().setText(currentTrip.getTripName());
            holder.getDestinationCustomRow().setText(currentTrip.getDestination());
            holder.getTextView9().setText(String.valueOf(currentTrip.getPrice()));
            holder.getRatingCustomRow().setText("Rating\n" +"\t"+currentTrip.getRating());

            holder.itemView.setOnLongClickListener(view -> {
                Intent intent = new Intent(context, EditTripActivity.class);
                //intent.putExtra("imageLink",holder.getImageView2().toString());
                intent.putExtra("bookmarkItem", currentTrip.isBookmarked());
                intent.putExtra("tripName",currentTrip.getTripName());
                intent.putExtra("destination",currentTrip.getDestination());
                intent.putExtra("price",String.valueOf(currentTrip.getPrice()));
                intent.putExtra("rating",String.valueOf(currentTrip.getRating()));
                intent.putExtra("email",email);
                context.startActivity(intent);
                return true;
            });
        }
        else{
            holder.getTripNameCustomRow().setText(R.string.no_trips);
        }
    }

    @Override
    public int getItemCount() {
        if(tripList != null){
            return tripList.size();
        }
        return 0;
    }
}
