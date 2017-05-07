package com.example.richard.geomap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.kml.KmlLayer;
import com.orm.SugarRecord;

import java.util.List;

public class Project extends SugarRecord {

    private String title;
    private String desc;
    private String color;
    public static final String DEFAULT_COLOR = "#303F9F";

    public Project(){
        this("No Title", "", DEFAULT_COLOR);
    }

    public Project(String title, String desc){
        this(title, desc, DEFAULT_COLOR);
    }

    public Project(String title, String desc, String color){
        this.title = title;
        this.desc = desc;
        this.color = color;
    }

    public String getColor(){
        return color;
    }

    public List<Measurement> getMeasurements(){
        return Measurement.find(Measurement.class, "project = ?", new String[]{getId().toString()});
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString(){
        return title;
    }

    public LatLng getCenter() {

        double latAvg = 0;
        double lngAvg = 0;

        List<Measurement> measurements = getMeasurements();
        int n = measurements.size();

        if (n != 0) {
            for (Measurement measurement : measurements) {
                LatLng mLatLng = measurement.getPosition();
                latAvg += mLatLng.latitude;
                lngAvg += mLatLng.longitude;
            }
            latAvg /= n;
            lngAvg /= n;
        }

        return new LatLng(latAvg, lngAvg);
    }
}
