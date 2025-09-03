package DSA;

public class ReverseDistinctNumberPyramid {

    public static void pattern(int depth){

        for(int i=depth; i>=1; i--){
            System.out.println();
            int count = depth;
            for(int j=i; j>=1; j--){
                System.out.print(count + " ");
                count--;
            }
        }
    }
}
