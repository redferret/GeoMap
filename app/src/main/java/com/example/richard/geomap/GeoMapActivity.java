package com.example.richard.geomap;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public abstract class GeoMapActivity extends FragmentActivity {

    public void forceKeyboardToHide(View v) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    // method definition
    public static BitmapDescriptor getMarkerColor(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
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

    public void centerOnProject(Project project, GoogleMap mMap){

        mMap.clear();
        List<Measurement> markers = project.getMeasurements();

        for (Measurement measurement : markers){
            LatLng markerPos = measurement.getPosition();
            BitmapDescriptor icon = GeoMapActivity.getMarkerColor(measurement.getColor());
            mMap.addMarker(new MarkerOptions()
                    .position(markerPos)
                    .title(project.getTitle())
                    .snippet("Dip: " + measurement.getDip() + " Strike: " + measurement.getStrike())
                    .icon(icon));
        }

        LatLng center = project.getCenter();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
        if (center.latitude != 0 && center.longitude != 0) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }else{
            mMap.animateCamera(CameraUpdateFactory.zoomTo(0));
        }
    }
}
