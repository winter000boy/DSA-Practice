package DSA;

import java.util.Scanner;

public class NumberStarPattern {
    public static void main(String[] args) {

        int count =1;
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int loopRun=0;

        if(n%5 ==0){
            loopRun = n/5;
        }else{
            loopRun = n/5 +1;
        }


        for (int i=0; i<loopRun+1; i++){
            if(i%2 ==0){
                for (int j=0; j<5; j++){
                    System.out.print(count + " ");
                    count++;
                }
            }
            else {
                for (int j=0; j<5; j++){
                    System.out.print("*" + " ");
                    count++;
                }
            }
            if (count==n+1){
                break;
            }
        }
    }
}
