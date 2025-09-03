package DSA;

public class StarWithNumbers {

    public static void printStarSequence(int num){
        int i=0;
        int count =0;

        for(int j=0; j<num;j++){
            if(j%2 != 0){
                while(i<num){
                    System.out.println(i + " ");
                    i++;
                    j++;
                }
            }
            else{
                System.out.print(" * ");
            }

        }


    }
}
