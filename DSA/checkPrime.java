package DSA;

public class checkPrime {

    public static boolean checkPrimeNumber(int num){

        if(num == 2){
            return true;
        }
        for(int i=2; i<num/2; i++){
            if(num%i == 0){
                return false;
            }
        }
        return true;
    }
}
