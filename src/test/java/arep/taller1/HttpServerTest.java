package arep.taller1;

import org.junit.Test;
import static org.junit.Assert.*;

public class HttpServerTest {

    /**
     * Test case for the function `extractMovieTitleFromUri` in the `HttpServer` class.
     *
     * This test case validates that the `extractMovieTitleFromUri` function correctly extracts
     * the movie title from the given URI.
     *
     * @return None
     */
    @Test
    public void testExtractMovieTitleFromUri() {
        assertNotEquals("Guardian", HttpServer.extractMovieTitleFromUri("/movie"));
        assertEquals("Inception", HttpServer.extractMovieTitleFromUri("/movie?movieTitle=Inception"));
        assertNotEquals("Interstellar", HttpServer.extractMovieTitleFromUri("/movie?movieTitle=interstellar"));
        assertNotEquals("Interstellar", HttpServer.extractMovieTitleFromUri("/movie?otherParam=123&movieTitle=Interstellar"));
    }
}
