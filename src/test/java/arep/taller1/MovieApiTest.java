package arep.taller1;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MovieApiTest {

    /**
     * Makes an API request for a movie title and returns the details of the movie.
     *
     * @return             the details of the movie as a JSON string
     * @throws IOException if an error occurs while making the API request
     */
    private class MockMovieApi extends MovieApi {
        @Override
        public String makeApiRequest(String movieTitle) throws IOException {
            switch (movieTitle) {
                case "Inception":
                    return "{\"Title\":\"Inception\",\"Year\":\"2010\",\"Rated\":\"PG-13\",\"Runtime\":\"148 min\"}";
                case "Interstellar":
                    return "{\"Title\":\"Interstellar\",\"Year\":\"2014\",\"Rated\":\"PG-13\",\"Runtime\":\"169 min\"}";
                default:
                    return null; // Not Found
            }
        }
    }

    @Test
    public void testGetMovieInfo() {
        MovieApi movieApi = new MockMovieApi();

        // Caso 1: Película encontrada en caché
        String cachedResult = movieApi.getMovieInfo("Inception");
        assertNotNull(cachedResult);
        assertTrue(cachedResult.contains("\"Title\":\"Inception\""));

        // Caso 2: Película no encontrada en caché, se obtiene de la API
        String apiResult = movieApi.getMovieInfo("Interstellar");
        assertNotNull(apiResult);
        assertTrue(apiResult.contains("\"Title\":\"Interstellar\""));

        // Caso 3: Película no encontrada en caché y no se puede obtener de la API (simulando un error)
        String errorResult = movieApi.getMovieInfo("NonexistentMovie");
        assertNotNull(errorResult);
        assertTrue(errorResult.contains("Movie not found"));
    }


    @Test
    public void testMakeApiRequest() {
        MovieApi movieApi = new MovieApi();

        // Caso 1: Película encontrada en la API
        try {
            String apiResult = movieApi.makeApiRequest("Inception");
            assertNotNull(apiResult);
            assertTrue(apiResult.contains("\"Title\":\"Inception\""));
        } catch (IOException e) {
            fail("IOException in makeApiRequest: " + e.getMessage());
        }

        // Caso 2: Película no encontrada en la API (simulando un error)
        try {
            String apiResult = movieApi.makeApiRequest("NonexistentMovie");
            assertNotNull(apiResult); // Se espera que diga que no existe la película
        } catch (IOException e) {
            fail("IOException in makeApiRequest: " + e.getMessage());
        }
    }
}
