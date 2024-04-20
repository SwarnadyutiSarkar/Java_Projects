import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;

public class VPN_Server {
    private static final int SERVER_PORT = 5000;
    private static final String SECRET_KEY = "SecretKeyForVPN";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server is running on port " + SERVER_PORT);

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
        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {

            // Simulate secure handshake (authentication and key exchange)
            SecureRandom secureRandom = new SecureRandom();
            byte[] randomBytes = new byte[16];
            secureRandom.nextBytes(randomBytes);

            oos.writeObject(randomBytes);
            byte[] clientRandomBytes = (byte[]) ois.readObject();

            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = cipher.doFinal(clientRandomBytes);

            oos.writeObject(encryptedBytes);

            // Start encrypted communication
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            while (true) {
                byte[] encryptedData = (byte[]) ois.readObject();
                byte[] decryptedData = cipher.doFinal(encryptedData);
                String message = new String(decryptedData);
                System.out.println("Received from client: " + message);

                // Echo back to client
                oos.writeObject(cipher.doFinal(message.getBytes()));
            }

        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException |
                 NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}
