// LeetCode 3637: Trionic Array I

import java.util.*;

public class Day3 {

    // Solution class inside Day3
    static class Solution {
        public boolean isTrionic(int[] nums) {
            int n = nums.length;
            if (n < 4) return false;

            int i = 1;

            // 1) strictly increasing
            while (i < n && nums[i] > nums[i - 1]) i++;
            if (i == 1 || i == n) return false;

            // 2) strictly decreasing
            if (nums[i] >= nums[i - 1]) return false;
            while (i < n && nums[i] < nums[i - 1]) i++;
            if (i == n) return false;

            // 3) strictly increasing
            if (nums[i] <= nums[i - 1]) return false;
            while (i < n && nums[i] > nums[i - 1]) i++;

            return i == n;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] nums1 = {1, 3, 5, 4, 2, 6, 8};
        int[] nums2 = {1, 2, 3, 4};
        int[] nums3 = {1, 4, 6, 3, 2, 5};

        System.out.println(sol.isTrionic(nums1)); // true
        System.out.println(sol.isTrionic(nums2)); // false
        System.out.println(sol.isTrionic(nums3)); // true
    }
}
