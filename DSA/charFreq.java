package DSA;

import java.util.HashMap;

public class charFreq {

    public static HashMap<Character,Integer> frequency(String str){
        HashMap<Character, Integer> freq = new HashMap<>();



        for(int i=0; i<str.length(); i++){
            char currentChar = str.charAt(i);
            if(currentChar == ' '){
                continue;
            }
            freq.put(currentChar, freq.getOrDefault(currentChar,0)+1);
        }
        return freq;
    }
}
