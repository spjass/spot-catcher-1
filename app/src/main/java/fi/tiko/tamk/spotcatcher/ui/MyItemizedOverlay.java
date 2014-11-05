package fi.tiko.tamk.spotcatcher.ui;

import java.util.ArrayList;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
    private Drawable pDefaultMarker;
    private ResourceProxy pResourceProxy;

    public MyItemizedOverlay(Drawable pDefaultMarker,
                             ResourceProxy pResourceProxy) {

        super(pDefaultMarker, pResourceProxy);

        this.pDefaultMarker = pDefaultMarker;
        this.pResourceProxy = pResourceProxy;

    }

    public void addItem(GeoPoint p, String title, String snippet){

        OverlayItem newItem = new OverlayItem(title, snippet, p);
        overlayItemList.add(newItem);
        populate();

    }

    public void setIcon(GeoPoint p, Drawable marker){

        for(int i = 0; i<overlayItemList.size(); i++){

            if(overlayItemList.get(i).getPoint()==p){

                overlayItemList.get(i).setMarker(marker);

            }
        }

        Log.d("sup", "working2");

    }

    public void clear(){

        overlayItemList.clear();

    }

    @Override
    public boolean onSnapToItem(int arg0, int arg1, Point arg2, IMapView arg3) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected OverlayItem createItem(int arg0) {
        // TODO Auto-generated method stub
        return overlayItemList.get(arg0);
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return overlayItemList.size();
    }

}