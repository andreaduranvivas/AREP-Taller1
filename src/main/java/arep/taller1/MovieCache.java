package arep.taller1;

import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MovieCache {

    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();

    /**
     * Retrieves the movie information for the given movie title.
     *
     * @param  movieTitle  the title of the movie
     * @return             the movie information for the given title
     */
    public String getMovieInfo(String movieTitle) {
        return cache.get(movieTitle);
    }

    /**
     * Adds a movie to the cache.
     *
     * @param  movieTitle     the title of the movie
     * @param  movieInfoJson  the JSON representation of the movie info
     */
    public void addMovieToCache(String movieTitle, String movieInfoJson) {
        cache.put(movieTitle, movieInfoJson);
    }

    /**
     * Adds a movie to the cache.
     *
     * @param  movieTitle        the title of the movie
     * @param  movieInfoObject   an object containing information about the movie
     * @return                   none
     */
    public void addMovieToCache(String movieTitle, Object movieInfoObject) {
        String movieInfoJson = gson.toJson(movieInfoObject);
        cache.put(movieTitle, movieInfoJson);
    }

    /**
     * Retrieves the movie information object for the given movie title.
     *
     * @param  movieTitle  the title of the movie
     * @param  clazz       the class of the movie information object
     * @return             the movie information object
     */
    public <T> T getMovieInfoObject(String movieTitle, Class<T> clazz) {
        String movieInfoJson = cache.get(movieTitle);
        return gson.fromJson(movieInfoJson, clazz);
    }
}