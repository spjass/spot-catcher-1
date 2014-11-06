package fi.tiko.tamk.spotcatcher.db;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import fi.tiko.tamk.spotcatcher.services.JSONParser;

/**
 * Fetches stuff from database and sends it to JSONParser?
 */
public class ExternalDatabaseService extends Service {

    private final String TAG = "ExternalDatabaseHandler";
    private DatabaseConnection dbConn;
    private String table;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStartCommand()");
        table = (String) intent.getExtras().get("table");

        dbConn = new DatabaseConnection(table);
        dbConn.execute();

        return flags;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return null;
    }

    class DatabaseConnection extends AsyncTask<Void, Void, Void> {

        private HttpURLConnection con;
        private String address;
        private BufferedReader reader;

        public DatabaseConnection(String table) {
            address = URLBuilder.buildFetchAddress(table);

            Log.d(TAG, "DatabaseConnection()");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(address);
                con = (HttpURLConnection) url.openConnection();

                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String response = readStream(reader);

                JSONParser parser = new JSONParser();
                parser.handleData(response, table);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }


            Intent i = new Intent();
            i.setAction("fi.tiko.tamk.spotcatcher.fetchFinished");
            sendBroadcast(i);

            return null;
        }

        protected String readStream(BufferedReader in) {

            StringBuilder response = new StringBuilder();
            String inputLine;

            try {
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response.toString();
        }
    }
}
