import java.io.*;
import java.net.*;

import com.google.gson.Gson;

public class server {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int serverCount;
        System.out.println("insert number of servers: ");
        serverCount = System.in.read();
        int nextPort=8888;
        for (int i=0; i<serverCount; i++)
        {
            createServer(nextPort++);
        }
    }
    public static void createServer(int port) throws IOException, URISyntaxException {

        ServerSocket server = new ServerSocket(port);

        URI url = new URI("http://localhost:8080/register"); // Creates a URL object with the load balancer's address.
        HttpURLConnection con = (HttpURLConnection) url.toURL().openConnection(); // Opens a connection to the load balancer.
        con.setRequestMethod("POST");   // Sets the request method to POST.
        con.setDoOutput(true);  // Sets the doOutput flag to true.
        con.setRequestProperty("Content-Type", "application/json; utf-8"); // Sets the Content-Type header to application/json

        Gson gson = new Gson();
        String json = gson.toJson(new serverInfo(server.getInetAddress(), server.getLocalPort()));

        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(json);
        out.flush();    // Flushes the output stream.
        out.close();    // Closes the output stream.

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Server registered successfully with the load balancer.");

            Socket client = server.accept();
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            while (true) {
                if (input.ready()) {
                    String S = input.readLine();
                    alphanumericChecker temp = new alphanumericChecker(S, output);
                    temp.start();
                }
            }
        } else {
            System.out.println("Server registration failed with the load balancer.");
        }
    }
}