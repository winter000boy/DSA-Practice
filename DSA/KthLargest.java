package DSA;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class KthLargest {
    public static void main(String[] args) {

        Integer[] array = {1, 2, 3, 5, 7, 3, 99, 76, 54, 67, 2, 21};
        for(int num : array){
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.print("Enter the number to get the Kth largest : ");

        Scanner sc = new Scanner(System.in);

        int k = sc.nextInt();

        Arrays.sort(array, Collections.reverseOrder());
        System.out.println("The Kth largest element is " + array[k-1]);


    }
}
