import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class client {
    public static void main(String[] args) throws IOException, URISyntaxException {
        while (true) {
            // Takes a string as input from the user through the console.
            System.out.println("Enter the string to be sent to the load balancer: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String str = br.readLine();


            // Sends this string to the load balancer for checking through a http request.
            URI url = new URI("http://localhost:8080/check"); // Creates a URL object with the load balancer's address.
            HttpURLConnection con = (HttpURLConnection) url.toURL().openConnection(); // Opens a connection to the load balancer.
            con.setRequestMethod("POST");   // Sets the request method to POST.
            con.setRequestProperty("Content-Type", "text/plain; charset=UTF-8"); // Sets the Content-Type header to text/plain
            con.setDoOutput(true);  // Sets the doOutput flag to true.
            PrintWriter out = new PrintWriter(con.getOutputStream(),true); // Opens a data output stream to send the string.
            out.write(str);   // Writes the string to the output stream.
            out.close();

            // Receives the result from the load balancer as a response to the post request.
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); // Opens a buffered reader to read the response.
            String inputLine;   // Declares a string to store the response.
            StringBuilder content = new StringBuilder(); // Declares a string buffer to store the response.
            while ((inputLine = in.readLine()) != null) { // Reads the response line by line.
                content.append(inputLine);  // Appends the line to the string buffer.
            }
            in.close(); // Closes the buffered reader.
            con.disconnect();    // Disconnects the connection.

            // Prints the received result (True/False).
            System.out.println(content);

            // Asks the user if they want to send another string to the load balancer.
            System.out.println("Do you want to send another string to the load balancer? (Y/N): ");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            String str1 = br1.readLine();
            str1 = str1.toUpperCase();
            str1 = str1.trim(); //Trims the string to remove any leading or trailing whitespaces.
            if (str1.equals("N")) {
                break;
            }
        }
    }
}