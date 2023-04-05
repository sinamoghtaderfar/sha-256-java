package org.Hash.Generator;
import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {

        public static void main(String[] args) {
            System.out.println("Enter your min length and max length : ");
            Scanner scanner = new Scanner(System.in);
            int minLength = Integer.parseInt(scanner.nextLine());
            int maxLength = Integer.parseInt(scanner.nextLine());

            Random random = new Random();
            int passwordLength = random.nextInt(maxLength - minLength + 1) + minLength;
            int password = random.nextInt((int) Math.pow(10, passwordLength));
            System.out.println("Your password is: " + password);
        }
}

