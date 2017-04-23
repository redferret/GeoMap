package com.example.richard.geomap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 *
 */
public class Measurement extends SugarRecord {

    public static final String DEFAULT_COLOR = "#303F9F";
    private String notes;
    private double lat, lng;
    private double strike, dip;
    private String color;
    private Project project;

    public Measurement() {
        this(0, 0, 0, 0, DEFAULT_COLOR);
    }
    public Measurement(double lat, double lng){
        this(lat, lng, 0, 0, DEFAULT_COLOR);
    }
    public Measurement(double lat, double lng, String color){
        this(lat, lng, 0, 0, color);
    }
    public Measurement(double lat, double lng, double strike, double dip, String color){
        this.lat = lat;
        this.lng = lng;
        this.color = color;
        setStrikeDip(strike, dip);
    }

    public String getColor(){
        return color;
    }

    public void saveProject(Project project){
        this.project = project;
    }

    public final void setStrikeDip(double strike, double dip){
        this.strike = strike;
        this.dip = dip;
    }

    public double getStrike() {
        return strike;
    }

    public LatLng getPosition() {
        return new LatLng(lat, lng);
    }

    public double getDip() {
        return dip;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
