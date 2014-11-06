package fi.tiko.tamk.spotcatcher.db.objects;

/**
 * Created by Spjass on 6.11.2014.
 */
public class Piste {

    private int id;
    private int score;
    private String user;
    private String place;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
