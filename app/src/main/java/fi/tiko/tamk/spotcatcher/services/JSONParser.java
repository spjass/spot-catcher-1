package fi.tiko.tamk.spotcatcher.services;

import android.util.Log;

/**
 * Created by otrdiz on 30.10.2014.
 */
public class JSONParser {

    private final String TAG = "JSONParser";
    private String table;

    public void handleData(String response, String table) {
        this.table = table;
        parseJSON(response);
    }

    protected void parseJSON(String json) {

        if (json != null) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Failed parsing JSON");
            }
        }
    }
}
