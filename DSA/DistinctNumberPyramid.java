package DSA;

public class DistinctNumberPyramid {

    public static void pattern(int depth){

        for(int i=1; i<=depth; i++){
            int count = 1;
            System.out.println();
            for(int j=1; j<=i; j++){
                System.out.print(count + " ");
                count++;
            }
        }
    }
}
