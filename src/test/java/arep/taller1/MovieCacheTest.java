package arep.taller1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieCacheTest {

    @Test
    void testAddAndGetMovieInfo() {
        MovieCache movieCache = new MovieCache();
        String movieTitle = "Inception";
        String movieInfoJson = "{\"Title\":\"Inception\",\"Year\":\"2010\"}";

        movieCache.addMovieToCache(movieTitle, movieInfoJson);
        String cachedMovieInfo = movieCache.getMovieInfo(movieTitle);
        assertEquals(movieInfoJson, cachedMovieInfo);
    }

    @Test
    void testConcurrentAddAndGetMovieInfo() throws InterruptedException {
        MovieCache movieCache = new MovieCache();
        String movieTitle = "Inception";
        String movieInfoJson = "{\"Title\":\"Inception\",\"Year\":\"2010\"}";

        Thread addThread = new Thread(() -> {
            movieCache.addMovieToCache(movieTitle, movieInfoJson);
        });

        Thread getThread = new Thread(() -> {
            String cachedMovieInfo = movieCache.getMovieInfo(movieTitle);
            assertEquals(movieInfoJson, cachedMovieInfo);
        });

        addThread.start();
        getThread.start();

        addThread.join();
        getThread.join();
    }
}
