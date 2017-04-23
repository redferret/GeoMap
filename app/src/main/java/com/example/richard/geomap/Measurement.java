package com.example.richard.geomap;

import com.google.android.gms.maps.model.LatLng;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 *
 */
public class Measurement extends SugarRecord {

    private String title;
    private double lat, lng;
    private double strike, dip;

    private Project project;

    public Measurement() {
        lat = lng = strike = dip = 0;
        title = "";
    }
    public Measurement(double lat, double lng){
        this(lat, lng, 0, 0);
    }
    public Measurement(double lat, double lng, double strike, double dip){
        this.lat = lat;
        this.lng = lng;
        setStrikeDip(strike, dip);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
