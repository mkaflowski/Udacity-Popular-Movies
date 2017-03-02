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
        movie.setPosterImageUrl(imageUrlCore + jsonObject.get("poster_path"));
        movie.setReleaseDate(jsonObject.get("release_date").toString());
        movie.setUserRating(Float.parseFloat(jsonObject.get("vote_average").toString()));

        return movie;
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
