import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Richard on 4/20/2017.
 */

public class Location {

    private String title;
    private LatLng position;


    public Location(double lat, double lng){
        this(new LatLng(lat, lng));
    }

    public Location(LatLng pos){
        this.position = pos;
    }

}
