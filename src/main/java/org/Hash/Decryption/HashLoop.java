package org.Hash.Decryption;


import org.Hash.Helper.ConnectionProvider;


import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;

public class HashLoop {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        // Create an instance of the SHA-256 message digest algorithm
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Hash-256 :");
        String userHash = scanner.nextLine();
        Connection connection = ConnectionProvider.getSqlConnection();


        for (int i = 1; i <= 50000; i++) {
            // Convert the current number to a byte array
            ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
            buffer.putInt(i);
            byte[] bytes = buffer.array();

            digest.update(bytes);


            byte[] hash = digest.digest();

           try{
               PreparedStatement statement = connection.prepareStatement("INSERT INTO hash values(?,?)");
               statement.setString(1,bytesToHex(hash));
               statement.setString(2, String.valueOf(i));
               statement.executeUpdate();
               System.out.println(i);
           }catch (Exception exception){
               System.out.println(exception);
           }

//            if(bytesToHex(hash).equals(userHash)) {
//                System.out.println("***************Your password is :" + i);
//                break;
//            }
//            System.out.println("Hash value for " + i + ": " + bytesToHex(hash));

        }

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
