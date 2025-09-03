package DSA;

import java.util.Scanner;

public class binarySearch {

    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9, 11, 23, 44, 56, 65, 76, 87, 98};

        int first = 0, last = array.length - 1;
        int mid;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the key to find: ");
        int key = sc.nextInt();
        boolean flag = false;
        int position = -1;

        while (first <= last) {  // ✅ Correct: loop continues while search space is valid
            mid = (first + last) / 2;

            if (key == array[mid]) {
                flag = true;
                position = mid;   // ✅ Save the position where key is found
                break;
            }
            else if (array[mid] > key) {
                last = mid - 1;   // ✅ Correct: eliminate upper half
            }
            else {
                first = mid + 1;  // ✅ Correct: eliminate lower half
            }
        }

        if (flag) {
            System.out.println("The key is present at index: " + position);
        } else {
            System.out.println("The key is not present");
        }
    }
}
