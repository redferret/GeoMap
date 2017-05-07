package com.example.richard.geomap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orm.SugarContext;

import java.util.List;

public class GoogleMapActivity extends GeoMapActivity {

    private Project selectedProject;
    private ProjectFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo_map_activity);

        SugarContext.init(this);

        Intent intent = getIntent();

        Long projectId = intent.getExtras().getLong("PROJECT_ID");
        selectedProject = Project.findById(Project.class, projectId);

        if (selectedProject == null) {
            finish();
        }

        mainFragment = new ProjectFragment();

        mainFragment.setProjectLocation(selectedProject.getId());
        //Get the fragment for this activity
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mainFragment)
                .commit();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        // do something here and don't write super.onBackPressed()FragmentTransaction fragmentTransaction;

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Life Cycle: ", "On Resume");
        mainFragment.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mainFragment.connect();
    }



    @Override
    protected void onStop() {

        mainFragment.removeLocationUpdates();

        if (mainFragment != null) {
            mainFragment.disconnect();
        }
        super.onStop();
    }

}
