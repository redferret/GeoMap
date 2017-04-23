package com.example.richard.geomap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.kml.KmlLayer;
import com.orm.SugarRecord;

import java.util.List;

public class Project extends SugarRecord {

    private String title;
    private String desc;

    public Project(){
        title = "No Title";
        desc = "";

    }

    public Project(String title, String desc){
        this.title = title;
        this.desc = desc;
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
