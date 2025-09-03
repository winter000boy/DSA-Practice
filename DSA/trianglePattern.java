package DSA;

import java.util.Scanner;

public class trianglePattern {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("ENter the depth : ");
        int dept = sc.nextInt();

        for (int i=1; i<=dept; i++){
            for(int j=1; j<=i; j++){
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
