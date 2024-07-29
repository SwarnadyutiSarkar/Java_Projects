// Server side
import java.net.*;
import java.io.*;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server started. Listening for incoming connections...");

        Socket socket = serverSocket.accept();
        System.out.println("Incoming connection from " + socket.getInetAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Client: " + inputLine);
            out.println("Server: " + inputLine);
        }

        socket.close();
        serverSocket.close();
    }
}

// Client side
import java.net.*;
import java.io.*;

public class ChatClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", 8000);
        System.out.println("Connected to the server");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        String inputLine;
        while ((inputLine = userInput.readLine()) != null) {
            out.println(inputLine);
            System.out.println("Server: " + in.readLine());
        }

        socket.close();
    }
}