package DSA;

public class palindrome {

    public static boolean Palindrome(String str){
        String reverse = new StringBuilder(str).reverse().toString();
        return reverse.equals(str);
    }
}
