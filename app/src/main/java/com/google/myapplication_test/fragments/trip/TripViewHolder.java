package com.google.myapplication_test.fragments.trip;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.myapplication_test.R;

public class TripViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView2, bookmarkIconCustomRow;
    private final TextView tripNameCustomRow, destinationCustomRow, textView9, ratingCustomRow, emailCustomRow;

    public TripViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imageView2 = itemView.findViewById(R.id.imageView2);
        this.bookmarkIconCustomRow = itemView.findViewById(R.id.bookmarkIconCustomRow);
        this.tripNameCustomRow = itemView.findViewById(R.id.tripNameCustomRow);
        this.destinationCustomRow = itemView.findViewById(R.id.destinationCustomRow);
        this.textView9 = itemView.findViewById(R.id.textView9);
        this.ratingCustomRow = itemView.findViewById(R.id.ratingCustomRow);
        this.emailCustomRow = itemView.findViewById(R.id.emailCustomRow);
    }

    public ImageView getImageView2() {
        return imageView2;
    }

    public ImageView getBookmarkIconCustomRow() {
        return bookmarkIconCustomRow;
    }

    public TextView getTripNameCustomRow() {
        return tripNameCustomRow;
    }

    public TextView getDestinationCustomRow() {
        return destinationCustomRow;
    }

    public TextView getTextView9() {
        return textView9;
    }

    public TextView getRatingCustomRow() {
        return ratingCustomRow;
    }

    public TextView getEmailCustomRow() {
        return emailCustomRow;
    }


}
