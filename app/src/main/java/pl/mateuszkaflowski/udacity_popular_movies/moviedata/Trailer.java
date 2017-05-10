package pl.mateuszkaflowski.udacity_popular_movies.moviedata;

/**
 * Created by Mateusz on 08.05.2017.
 */

public class Trailer {

    private String id;
    private String iso;
    private String key;
    private String name;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYoutubeLink(){
        return "https://www.youtube.com/watch?v="+key;
    }
}
