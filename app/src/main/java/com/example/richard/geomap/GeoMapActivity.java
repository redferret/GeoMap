package com.example.richard.geomap;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

public class GeoMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_map);

        // setupSupportMapFragment(R.id.open_projects_map);
    }

    public void setupSupportMapFragment(int fragmentId){
        mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(fragmentId);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * Load projects into the spinner for the user to select from
     * @param list The list of projects
     * @param <T> The datatype
     */
    public <T> void loadProjectsIntoSpinner(ArrayList<T> list){
        Spinner karant_sp = (Spinner) findViewById(R.id.select_project_spinner);
        ArrayAdapter<T> karant_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        karant_sp.setAdapter(karant_adapter);
        karant_sp.setSelection(0);
        //karant_sp.setOnItemSelectedListener(new select_karant());
    }

    /**
     * Time Picker Fragment, show this when the user is ready to pick a time.
     */
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, 12, 0,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }
}
