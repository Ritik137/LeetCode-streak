// LeetCode 3010: Divide an Array Into Subarrays With Minimum Cost I

public class Day1 {

    public static int minimumCost(int[] nums) {
        int firstMin = Integer.MAX_VALUE;
        int secondMin = Integer.MAX_VALUE;

        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];

            if (x < firstMin) {
                secondMin = firstMin;
                firstMin = x;
            } else if (x < secondMin) {
                secondMin = x;
            }
        }

        return nums[0] + firstMin + secondMin;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 12};
        System.out.println(minimumCost(nums)); // Output: 6
    }
}
