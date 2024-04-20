import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class VehicleRecognitionClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load OpenCV native library

        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nVehicle Recognition System");
                System.out.println("1. Upload Image");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        oos.writeObject(getImageData());
                        int detectedCars = (int) ois.readObject();
                        System.out.println("Number of Cars Detected: " + detectedCars);
                        break;

                    case 2:
                        oos.writeObject("EXIT");
                        System.out.println("Exiting the program. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    private static byte[] getImageData() throws IOException {
        System.out.print("Enter image file path: ");
        Scanner scanner = new Scanner(System.in);
        String imagePath = scanner.nextLine();
        Mat image = Imgcodecs.imread(imagePath);
        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, byteMat);
        return byteMat.toArray();
    }
}
