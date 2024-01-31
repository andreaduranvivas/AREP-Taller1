package arep.org.networking;

import java.io.*;
import java.net.*;
public class EchoClient {
 public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null; //Flujo de salida
        BufferedReader in = null; //Flujo de entrada

        try {
            echoSocket = new Socket("127.0.0.1", 35000); //desde mi servidor
            out = new PrintWriter(echoSocket.getOutputStream(), true); //si es verdad, limpia la salida del buffer
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Don’t know about host!.");
            System.exit(1);

        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for "
                    + "the connection to: localhost.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;

        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("echo: " + in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
