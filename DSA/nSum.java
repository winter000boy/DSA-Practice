package DSA;

import java.util.Scanner;

public class nSum {

    public int getSum(int n){
        int sum =0;
        if(n ==0 || n==1){
            return 1;
        }
        if(n>1){
            for(int i=1; i<=n; i++){
                sum += i;
            }
        }
        return sum;
    }

    public static int getSimpleSum(int n){
        return n*(n+1)/2;
    }

}
