package pl.mateuszkaflowski.udacity_popular_movies.moviedata;

import com.socks.library.KLog;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MovieCollector {

    private static final String imageUrlCore = "https://image.tmdb.org/t/p/w500/";

    public static List<Movie> getPopularMovies(final String API_KEY) throws Throwable {
        List<Movie> movieList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        String address = "https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY;
        Object obj = parser.parse(getResponseFromHttpUrl(address));

        JSONObject jsonObject = (JSONObject) obj;

        JSONArray jsonarray = (JSONArray) jsonObject.get("results");
        KLog.d(jsonarray.size());

        for (Object jsonMovie : jsonarray) {
            JSONObject podcastJson = (JSONObject) jsonMovie;
            Movie movie = getMovie(podcastJson);
            if (movie != null)
                movieList.add(movie);
        }

        return movieList;
    }

    public static List<Movie> getTopRatedMovies(final String API_KEY) throws Throwable {
        List<Movie> movieList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        String address = "https://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY;
        Object obj = parser.parse(getResponseFromHttpUrl(address));

        JSONObject jsonObject = (JSONObject) obj;

        JSONArray jsonarray = (JSONArray) jsonObject.get("results");
        KLog.d(jsonarray.size());

        for (Object jsonMovie : jsonarray) {
            JSONObject podcastJson = (JSONObject) jsonMovie;
            Movie movie = getMovie(podcastJson);
            if (movie != null)
                movieList.add(movie);
        }

        return movieList;
    }

    private static Movie getMovie(JSONObject jsonObject) {
        Movie movie = new Movie();

        movie.setOriginalTitle(jsonObject.get("original_title").toString());
        movie.setOverview(jsonObject.get("overview").toString());
        movie.setPosterImageUrl(imageUrlCore + jsonObject.get("poster_path"));
        movie.setId(jsonObject.get("id").toString());
        movie.setReleaseDate(jsonObject.get("release_date").toString());
        movie.setUserRating(jsonObject.get("vote_average").toString());

        return movie;
    }

    public static List<Trailer> getTrailers(final String API_KEY, Movie movie) throws Throwable {
        List<Trailer> trailerList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        String address = "https://api.themoviedb.org/3/movie/"+movie.getId()+"/videos?api_key="+API_KEY;
        Object obj = parser.parse(getResponseFromHttpUrl(address));

        JSONObject jsonObject = (JSONObject) obj;

        JSONArray jsonArray = (JSONArray) jsonObject.get("results");
        KLog.d(jsonArray.size());

        for (Object jsonTrailer : jsonArray) {
            JSONObject jsonObj = (JSONObject) jsonTrailer;
            Trailer trailer = getTrailer(jsonObj);
            if (trailer != null)
                trailerList.add(trailer);
        }

        return trailerList;
    }

    public static List<Review> getReviews(final String API_KEY, Movie movie) throws Throwable {
        List<Review> reviews = new ArrayList<>();

        JSONParser parser = new JSONParser();

        String address = "https://api.themoviedb.org/3/movie/"+movie.getId()+"/reviews?api_key="+API_KEY;
        Object obj = parser.parse(getResponseFromHttpUrl(address));

        JSONObject jsonObject = (JSONObject) obj;

        JSONArray jsonArray = (JSONArray) jsonObject.get("results");
        KLog.d(jsonArray.size());

        for (Object jsonTrailer : jsonArray) {
            JSONObject jsonObj = (JSONObject) jsonTrailer;
            Review review = getReview(jsonObj);
            if (review != null)
                reviews.add(review);
        }

        return reviews;
    }

    private static Review getReview(JSONObject jsonObj) {
        Review review = new Review();

        review.setAuthor(jsonObj.get("author").toString());
        review.setContent(jsonObj.get("content").toString());

        return review;
    }

    private static Trailer getTrailer(JSONObject jsonObject) {
        Trailer trailer = new Trailer();

        trailer.setId(jsonObject.get("id").toString());
        trailer.setIso(jsonObject.get("iso_3166_1").toString());
        trailer.setKey(jsonObject.get("key").toString());
        trailer.setName(jsonObject.get("name").toString());
        trailer.setType(jsonObject.get("type").toString());

        return trailer;
    }

    private static String getResponseFromHttpUrl(String url) throws Throwable {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return response.toString();
    }
}
