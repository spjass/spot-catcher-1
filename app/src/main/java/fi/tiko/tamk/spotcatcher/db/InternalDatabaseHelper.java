package fi.tiko.tamk.spotcatcher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * SQL helper class. Holds methods for creating tables and database variables.
 */
public class InternalDatabaseHelper extends SQLiteOpenHelper {
    //  Change to create new tables
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_RAKENNELMAT = "Rakennelmat";
    public static final String TABLE_PISTEET = "Pisteet";
    public static final String TABLE_RAKENNUKSET = "Rakennukset";
    public static final String TABLE_HIGHSCORE = "Highscore";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_DATE = "date";

    private static final String DATABASE_NAME = "spotcatcherdb";



    // Database creation sql statement
    private static final String DATABASE_CREATE_RAKENNELMAT = "create table "
            + TABLE_RAKENNELMAT + "(" + COLUMN_ID
            + " integer not null primary key autoincrement unique, "
            + COLUMN_LATITUDE + " float(3) not null,"
            + COLUMN_LONGITUDE + " float(3) not null,"
            + COLUMN_NAME + " text,"
            + COLUMN_TYPE + " integer);";

    private static final String DATABASE_CREATE_RAKENNUKSET = "create table "
            + TABLE_RAKENNUKSET + "(" + COLUMN_ID
            + " integer not null primary key autoincrement unique, "
            + COLUMN_LATITUDE + " float(3) not null,"
            + COLUMN_LONGITUDE + " float(3) not null,"
            + COLUMN_NAME + " text);";

    private static final String DATABASE_CREATE_HIGHSCORE = "create table "
            + TABLE_HIGHSCORE + "(" + COLUMN_ID
            + " integer not null primary key autoincrement unique, "
            + COLUMN_SCORE + " integer not null,"
            + COLUMN_USER + " text not null,"
            + COLUMN_PLACE + " text not null,"
            + COLUMN_DATE + " timestamp not null);";

    private static final String DATABASE_CREATE_PISTEET = "create table "
            + TABLE_PISTEET + "(" + COLUMN_ID
            + " integer not null primary key autoincrement unique, "
            + COLUMN_LATITUDE + " float(3) not null,"
            + COLUMN_LONGITUDE + " float(3) not null,"
            + COLUMN_NAME + " text unique"
            + COLUMN_SCORE + " integer"
            + COLUMN_DESCRIPTION + " text);";

    public InternalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    /**
     * Creates database tables for matches and tournaments.
     *
     * @param database Used database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d("InternalDatabaseHelper", "creating databases");
        database.execSQL(DATABASE_CREATE_RAKENNELMAT);
        database.execSQL(DATABASE_CREATE_RAKENNUKSET);
        database.execSQL(DATABASE_CREATE_PISTEET);
        database.execSQL(DATABASE_CREATE_HIGHSCORE);
    }

    /**
     * Creates new database tables if version upgraded
     *
     * @param db Used database
     * @param oldVersion Old database version
     * @param newVersion New database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(InternalDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PISTEET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAKENNELMAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAKENNUKSET);
        onCreate(db);
    }

}