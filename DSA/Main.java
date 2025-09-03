package DSA;

import java.util.Scanner;

import static DSA.fibonacci.Fibonacci;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter The length : ");
        int length = sc.nextInt();

        Fibonacci(length);
    }
}
