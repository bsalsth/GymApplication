package com.softlica.bishal.gymapp.views.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.softlica.bishal.gymapp.R;
import com.softlica.bishal.gymapp.main.App;
import com.softlica.bishal.gymapp.models.GymObjectDB;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 1972;
    private GoogleMap googleMap;
    int locationCount = 0;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    List<GymObjectDB> gymObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //get clicked gym
        gymObjects = App.getInstance().gymObjectDBList;

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMarkerClickListener(this);
//        for (int i = 0; i < flickList.size(); i++) {
//            final MarkerModel marker = presenter.prepareMarker(flickList.get(i));
//            final LatLng location = new LatLng(marker.getLatitude(), marker.getLongitude());
//            googleMap.addMarker(new MarkerOptions()
//                    .position(location)
//                    .snippet(marker.getTitle())
//                    .visible(true)
//                    .title(marker.getOwnerName()));

        for (GymObjectDB gym : gymObjects) {
            LatLng location = new LatLng(gym.getLatitude(), gym.getLongitude());
            mMap.addMarker(
                    new MarkerOptions()
                    .position(location)
                    .title(gym.getName())
                    .visible(true)
                    .snippet(gym.getAddress())
            ).showInfoWindow();
//
        }

//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom
//                (new LatLng(gymObjects.get(0).getLatitude(), gymObjects.get(0).getLongitude()), 12.0f));

        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(gymObjects.get(0).getLatitude(),
                        gymObjects.get(0).getLongitude()));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }

    // GET FLICK DATA TO SHOW IN MAP

    @Override
    public boolean onMarkerClick(Marker marker) {
        Snackbar.make(findViewById(android.R.id.content), marker.getTitle(), Snackbar.LENGTH_LONG)
                .show();
        return false;
    }


}

