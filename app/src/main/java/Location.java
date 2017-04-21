import com.google.android.gms.maps.model.LatLng;

/**
 *
 */
public class Location {

    // Images
    //
    private String title;
    private LatLng position;

    public Location(double lat, double lng){
        this(new LatLng(lat, lng));
    }

    public Location(LatLng pos){
        this.position = pos;
    }

}
