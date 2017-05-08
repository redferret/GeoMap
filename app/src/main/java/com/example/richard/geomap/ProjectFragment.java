package com.example.richard.geomap;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.DecimalFormat;

/**
 * Created by Richard on 5/3/2017.
 */

public class ProjectFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Long projectId;
    private boolean recenter;
    private LatLng currentLatLng;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Measurement usersCurrentLocation;
    private LocationRequest mLocationRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.project, container, false);

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.project_map);
        mapFragment.getMapAsync(this);

        usersCurrentLocation = new Measurement();

        buildGoogleApiClient();

        return view;

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button getStrikeDip = (Button) getView().findViewById(R.id.get_strike_dip_button);
        getStrikeDip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText strikeText = (EditText) getView().findViewById(R.id.strike_text);
                EditText dipText = (EditText) getView().findViewById(R.id.dip_text);

                DecimalFormat df = new DecimalFormat("##.##");

                float azimut = ((GoogleMapActivity)getActivity()).getAzimut();
                float rotation = -azimut * 360 / (2 * 3.14159f);
                double strike = rotation;

                double pitch = ((GoogleMapActivity)getActivity()).getPitch();
                // angle in degree [-180 - 0 - 180] degree
                pitch = Math.toDegrees(pitch);
                double dip = pitch < 0 ? -pitch: pitch;

                strikeText.setText(df.format(strike));
                dipText.setText(df.format(dip));

                Toast.makeText(getActivity(), "Strike and Dip Set", Toast.LENGTH_LONG).show();
            }
        });

        Button getLocation = (Button) getView().findViewById(R.id.get_location_button);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText lat = (EditText) getView().findViewById(R.id.latitude_text);
                EditText lng = (EditText) getView().findViewById(R.id.logitude_text);

                lat.setText(currentLatLng.latitude+"");
                lng.setText(currentLatLng.longitude+"");

                Toast.makeText(getActivity(), "Location Found and Set", Toast.LENGTH_LONG).show();
            }
        });

        Button record = (Button) getView().findViewById(R.id.record_button);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SugarContext.init(getActivity());

                EditText lat = (EditText) getView().findViewById(R.id.latitude_text);
                EditText lng = (EditText) getView().findViewById(R.id.logitude_text);
                EditText strikeText = (EditText) getView().findViewById(R.id.strike_text);
                EditText dipText = (EditText) getView().findViewById(R.id.dip_text);

                double strike = Double.parseDouble(strikeText.getText().toString());
                double dip = Double.parseDouble(dipText.getText().toString());

                usersCurrentLocation.setStrikeDip(strike, dip);
                usersCurrentLocation.setLat(Double.parseDouble(lat.getText().toString()));
                usersCurrentLocation.setLng(Double.parseDouble(lng.getText().toString()));

                Project project = Project.findById(Project.class, projectId);
                usersCurrentLocation.saveProject(project);
                usersCurrentLocation.setColor(project.getColor());

                LatLng markerPos = usersCurrentLocation.getPosition();
                BitmapDescriptor icon = GeoMapActivity.getMarkerColor(usersCurrentLocation.getColor());
                mMap.addMarker(new MarkerOptions()
                        .position(markerPos)
                        .title(project.getTitle())
                        .snippet("Dip: " + usersCurrentLocation.getDip()
                                + " Strike: " + usersCurrentLocation.getStrike())
                        .icon(icon));

                usersCurrentLocation.save();
                usersCurrentLocation = new Measurement();

                Toast.makeText(getActivity(), "Record Saved", Toast.LENGTH_LONG).show();
            }
        });

    }
    private synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setMyLocationEnabled(true);

        if (projectId != null) {
            Project projectLocation = Project.findById(Project.class, projectId);
            ((GoogleMapActivity) getActivity()).centerOnProject(projectLocation, mMap);
        }
    }

    @Override
    public void onConnected(Bundle dataBundle) {

        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mCurrentLocation != null) {
            currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(getActivity(), "Disconnected. Please re-connect.", Toast.LENGTH_LONG).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(getActivity(), "Network lost. Please re-connect.", Toast.LENGTH_LONG).show();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(5000);
        // Request location updates
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void connect(){
        mGoogleApiClient.connect();
    }

    public void disconnect(){
        mGoogleApiClient.disconnect();
    }

    public GoogleApiClient getClient(){
        return mGoogleApiClient;
    }

    @Override
    public void onLocationChanged(Location location) {

        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    public void removeLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(getClient(), this);
    }

    public void setProjectLocation(Long projectId) {
        this.projectId = projectId;
    }
}
