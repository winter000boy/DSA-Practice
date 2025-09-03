package DSA;

import java.util.ArrayList;

public class OddEvenSequence {

    public static void printSequence(int n){

        ArrayList<Integer> oddList = new ArrayList<>();
        ArrayList<Integer> evenList = new ArrayList<>();
        for(int i=0; i<n+1; i++){
            if(i%2 ==0){
                evenList.add(i);
            }
            else{
                oddList.add(i);
            }
        }

        System.out.println(oddList +" \n"+ evenList);
    }
}
