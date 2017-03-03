package pl.mateuszkaflowski.udacity_popular_movies.moviedata;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String originalTitle;
    private String posterImageUrl;
    private String overview;
    private float userRating;
    private String releaseDate;

    protected Movie(Parcel in) {
        originalTitle = in.readString();
        posterImageUrl = in.readString();
        overview = in.readString();
        userRating = in.readFloat();
        releaseDate = in.readString();
    }

    public Movie() {

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    public void setPosterImageUrl(String posterImageUrl) {
        this.posterImageUrl = posterImageUrl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(originalTitle);
        parcel.writeString(posterImageUrl);
        parcel.writeString(overview);
        parcel.writeFloat(userRating);
        parcel.writeString(releaseDate);
    }
}
