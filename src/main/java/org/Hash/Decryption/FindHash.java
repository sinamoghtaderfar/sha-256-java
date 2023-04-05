package org.Hash.Decryption;

import org.Hash.Helper.ConnectionProvider;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import org.apache.commons.lang3.time.StopWatch;

public class FindHash {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Hash-256 :");
        String userHash = scanner.nextLine();

        Connection connection = ConnectionProvider.getSqlConnection();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        StopWatch stopWatch = new StopWatch();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM hash WHERE hash = ?");
            statement.setString(1,userHash);
            ResultSet rs = statement.executeQuery();
            String hashed = null;
            while (rs.next()) {

                hashed = rs.getString("hash");


                System.out.println("Your password is:" + rs.getString("numbers"));
            }
            if (hashed == null) {
                System.out.println("hash is not available in the database, but we will generate it");
                stopWatch.start();

                for (int i = 1; i <= 100000000; i++) {
                    // Convert the current number to a byte array
                    ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
                    buffer.putInt(i);
                    byte[] bytes = buffer.array();

                    // Update the digest with the byte array representation of the number
                    digest.update(bytes);

                    // Perform the hash calculation and print the resulting hash value
                    byte[] hash = digest.digest();

                    if(bytesToHex(hash).equals(userHash)) {
                        System.out.println("***************Your password is :" + i);
                        PreparedStatement sta = connection.prepareStatement("INSERT INTO hash values(?,?)");
                        sta.setString(1,bytesToHex(hash));
                        sta.setString(2, String.valueOf(i));
                        sta.executeUpdate();
                        System.out.println("New Hash save");
                        break;
                    }
                }
                stopWatch.stop();
                System.out.println("Elapsed Time in minutes: "+ stopWatch.getTime());

            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
