package DSA;

import java.util.ArrayList;

public class fibonacci {

    public static void Fibonacci(int length){
        int first = 0, second =1;

        System.out.print(first+" "+second);
        for(int i=2; i<length; i++){
            int next = first+second;
            System.out.print(" "+next);
            first=second;
            second = next;
        }

    }
}
