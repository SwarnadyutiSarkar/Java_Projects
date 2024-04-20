import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;

public class VPN_Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 5000;
    private static final String SECRET_KEY = "SecretKeyForVPN";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // Simulate secure handshake (authentication and key exchange)
            byte[] randomBytes = (byte[]) ois.readObject();
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(randomBytes);
            oos.writeObject(randomBytes);

            byte[] encryptedBytes = (byte[]) ois.readObject();
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            oos.writeObject(decryptedBytes);

            // Start encrypted communication
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("Enter message to send to server (type 'exit' to quit): ");
                String message = br.readLine();

                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }

                byte[] encryptedData = cipher.doFinal(message.getBytes());
                oos.writeObject(encryptedData);

                byte[] receivedData = (byte[]) ois.readObject();
                byte[] decryptedData = cipher.doFinal(receivedData);
                String receivedMessage = new String(decryptedData);
                System.out.println("Received from server: " + receivedMessage);
            }

        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException |
                 NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
