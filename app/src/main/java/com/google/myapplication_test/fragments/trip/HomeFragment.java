package com.google.myapplication_test.fragments.trip;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.myapplication_test.R;
import com.google.myapplication_test.activities.AddDestinationActivity;
import com.google.myapplication_test.database.City;
import com.google.myapplication_test.database.CityViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{

    TextView textInvisible;
    Button addTripButtonHome;
    static String email;
    private RecyclerView tripRecyclerView;
    private List<Trip> tripList;
    private CityViewModel cityViewModel;
    private TripAdapter tripAdapter;
    List<Trip> myTrips = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        Bundle bundle1 = new Bundle();

        Bundle bundle = getArguments();
        if (bundle != null) {
           email = bundle.getString("email");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        Button button = (Button) view.findViewById(R.id.addTripButtonHome);

        button.setOnClickListener(myView -> {
            Intent intent = new Intent(getActivity(),AddDestinationActivity.class);
            intent.putExtra("email",email);
            startActivity(intent);
        });

        tripRecyclerView = view.findViewById(R.id.recyclerView);
        tripAdapter = new TripAdapter(getContext());
        tripRecyclerView.setAdapter(tripAdapter);
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cityViewModel = new ViewModelProvider(this).get(CityViewModel.class);
        cityViewModel.getAllCities().observe(getViewLifecycleOwner(), cities -> {
            for(City c: cities){
                Trip trip = new Trip(c.getTripName(),c.getDestination(),c.getPhotoUri(),c.getPrice(),c.getRating(),c.isFavourite(),c.getUserId());
                myTrips.add(trip);
            }
            tripAdapter.setTripList(myTrips);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setAddTripButtonHome(){
        addTripButtonHome.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddDestinationActivity.class);
            startActivity(intent);
        });
    }

}