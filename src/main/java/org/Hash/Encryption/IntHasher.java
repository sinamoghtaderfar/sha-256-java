package org.Hash.Encryption;

import java.util.Scanner;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IntHasher {
    public static byte[] hashInt(int num) throws NoSuchAlgorithmException {
        // Convert the integer to a byte array
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(num);
        byte[] bytes = buffer.array();

        // Create an instance of the SHA-256 message digest algorithm
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Update the digest with the byte array representation of the integer
        digest.update(bytes);

        // Perform the hash calculation and return the resulting hash value
        return digest.digest();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your password to encrypt : ");
        int num = Integer.parseInt(scanner.nextLine());
        byte[] hash = hashInt(num);
        System.out.println("Hash value: " + bytesToHex(hash));
    }

    // Helper function to convert a byte array to a hexadecimal string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
