package fi.tiko.tamk.spotcatcher.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSTracker extends Service{

    LocationManager locationManager;
    LocationListener locationListener;
    String locationProvider;
    Double latitude;
    Double longitude;


    @Override
    public IBinder onBind(Intent arg0){

        Log.d("LocationService", "onBind");
        return null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        return START_STICKY;

    }

    @Override
    public void onCreate(){

        super.onCreate();

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener(){

            public void onLocationChanged(Location location) {

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                String latitude_string = Double.toString(location.getLatitude());
                String longitude_string = Double.toString(location.getLongitude());

                Log.d("sup", "Latitude is:" + latitude_string);
                Log.d("sup", "Longitude is:" + longitude_string);

                Intent i = new Intent("fi.tiko.tamk.spotcatcher.ui.LOCATION_UPDATED");
                i.putExtra("lat", location.getLatitude());
                i.putExtra("lng", location.getLongitude());
                sendBroadcast(i);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}

        };

    }

    @Override
    public void onDestroy(){


    }

    public void sentToServer(){

        Log.d("LocationService", "Successfully sent to server");

    }

}
