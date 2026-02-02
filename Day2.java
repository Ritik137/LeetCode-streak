import java.util.*;

// LeetCode 3013: Divide an Array Into Subarrays With Minimum Cost II
public class Day2 {

    static class Solution {

        /*
         small  -> k-2 smallest elements (cost contributors)
         large  -> remaining elements in window
         sumSmall -> sum of elements present in small
        */
        private TreeMap<Integer, Integer> small = new TreeMap<>();
        private TreeMap<Integer, Integer> large = new TreeMap<>();
        private long sumSmall = 0;

        // need = k-2 (how many smallest elements we must pick)
        private int need;

        // sizes (duplicates included)
        private int smallSize;
        private int largeSize;

        // Add element x into a multiset (TreeMap with frequency)
        private void addMap(TreeMap<Integer, Integer> map, int x) {
            map.put(x, map.getOrDefault(x, 0) + 1);
        }

        // Remove one occurrence of x from multiset
        private void removeMap(TreeMap<Integer, Integer> map, int x) {
            int c = map.get(x);
            if (c == 1) map.remove(x);
            else map.put(x, c - 1);
        }

        // Move the largest element of 'small' to 'large'
        private void moveSmallToLarge() {
            int x = small.lastKey();          // biggest in small
            removeMap(small, x);
            smallSize--;
            sumSmall -= x;

            addMap(large, x);
            largeSize++;
        }

        // Move the smallest element of 'large' to 'small'
        private void moveLargeToSmall() {
            int x = large.firstKey();         // smallest in large
            removeMap(large, x);
            largeSize--;

            addMap(small, x);
            smallSize++;
            sumSmall += x;
        }

        /*
         Rebalance condition:
         - small must contain exactly 'need' elements
         - if small > need -> move to large
         - if small < need -> pull from large
        */
        private void rebalance() {
            while (smallSize > need) moveSmallToLarge();
            while (smallSize < need && largeSize > 0) moveLargeToSmall();
        }

        // Add a value to window
        private void addVal(int x) {
            if (smallSize < need) {
                // still space in small
                addMap(small, x);
                smallSize++;
                sumSmall += x;
            } else {
                // compare with largest element of small
                int maxSmall = small.lastKey();
                if (x < maxSmall) {
                    // x is better candidate for small
                    removeMap(small, maxSmall);
                    sumSmall -= maxSmall;
                    addMap(large, maxSmall);
                    largeSize++;

                    addMap(small, x);
                    sumSmall += x;
                } else {
                    // x goes to large
                    addMap(large, x);
                    largeSize++;
                }
            }
            rebalance();
        }

        // Remove a value from window
        private void removeVal(int x) {
            if (small.getOrDefault(x, 0) > 0) {
                // x exists in small
                removeMap(small, x);
                smallSize--;
                sumSmall -= x;
            } else {
                // x must be in large
                removeMap(large, x);
                largeSize--;
            }
            rebalance();
        }

        /*
         Main logic:
         - nums[0] is always included
         - i1 = start of 2nd subarray
         - from window [i1+1 ... i1+dist], pick k-2 smallest
        */
        public long minimumCost(int[] nums, int k, int dist) {
            int n = nums.length;

            // Case 1: only one subarray
            if (k == 1) return nums[0];

            // Case 2: need only one extra start
            if (k == 2) {
                int mn = Integer.MAX_VALUE;
                for (int i = 1; i < n; i++) mn = Math.min(mn, nums[i]);
                return (long) nums[0] + mn;
            }

            // For k >= 3
            need = k - 2;

            // Maximum valid start index for second subarray
            int maxI1 = n - k + 1;
            long ans = Long.MAX_VALUE;

            // Reset data structures
            small.clear();
            large.clear();
            sumSmall = 0;
            smallSize = 0;
            largeSize = 0;

            // Initial window for i1 = 1
            int i1 = 1;
            int L = i1 + 1;
            int R = Math.min(n - 1, i1 + dist);

            // Fill initial window
            for (int idx = L; idx <= R; idx++) addVal(nums[idx]);

            // Slide i1
            for (i1 = 1; i1 <= maxI1; i1++) {
                L = i1 + 1;
                R = Math.min(n - 1, i1 + dist);
                int windowSize = R - L + 1;

                // If enough elements exist, calculate cost
                if (windowSize >= need && smallSize == need) {
                    long cost = (long) nums[0] + nums[i1] + sumSmall;
                    ans = Math.min(ans, cost);
                }

                // Slide window forward
                int outIdx = i1 + 1;        // element leaving window
                int inIdx = i1 + dist + 1; // element entering window

                if (outIdx <= n - 1) removeVal(nums[outIdx]);
                if (inIdx <= n - 1) addVal(nums[inIdx]);
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] nums = {1, 3, 2, 6, 4};
        int k = 3;
        int dist = 3;

        long ans = sol.minimumCost(nums, k, dist);
        System.out.println(ans);
    }
}
