import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import com.google.gson.Gson;

public class server {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(8888);

        URL url = new URL("http://localhost:8080/register"); // Creates a URL object with the load balancer's address.
        HttpURLConnection con = (HttpURLConnection) url.openConnection(); // Opens a connection to the load balancer.
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
                String S = input.readLine();
                new alphanumericChecker(S, output).start();
            }
        } else {
            System.out.println("Server registration failed with the load balancer.");
        }
    }
}