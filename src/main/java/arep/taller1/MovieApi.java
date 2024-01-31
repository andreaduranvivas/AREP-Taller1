package arep.taller1;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieApi {

    private static final MovieCache movieCache = new MovieCache();
    private static final String API_KEY = "b7232f2";
    private static final String BASE_URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&t=";
    private static final String USER_AGENT = "Mozilla/5.0";
    private final Gson gson = new Gson();

    public String getMovieInfo(String movieTitle) {
        String cacheMovieInfo = movieCache.getMovieInfo(movieTitle);

        if (cacheMovieInfo != null) {
            return cacheMovieInfo;
        } else {
            try {
                String movieInfo = makeApiRequest(movieTitle);
                movieCache.addMovieToCache(movieTitle, movieInfo);
                return movieInfo;
            } catch (Exception e) {
                e.printStackTrace();
                return "Error al obtener información de la película";
            }
        }
    }

    public String makeApiRequest(String movieTitle) throws IOException {

        System.out.println("Movie Title: " + movieTitle);

        URL url = new URL(BASE_URL + movieTitle);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = conn.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer content = new StringBuffer();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            System.out.println("API Response: " + content.toString());

            return content.toString();

        } else {
            System.out.println("GET request not worked");
            return null;
        }
    }
}
