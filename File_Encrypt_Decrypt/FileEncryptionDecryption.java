import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class FileEncryptionDecryption {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Encrypt File");
            System.out.println("2. Decrypt File");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter file path: ");
            String filePath = scanner.nextLine();

            if (option == 1) {
                System.out.print("Enter encryption key (must be 16 characters long): ");
                String key = scanner.nextLine();
                encryptFile(filePath, key);
                System.out.println("File encrypted successfully!");
            } else if (option == 2) {
                System.out.print("Enter decryption key (must be 16 characters long): ");
                String key = scanner.nextLine();
                decryptFile(filePath, key);
                System.out.println("File decrypted successfully!");
            } else {
                System.out.println("Invalid option!");
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void encryptFile(String filePath, String key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IOException {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        byte[] encryptedBytes = cipher.doFinal(fileContent);

        Path encryptedFilePath = Paths.get(filePath + ".encrypted");
        Files.write(encryptedFilePath, Base64.getEncoder().encode(encryptedBytes));
    }

    public static void decryptFile(String filePath, String key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IOException {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] encryptedBytes = Base64.getDecoder().decode(Files.readAllBytes(Paths.get(filePath)));
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        Path decryptedFilePath = Paths.get(filePath.replace(".encrypted", "_decrypted"));
        Files.write(decryptedFilePath, decryptedBytes);
    }
}
