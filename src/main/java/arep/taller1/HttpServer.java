package arep.taller1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private static final MovieApi movieApi = new MovieApi();

    /**
     * Listens for incoming client connections on a specific port and handles the requests.
     *
     * @throws IOException   if an I/O error occurs while listening on the port
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;

        while(running){
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean firstLine = true;
            String uriStr = "";

            while ((inputLine = in.readLine()) != null) {
                if (firstLine){
                    uriStr = inputLine.split(" ")[1];
                    firstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            if (uriStr.startsWith("/movie")){
                outputLine = httpMovie(uriStr);
            }else{
                outputLine = httpError();
            }

            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();

        }

        serverSocket.close();
    }

    private static String extractMovieTitleFromUri(String uri) {
        return uri.substring("/movie".length()).trim();
    }


    /**
     * Generates a HTTP response for a movie search.
     *
     * @param  uri   the URI for the movie search
     * @return       the HTTP response as a string
     */
    public static String httpMovie(String uri){

        String movieTitle = "Guardian";
        String movieInfo = movieApi.getMovieInfo(movieTitle);

        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Search Your Favorite Movie</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "        <style>\n" +
                "            #mainContainer {\n" +
                "                margin: auto;\n" +
                "                width: 80%;\n" +
                "            }\n" +
                "            #movieContainer {\n" +
                "                display: flex;\n" +
                "                justify-content: space-between;\n" +
                "                align-items: center;\n" +
                "                padding: 20px;\n" +
                "                background-color: #f0f0f0;\n" +
                "                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);\n" +
                "                border-radius: 10px;\n" +
                "                margin: 20px auto;\n" +
                "                width: 80%;\n" +
                "            }\n" +
                "            #posterContainer {\n" +
                "                flex: 1;\n" +
                "            }\n" +
                "            #moviePoster {\n" +
                "                max-width: 100%;\n" +
                "                height: auto;\n" +
                "            }\n" +
                "            #infoContainer {\n" +
                "                flex: 2;\n" +
                "                text-align: left;\n" +
                "            }\n" +
                "            #movieTitle {\n" +
                "                font-size: 1.1em;\n" +
                "                text-align: center;\n" +
                "                margin-bottom: 20px;\n" +
                "            }\n" +
                "        </style>\n" +
                "   </head>\n" +
                "    <body>\n" +
                "        <div id=\"mainContainer\">\n" +
                "            <h1>Search Your Favorite Movie</h1>\n" +
                "            <form>\n" +
                "                <label for=\"movieTitle\">Movie Title:</label><br>\n" +
                "                <input type=\"text\" id=\"movieTitle\" name=\"movieTitle\"><br><br>\n" +
                "                <input type=\"button\" value=\"Search Movie\" onclick=\"loadMovieInfo()\">\n" +
                "            </form> \n" +
                "            <div id=\"movieContainer\">\n" +
                "                <div id=\"posterContainer\">\n" +
                "                    <img id=\"moviePoster\" src=\"\" alt=\"Movie Poster\">\n" +
                "                </div>\n" +
                "                <div id=\"infoContainer\">\n" +
                "                    <h2 id=\"movieTitle\"></h2>\n" +
                "                    <div id=\"movieInfo\"></div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <script>\n" +
                "                const movieApi = new MovieApi();\n" +
                "                function loadMovieInfo() {\n" +
                "                    let movieTitle = document.getElementById(\"movieTitle\").value;\n" +
                "                    document.getElementById('movieContainer').style.display = 'flex';\n" +
                "                    movieApi.getMovieInfo(movieTitle, function(movieData) {\n" +
                "                        let movieTitleElement = document.getElementById('movieTitle');\n" +
                "                        let movieInfoElement = document.getElementById('movieInfo');\n" +
                "                        let moviePosterElement = document.getElementById('moviePoster');\n" +
                "                        movieTitleElement.textContent = movieData.Title;\n" +
                "                        moviePosterElement.src = movieData.Poster;\n" +
                "                        movieInfoElement.innerHTML = '';\n" +
                "                        for (let key in movieData) {\n" +
                "                            movieInfoElement.innerHTML += `<strong>${key}:</strong> ${movieData[key]}<br>`;\n" +
                "                        }\n" +
                "                    });\n" +
                "                }\n" +
                "            </script>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";

        return outputLine;
    }






    /**
     * Generates an HTTP error response.
     *
     * @return          the HTTP error response as a string
     */
    public static String httpError(){
        return "HTTP/1.1 400 Not Found\n\r"
                + "Content-Type:text/html\r\n"
                +"\r\n"
                +"<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Error. It's not the correct URL</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "<h1>Error. The URL is not the correct</h1>"+
                "    </body>\n" +
                "</html>";
    }
}