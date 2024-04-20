import java.io.*;
import java.net.*;

public class VideoStreamingClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";
    private static final String VIDEO_FILE_PATH = "received_video.mp4";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
             FileOutputStream fos = new FileOutputStream(VIDEO_FILE_PATH)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("Video received successfully.");

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
