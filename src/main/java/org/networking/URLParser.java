package org.networking;

import java.net.MalformedURLException;
import java.net.*;

/**
 * Hello world!
 *
 */
public class URLParser
{
    public static void main( String[] args ) throws MalformedURLException, URISyntaxException {
        URL misitio = new URL("http://estudiantes.escuelaing.edu.co:65324/notas.html?q=marron+5#seccion");

        System.out.println("URL");
        System.out.println( "Protocol: "  + misitio.getProtocol());
        System.out.println( "Authority: "  + misitio.getAuthority());
        System.out.println( "Host: "  + misitio.getHost());
        System.out.println( "Port: "  + misitio.getPort());
        System.out.println( "Path: "  + misitio.getPath());
        System.out.println( "Query: "  + misitio.getQuery());
        System.out.println( "File: "  + misitio.getFile());
        System.out.println( "Ref: "  + misitio.getRef() + "\n");

        System.out.println("URI");
        URI miuri = new URI("/cliente?t=6&o=25");
        System.out.println( "Path: "  + miuri.getPath());
        System.out.println( "Query: "  + miuri.getQuery());
    }
}
