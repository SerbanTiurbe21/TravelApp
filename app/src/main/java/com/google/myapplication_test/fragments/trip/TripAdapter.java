package com.google.myapplication_test.fragments.trip;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.myapplication_test.R;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {

    private List<Trip> tripList;

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row,parent,false);
        return new TripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip currentTrip = tripList.get(position);
        holder.getImageView2().setImageURI(Uri.parse(currentTrip.getImageUrl()));
        holder.getBookmarkIconCustomRow().setImageResource(
                currentTrip.isBookmarked() ? R.drawable.ic_baseline_bookmark__gold_24 : R.drawable.ic_baseline_bookmark_24
        );
        holder.getTripNameCustomRow().setText(currentTrip.getTripName());
        holder.getDestinationCustomRow().setText(currentTrip.getDestination());
        holder.getTextView9().setText(String.valueOf(currentTrip.getPrice()));
        holder.getRatingCustomRow().setText(String.valueOf(currentTrip.getRating()));

        

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
