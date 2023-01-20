package com.google.myapplication_test.fragments.trip;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.myapplication_test.R;
import com.google.myapplication_test.activities.AddDestinationActivity;
import com.google.myapplication_test.database.AppDatabase;
import com.google.myapplication_test.database.City;
import com.google.myapplication_test.database.CityDao;
import com.google.myapplication_test.database.CityViewModel;
import com.google.myapplication_test.database.UserDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    private final int REQUEST_CODE = 1;
    Button addTripButtonHome;
    static String email;
    private RecyclerView tripRecyclerView;
    private List<Trip> tripList;
    private CityViewModel cityViewModel;
    private TripAdapter tripAdapter;
    private TripViewModel tripViewModel;
    private SwipeRefreshLayout swipeRefresh;
    private static String tripName, destination, price, stars, linkImage;
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
            //tripName = bundle.getString("tripName");
            //destination = bundle.getString("destination");
            //price = bundle.getString("price");
            //stars = bundle.getString("stars");
            //linkImage = bundle.getString("linkImage");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button button = (Button) view.findViewById(R.id.addTripButtonHome);

        AppDatabase appDatabase = AppDatabase.getDatabase(getContext());
        UserDao userDao = appDatabase.userDao();
        CityDao cityDao = appDatabase.cityDao();
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        tripRecyclerView = view.findViewById(R.id.recyclerView);
        tripAdapter = new TripAdapter(getContext());
        tripRecyclerView.setAdapter(tripAdapter);
        tripRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cityViewModel = new ViewModelProvider(this).get(CityViewModel.class);
        myTrips = new ArrayList<>();
        List<Trip> helperList = new ArrayList<>();

        //myTrips.add(new Trip("balamuc", "milano", "", 300F, 4.5F, false, email));
        tripAdapter.setTripList(myTrips);

        cityViewModel.getAllCities().observe(getViewLifecycleOwner(), cities -> {
            for (City c : cities) {
                Trip trip = new Trip(c.getTripName(), c.getDestination(), c.getPhotoUri(), c.getPrice(), c.getRating(), c.isFavourite(), c.getUserId());
                Log.d("tripName",trip.getTripName());
                helperList.add(trip);
            }
            List<Trip> aux = removeDuplicates(helperList);
            myTrips = aux;
            tripAdapter.setTripList(myTrips);
        });


        List<Trip> helperList1 = new ArrayList<>();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //myTrips.clear();
                cityViewModel.getAllCities().observe(getViewLifecycleOwner(),cities -> {
                    for (City c : cities) {
                        Trip trip = new Trip(c.getTripName(), c.getDestination(), c.getPhotoUri(), c.getPrice(), c.getRating(), c.isFavourite(), c.getUserId());
                        Log.d("tripName",trip.getTripName());
                        helperList1.add(trip);
                    }
                    List<Trip> aux2 = removeDuplicates(helperList1);
                    myTrips = aux2;
                    tripAdapter.notifyDataSetChanged();
                });
                swipeRefresh.setRefreshing(false);
            }
        });

        button.setOnClickListener(myView -> {
            Intent intent = new Intent(getActivity(), AddDestinationActivity.class);
            intent.putExtra("email", email);
            startActivityForResult(intent,REQUEST_CODE);
        });
        return view;
    }

    private static List<Trip> removeDuplicates(List<Trip> trips) {
        Set<Trip> uniqueTrips = new HashSet<>();
        uniqueTrips.addAll(trips);
        trips.clear();
        trips.addAll(uniqueTrips);
        return trips;
    }

}