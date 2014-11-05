package fi.tiko.tamk.spotcatcher.db;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

/**
 * Starts database service.
 */
public class ExternalDatabaseHandler {

    private Activity activity;
    private String TAG = "ExternalDatabaseHandler";

    public ExternalDatabaseHandler(Activity activity) {
        Log.d(TAG, "ExternalDatabaseHandler()");
        this.activity = activity;
    }

    public void fetchTable(String table) {
        Log.d(TAG, "Fetching table: " + table);
        Intent i = new Intent(activity, ExternalDatabaseFetcher.class);
        i.putExtra("table", table);
        activity.startService(i);
    }

}
