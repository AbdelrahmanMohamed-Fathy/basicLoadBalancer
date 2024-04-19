import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class server {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(8888);

        URL url = new URL("http://localhost:8080/register"); // Creates a URL object with the load balancer's address.
        HttpURLConnection con = (HttpURLConnection) url.openConnection(); // Opens a connection to the load balancer.
        con.setRequestMethod("POST");   // Sets the request method to POST.
        con.setDoOutput(true);  // Sets the doOutput flag to true.
        DataOutputStream out = new DataOutputStream(con.getOutputStream()); // Opens a data output stream to send the string.
        out.writeBytes(String.valueOf(new serverInfo(server.getInetAddress(), server.getLocalPort())));    // Writes the string to the output stream.
        out.flush();    // Flushes the output stream.
        out.close();
        Socket client = server.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter output = new PrintWriter(client.getOutputStream(),true);
        while (true){
            String S = input.readLine();
            new alphanumericChecker(S,output).start();
        }
    }
}