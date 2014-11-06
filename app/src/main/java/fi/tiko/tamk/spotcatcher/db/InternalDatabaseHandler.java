package fi.tiko.tamk.spotcatcher.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import fi.tiko.tamk.spotcatcher.db.objects.Highscore;
import fi.tiko.tamk.spotcatcher.db.objects.Piste;
import fi.tiko.tamk.spotcatcher.db.objects.Rakennelma;
import fi.tiko.tamk.spotcatcher.db.objects.Rakennus;

/**
 * SQL helper class. Holds methods for creating tables and database variables.
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class InternalDatabaseHandler {
    // Database fields
    private SQLiteDatabase database;
    private InternalDatabaseHelper dbHelper;

    private String[] allColumnsRakennelmat = { InternalDatabaseHelper.COLUMN_ID,
            InternalDatabaseHelper.COLUMN_LATITUDE,
            InternalDatabaseHelper.COLUMN_LONGITUDE,
            InternalDatabaseHelper.COLUMN_NAME
    };

    private String[] allColumnsRakennukset = {InternalDatabaseHelper.COLUMN_ID,
            InternalDatabaseHelper.COLUMN_LATITUDE,
            InternalDatabaseHelper.COLUMN_LONGITUDE,
            InternalDatabaseHelper.COLUMN_NAME,
            InternalDatabaseHelper.COLUMN_TYPE
    };

    private String[] allColumnsPisteet = { InternalDatabaseHelper.COLUMN_ID,
            InternalDatabaseHelper.COLUMN_LATITUDE,
            InternalDatabaseHelper.COLUMN_LONGITUDE,
            InternalDatabaseHelper.COLUMN_NAME,
            InternalDatabaseHelper.COLUMN_SCORE,
            InternalDatabaseHelper.COLUMN_DESCRIPTION
    };

    private String[] allColumnsHighscore = { InternalDatabaseHelper.COLUMN_ID,
            InternalDatabaseHelper.COLUMN_SCORE,
            InternalDatabaseHelper.COLUMN_USER,
            InternalDatabaseHelper.COLUMN_PLACE,
            InternalDatabaseHelper.COLUMN_DATE
    };


    /**
     * Constructor for a DataSource object.
     *
     * @param context Activity context
     */
    public InternalDatabaseHandler(Context context) {

        dbHelper = new InternalDatabaseHelper(context);
    }

    /**
     * Opens database connection.
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Closes database connection.
     */
    public void close() {
        dbHelper.close();
    }


    public void addRakennelma(float latitude, float longitude, String name, int type) {
        ContentValues values = new ContentValues();

        values.put(InternalDatabaseHelper.COLUMN_LATITUDE, latitude);
        values.put(InternalDatabaseHelper.COLUMN_LONGITUDE, longitude);
        values.put(InternalDatabaseHelper.COLUMN_NAME, name);
        values.put(InternalDatabaseHelper.COLUMN_TYPE, type);


        database.insert(InternalDatabaseHelper.TABLE_RAKENNELMAT, null,
                values);

    }

    public void addRakennus(float latitude, float longitude, String name, int type) {
        ContentValues values = new ContentValues();

        values.put(InternalDatabaseHelper.COLUMN_LATITUDE, latitude);
        values.put(InternalDatabaseHelper.COLUMN_LONGITUDE, longitude);
        values.put(InternalDatabaseHelper.COLUMN_NAME, name);

        database.insert(InternalDatabaseHelper.TABLE_RAKENNUKSET, null,
                values);

    }

    public void addHighscore(int score, String user, String place) {
        ContentValues values = new ContentValues();

        values.put(InternalDatabaseHelper.COLUMN_SCORE, score);
        values.put(InternalDatabaseHelper.COLUMN_USER, user);
        values.put(InternalDatabaseHelper.COLUMN_PLACE, place);

        database.insert(InternalDatabaseHelper.TABLE_HIGHSCORE, null,
                values);
    }

    public void addPiste(float latitude, float longitude, String name, int score, String description) {
        ContentValues values = new ContentValues();

        values.put(InternalDatabaseHelper.COLUMN_LATITUDE, latitude);
        values.put(InternalDatabaseHelper.COLUMN_LONGITUDE, longitude);
        values.put(InternalDatabaseHelper.COLUMN_NAME, name);
        values.put(InternalDatabaseHelper.COLUMN_SCORE, score);
        values.put(InternalDatabaseHelper.COLUMN_DESCRIPTION, description);

        database.insert(InternalDatabaseHelper.TABLE_PISTEET, null,
                values);
    }




    public void delete(String table, int id) {

        database.delete(table, InternalDatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }


    public Rakennelma getRakennelma(int id) {

        Cursor cursor = database.query(InternalDatabaseHelper.TABLE_RAKENNELMAT,
                allColumnsRakennelmat, InternalDatabaseHelper.COLUMN_ID + " = " + id, null, null, null, null);

        Rakennelma rakennelma = new Rakennelma();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            rakennelma = cursorToRakennelma(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return rakennelma;
    }

    /**
     * Initializes fetched data to a Rakennelma object
     *
     * @param cursor Database cursor
     * @return Rakennelma object
     */
    private Rakennelma cursorToRakennelma(Cursor cursor) {
        Rakennelma rakennelma = new Rakennelma();
        rakennelma.setId(cursor.getInt(0));
        rakennelma.setLat(cursor.getFloat(1));
        rakennelma.setLon(cursor.getFloat(2));
        rakennelma.setName(cursor.getString(3));
        rakennelma.setType(cursor.getInt(4));

        return rakennelma;
    }


    /**
     * Initializes fetched data to a Rakennus object
     *
     * @param cursor Database cursor
     * @return Rakennus object
     */
    private Rakennus cursorToRakennus(Cursor cursor) {
        Rakennus rakennus= new Rakennus();
        rakennus.setId(cursor.getInt(0));
        rakennus.setLat(cursor.getFloat(1));
        rakennus.setLon(cursor.getFloat(2));
        rakennus.setName(cursor.getString(3));

        return rakennus;
    }

    /**
     * Initializes fetched data to a Piste object
     *
     * @param cursor Database cursor
     * @return Piste object
     */
    private Piste cursorToPiste(Cursor cursor) {
        Piste piste= new Piste();
        piste.setId(cursor.getInt(0));
        piste.setScore(cursor.getInt(1));
        piste.setUser(cursor.getString(2));
        piste.setPlace(cursor.getString(3));

        return piste;
    }

    /**
     * Initializes fetched data to a Highscore object
     *
     * @param cursor Database cursor
     * @return Highscore object
     */
    private Highscore cursortToHighscore (Cursor cursor) {
        Highscore highscore = new Highscore();
        highscore.setId(cursor.getInt(0));
        highscore.setScore(cursor.getInt(1));
        highscore.setLat(cursor.getFloat(2));
        highscore.setLon(cursor.getFloat(3));
        highscore.setName(cursor.getString(4));
        highscore.setDate(cursor.getString(5));

        return highscore;
    }


}

