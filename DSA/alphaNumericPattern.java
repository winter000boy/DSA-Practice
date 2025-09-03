package DSA;

import java.util.Scanner;

public class alphaNumericPattern {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the depth : ");
        System.out.println();
        int depth = sc.nextInt();

        char ch = (char)('a' + depth-2);

        for (int i=0; i<depth; i++){

            int number = depth-i;

            if(i%2 == 0){
                for (int j=number; j>=1; j-- ){
                    System.out.print(number  + " ");
                    number --;
                }
                System.out.println();
            }
            else{
                for (char c = ch; c>='a'; c--){
                    System.out.print(c + " ");
                }
                System.out.println();
                ch--;
                ch--;
            }
        }
    }
}
