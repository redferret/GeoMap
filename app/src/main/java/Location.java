import com.google.android.gms.maps.model.LatLng;

/**
 *
 */
public class Location {

    // Images
    //
    private String title;
    private LatLng position;
    private double strike, dip;


    public Location(LatLng pos){
        this.position = pos;
    }

    public Location(double lat, double lng){
        this(new LatLng(lat, lng));
    }

    public void setStrikeDip(double strike, double dip){
        this.strike = strike;
        this.dip = dip;
    }

    public double getStrike() {
        return strike;
    }

    public LatLng getPosition() {
        return position;
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
