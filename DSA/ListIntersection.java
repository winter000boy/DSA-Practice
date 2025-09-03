package DSA;

import java.util.ArrayList;

public class ListIntersection {

    public static ArrayList<Integer> intersection(int [] arr1, int[] arr2){

        ArrayList<Integer> list = new ArrayList<>();
        for(int x : arr1){
            for(int y : arr2){
                if (x == y){
                    list.add(x);
                }
            }
        }
        return list;
    }
}
