package com.softlica.bishal.gymapp.views.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap googleMap;
    int locationCount = 0;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    TextView location, website;
    GymObjectDB gym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Need to be implemented", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        location = (TextView) findViewById(R.id.location);
        website = (TextView) findViewById(R.id.web);
        //get position from the previous activity
        Intent intent = getIntent();
        int position = intent.getIntExtra("POS", 0);

        //get clicked gym
        gym = App.getInstance().gymObjectDBList.get(position);
        // setting gym info
        location.setText(gym.getAddress());
        website.setText(gym.getWebsite());
        getSupportActionBar().setTitle(gym.getName());

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
        googleMap.setOnMarkerClickListener(this);
//        for (int i = 0; i < flickList.size(); i++) {
//            final MarkerModel marker = presenter.prepareMarker(flickList.get(i));
//            final LatLng location = new LatLng(marker.getLatitude(), marker.getLongitude());
//            googleMap.addMarker(new MarkerOptions()
//                    .position(location)
//                    .snippet(marker.getTitle())
//                    .visible(true)
//                    .title(marker.getOwnerName()));

        LatLng latLng = new LatLng(gym.getLatitude(), gym.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title(gym.getName())).showInfoWindow();
        ;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // Moving CameraPosition to last clicked position
        // Setting the zoom level in the map on last position  is clicked
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(gym.getLatitude(),
                        gym.getLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

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

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

