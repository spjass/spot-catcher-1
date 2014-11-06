package fi.tiko.tamk.spotcatcher.db.objects;

/**
 * Created by Spjass on 6.11.2014.
 */
public class Rakennelma {

    private int id;
    private float lat;



    private float lon;
    private String name;
    private int type;


    public Rakennelma() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
