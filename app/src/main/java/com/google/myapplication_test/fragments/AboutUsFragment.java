package com.google.myapplication_test.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.myapplication_test.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutUsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutUsFragment extends Fragment {

    ImageView googleIcon, facebookIcon, twitterIcon;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    public static AboutUsFragment newInstance(String param1, String param2) {
        AboutUsFragment fragment = new AboutUsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_abou_us, container, false);
        googleIcon = view.findViewById(R.id.googleIcon);
        facebookIcon = view.findViewById(R.id.facebookIcon);
        twitterIcon = view.findViewById(R.id.twitterIcon);

        setGoogleIcon();
        setTwitterIcon();
        setFacebookIcon();

        return view;
    }

    private void setGoogleIcon(){
        googleIcon.setOnClickListener(view -> {
            String url = "https://www.beatravelbuddy.com/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }

    private void setTwitterIcon(){
        twitterIcon.setOnClickListener(view -> {
            String url = "https://twitter.com/TiurbeSerban21";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }

    private void setFacebookIcon(){
        facebookIcon.setOnClickListener(view -> {
            String url = "https://www.facebook.com/serban.tiurbe/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }
}