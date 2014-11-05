package fi.tiko.tamk.spotcatcher.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import fi.tiko.tamk.spotcatcher.R;
import fi.tiko.tamk.spotcatcher.db.ExternalDatabaseFetcher;
import fi.tiko.tamk.spotcatcher.db.ExternalDatabaseHandler;
import fi.tiko.tamk.spotcatcher.services.GPSTracker;

public class MainActivity extends Activity {

    ArrayList<GeoPoint> starLocationList = new ArrayList<GeoPoint>();
    MapView mapView;
    MyItemizedOverlay myStarOverlay = null;
    MyItemizedOverlay myUserOverlay = null;
    IMapController controller;
    ResourceProxy resourceProxy;
    private String TAG = "MainActivity";
    GeoPoint userPoint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Log.d(TAG, "Start DB");
        Intent i = new Intent(this, ExternalDatabaseFetcher.class);
        i.putExtra("table", "rakennelmat");
        startService(i);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        BoundingBoxE6 bBox = new BoundingBoxE6(61.9949, 23.3500, 61.8468, 23.5550);
        mapView.setScrollableAreaLimit(bBox);
        mapView.setMinZoomLevel(12);

        controller = mapView.getController();
        controller.setZoom(12);

        Drawable marker=getResources().getDrawable(android.R.drawable.star_big_on);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);

        resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());

        myStarOverlay = new MyItemizedOverlay(marker, resourceProxy);

        for(int a = 0; a<3; a++){

            starLocationList.add(new GeoPoint(61.9000+(double)a/100, 23.4000+(double)a/100));
            myStarOverlay.addItem(starLocationList.get(a), "myPoint"+a, "myPoint"+a);

        }

        mapView.getOverlays().add(myStarOverlay);

        Drawable marker2=getResources().getDrawable(android.R.drawable.ic_menu_myplaces);
        int markerWidth2 = marker2.getIntrinsicWidth();
        int markerHeight2 = marker2.getIntrinsicHeight();
        marker2.setBounds(0, markerHeight2, markerWidth2, 0);

        myUserOverlay = new MyItemizedOverlay(marker2, resourceProxy);

        IntentFilter filter = new IntentFilter("fi.tiko.tamk.spotcatcher.ui.LOCATION_UPDATED");
        LocationUpdatedReceiver s = new LocationUpdatedReceiver();
        registerReceiver(s, filter);

        Intent location_service = new Intent(this, GPSTracker.class);
        this.startService(location_service);

    }

    class LocationUpdatedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle extras = intent.getExtras();

            //jostain syyst� v��rin p�in
            double latitude = extras.getDouble("lng");
            double longitude = extras.getDouble("lat");

            myUserOverlay.clear();

            userPoint = new GeoPoint(latitude, longitude );
            myUserOverlay.addItem(userPoint, "myPoint3", "myPoint3");
            mapView.getOverlays().add(myUserOverlay);

            controller.setCenter(userPoint);

            Log.d("sup", "Longitude:"+longitude+", Latitude:"+latitude);

            for(int a = 0; a<3; a++){

                double minLong = starLocationList.get(a).getLongitude()-0.01;
                double minLat = starLocationList.get(a).getLatitude()-0.01;
                double maxLong = starLocationList.get(a).getLongitude()+0.01;
                double maxLat = starLocationList.get(a).getLatitude()+0.01;

                Log.d("sup", "Star Longitude max:"+maxLong+", min:"+minLong);
                Log.d("sup", "Star Latitude max:"+maxLat+", Latitude min:"+minLat);

                if(latitude < maxLat && latitude > minLat
                        && longitude < maxLong &&  longitude > minLong){

                    myStarOverlay.setIcon(starLocationList.get(a), getResources().getDrawable(android.R.drawable.star_big_off));
                    Log.d("sup", "working");

                }
            }

        }
    }
}