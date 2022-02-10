package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityMapsBinding;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(this);

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "data1.json");
        Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<User>>() { }.getType();

        List<User> users = gson.fromJson(jsonFileString, listUserType);
        for (int i = 0; i < users.size(); i++) {
            Double lat1 = Double.parseDouble(users.get(i).block_location.get("latitude"));
            Double long1 = Double.parseDouble(users.get(i).block_location.get("longitude"));
            LatLng crime = new LatLng(lat1, long1);
            if (users.get(i).cvlegend.contains("THEFT") || users.get(i).cvlegend.contains("LARCENY") || users.get(i).cvlegend.contains("BURGLARY")) {
                mMap.addMarker(new MarkerOptions().position(crime).icon(BitmapDescriptorFactory.fromAsset("theft5.png")).title(users.get(i).offense).snippet(users.get(i).blkaddr + " " + users.get(i).eventdt.substring(0,10) + " " + users.get(i).eventtm));
            } else if (users.get(i).cvlegend.contains("ASSAULT")) {
                mMap.addMarker(new MarkerOptions().position(crime).icon(BitmapDescriptorFactory.fromAsset("assault1.png")).title(users.get(i).offense).snippet(users.get(i).blkaddr + " " + users.get(i).eventdt.substring(0,10) + " " + users.get(i).eventtm));
            } else if (users.get(i).cvlegend.contains("DISORDERLY CONDUCT")) {
                mMap.addMarker(new MarkerOptions().position(crime).icon(BitmapDescriptorFactory.fromAsset("dcon1.png")).title(users.get(i).offense).snippet(users.get(i).blkaddr + " " + users.get(i).eventdt.substring(0,10) + " " + users.get(i).eventtm));
            } else if (users.get(i).cvlegend.contains("DRUG") || users.get(i).cvlegend.contains("LIQUOR")) {
                mMap.addMarker(new MarkerOptions().position(crime).icon(BitmapDescriptorFactory.fromAsset("drug1.png")).title(users.get(i).offense).snippet(users.get(i).blkaddr + " " + users.get(i).eventdt.substring(0,10) + " " + users.get(i).eventtm));
            } else if (users.get(i).cvlegend.contains("SEX")) {
                mMap.addMarker(new MarkerOptions().position(crime).icon(BitmapDescriptorFactory.fromAsset("sexcrimes1.png")).title(users.get(i).offense).snippet(users.get(i).blkaddr + " " + users.get(i).eventdt.substring(0,10) + " " + users.get(i).eventtm));
            } else if (users.get(i).cvlegend.contains("ARSON")) {
                mMap.addMarker(new MarkerOptions().position(crime).icon(BitmapDescriptorFactory.fromAsset("arson1.png")).title(users.get(i).offense).snippet(users.get(i).blkaddr + " " + users.get(i).eventdt.substring(0,10) + " " + users.get(i).eventtm));
            } else if (users.get(i).cvlegend.contains("FRAUD")) {
                mMap.addMarker(new MarkerOptions().position(crime).icon(BitmapDescriptorFactory.fromAsset("fraud1.png")).title(users.get(i).offense).snippet(users.get(i).blkaddr + " " + users.get(i).eventdt.substring(0,10) + " " + users.get(i).eventtm));
            } else if (users.get(i).cvlegend.contains("WEAPONS")) {
                mMap.addMarker(new MarkerOptions().position(crime).icon(BitmapDescriptorFactory.fromAsset("gun1.png")).title(users.get(i).offense).snippet(users.get(i).blkaddr + " " + users.get(i).eventdt.substring(0,10) + " " + users.get(i).eventtm));
            }
        }
        LatLng berkeley = new LatLng(37.8719, -122.2585);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(berkeley));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(berkeley, 15));

    }
}