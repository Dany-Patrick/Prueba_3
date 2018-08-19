package cl.dany.prueba_3.models;

import java.io.Serializable;

public class Place implements Serializable {
    private String name, description, uid, owner, latitude, longitude;
    private float ranking;
    private boolean visited;


    public Place() {
    }

    public Place(String name, String description, String uid, String owner, String latitude, String longitude, float ranking, boolean visited) {
        this.name = name;
        this.description = description;
        this.uid = uid;
        this.owner = owner;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ranking = ranking;
        this.visited = visited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public float getRanking() {
        return ranking;
    }

    public void setRanking(float ranking) {
        this.ranking = ranking;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
