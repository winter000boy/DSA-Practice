// Problem Statement
// You wish to help Ashish, who possesses a collection of N strings, some of which may be duplicated, and has been assigned the task of finding the kth unique string.

// If the number of unique strings is less than k, he needs to display an empty string. Considering you are Ashish's best friend can you assist him with this challenge?

import java.util.*;

public class KthUniqueString {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the number of strings
        int N = scanner.nextInt();
        scanner.nextLine(); // consume the newline

        // Read the strings
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            strings.add(scanner.nextLine());
        }

        // Read the value of k
        int k = scanner.nextInt();
        
        // Find the k-th unique string
        String kthUniqueString = findKthUniqueString(strings, k);

        // Output the result
        System.out.println(kthUniqueString);
    }

    public static String findKthUniqueString(List<String> strings, int k) {
        Map<String, Integer> frequencyMap = new LinkedHashMap<>();

        // Count the frequency of each string
        for (String str : strings) {
            frequencyMap.put(str, frequencyMap.getOrDefault(str, 0) + 1);
        }

        // Collect the unique strings
        List<String> uniqueStrings = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == 1) {
                uniqueStrings.add(entry.getKey());
            }
        }

        // Check if the k-th unique string exists
        if (uniqueStrings.size() < k) {
            return ""; // less than k unique strings
        } else {
            return uniqueStrings.get(k - 1); // return the k-th unique string (1-based index)
        }
    }
}
