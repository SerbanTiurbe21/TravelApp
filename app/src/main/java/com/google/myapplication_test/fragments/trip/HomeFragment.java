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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    Button addTripButtonHome;
    static String email;
    private RecyclerView tripRecyclerView;
    private List<Trip> tripList;
    private CityViewModel cityViewModel;
    private TripAdapter tripAdapter;
    private TripViewModel tripViewModel;
    List<Trip> myTrips;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {

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
        Bundle bundle = getArguments();
        if (bundle != null) {
            email = bundle.getString("email");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button button = (Button) view.findViewById(R.id.addTripButtonHome);

        button.setOnClickListener(myView -> {
            Intent intent = new Intent(getActivity(), AddDestinationActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripRecyclerView = view.findViewById(R.id.recyclerView);
        tripAdapter = new TripAdapter(getContext());
        tripRecyclerView.setAdapter(tripAdapter);
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cityViewModel = new ViewModelProvider(this).get(CityViewModel.class);

        List<Trip> helperList = new ArrayList<>();
        myTrips = new ArrayList<>();
        myTrips.add(new Trip("balamuc", "milano", "", 300F, 4.5F, false, email));
        //tripAdapter.setTripList(myTrips);
        //tripViewModel.setDataSource(myTrips);

        cityViewModel.getAllCities().observe(getViewLifecycleOwner(), cities -> {
            boolean flag;
            for (City c : cities) {
                Trip trip = new Trip(c.getTripName(), c.getDestination(), c.getPhotoUri(), c.getPrice(), c.getRating(), c.isFavourite(), c.getUserId());
                //Log.d("tripName",trip.getTripName());
                helperList.add(trip);
            }
            List<Trip> aux = removeDuplicates(helperList);
            tripAdapter.setTripList(aux);
        });

        return view;
    }

    private void setTripsLayoutManager() {
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setTripsAdapter() {
        tripAdapter = new TripAdapter(getContext());
        tripRecyclerView.setAdapter(tripAdapter);
    }

    private void setTrips() {
        //myTrips = new ArrayList<>();
        myTrips.add(new Trip("balamuc", "milano", "", 300F, 4.5F, false, email));
        tripAdapter.setTripList(myTrips);
    }

    private void setupRecyclerView() {
        setTrips();
        setTripsLayoutManager();
        setTripsAdapter();
    }

    private static List<Trip> removeDuplicates(List<Trip> trips) {
        Set<Trip> uniqueTrips = new HashSet<>();
        uniqueTrips.addAll(trips);
        trips.clear();
        trips.addAll(uniqueTrips);
        return trips;
    }

}