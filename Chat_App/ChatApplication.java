import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatApplication {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try {
            InetAddress host = InetAddress.getLocalHost();
            System.out.println("Server Address: " + host.getHostAddress());

            System.out.println("1. Start Server");
            System.out.println("2. Connect as Client");
            System.out.print("Choose an option: ");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                startServer();
            } else if (option == 2) {
                System.out.print("Enter server IP address: ");
                String serverIp = scanner.nextLine();
                connectAsClient(serverIp);
            } else {
                System.out.println("Invalid option!");
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for client to connect...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("You: ");
                String message = scanner.nextLine();
                out.println(message);
                if (message.equalsIgnoreCase("bye")) {
                    break;
                }

                String receivedMessage = in.readLine();
                System.out.println("Client: " + receivedMessage);
                if (receivedMessage.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            scanner.close();
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Chat ended.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void connectAsClient(String serverIp) {
        try (Socket socket = new Socket(serverIp, PORT)) {
            System.out.println("Connected to server: " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String receivedMessage = in.readLine();
                System.out.println("Server: " + receivedMessage);
                if (receivedMessage.equalsIgnoreCase("bye")) {
                    break;
                }

                System.out.print("You: ");
                String message = scanner.nextLine();
                out.println(message);
                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            scanner.close();
            in.close();
            out.close();
            System.out.println("Chat ended.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
