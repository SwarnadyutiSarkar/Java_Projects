import java.io.*;
import java.net.*;

public class VideoStreamingServer {
    private static final int PORT = 8080;
    private static final String VIDEO_FILE_PATH = "path/to/video.mp4";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
             FileInputStream fis = new FileInputStream(VIDEO_FILE_PATH)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
                bos.flush();
            }

            System.out.println("Video sent successfully.");

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}
