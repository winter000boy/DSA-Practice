import java.util.HashMap;

public class two_sum {
    public static void main(String[] args) {
        // Example input
        int[] nums = { 2, 7, 11, 15 };
        int target = 9;

        // Create an instance of the two_sum class to call the twoSum method
        two_sum ts = new two_sum();
        int[] result = ts.twoSum(nums, target);

        // Print the result
        System.out.println("Indices of the numbers that add up to the target:");
        System.out.println("Index 1: " + result[0]);
        System.out.println("Index 2: " + result[1]);
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] ans = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                ans[0] = map.get(target - nums[i]);
                ans[1] = i;
                return ans; // Return as soon as the solution is found
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution"); // Handle cases where no solution is found
    }
}
