package fi.tiko.tamk.spotcatcher.db;

/**
 * Created by otrdiz on 30.10.2014.
 */
public class Address {

    private static final String DATABASE_FETCH = "http://koti.tamk.fi/~c2htoiva/spot-catcher/getdata.php";

    public static String buildFetchAddress(String table) {
        return DATABASE_FETCH + "?table=" + table;
    }
}
