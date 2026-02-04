
class Day4 {

    public long maxSumTrionic(int[] nums) {
        int n = nums.length;
        if (n < 4) {
            return 0;
        }

        final long NEG = Long.MIN_VALUE / 4;

        long inc1 = nums[0];   // increasing length >=1
        long inc2 = NEG;       // increasing length >=2
        long dec2 = NEG;       // inc(len>=2) -> dec
        long tri = NEG;       // inc -> dec -> inc

        long ans = NEG;

        for (int i = 1; i < n; i++) {
            long newInc1 = nums[i];
            long newInc2 = NEG;
            long newDec2 = NEG;
            long newTri = NEG;

            if (nums[i - 1] < nums[i]) {
                // increasing
                newInc1 = inc1 + nums[i];

                newInc2 = Math.max(
                        (inc2 == NEG ? NEG : inc2 + nums[i]),
                        inc1 + nums[i]
                );

                newTri = Math.max(
                        (tri == NEG ? NEG : tri + nums[i]),
                        (dec2 == NEG ? NEG : dec2 + nums[i])
                );

            } else if (nums[i - 1] > nums[i]) {
                // decreasing
                newInc1 = nums[i];

                newDec2 = Math.max(
                        (dec2 == NEG ? NEG : dec2 + nums[i]),
                        (inc2 == NEG ? NEG : inc2 + nums[i])
                );
            } else {
                // equal breaks strictness
                newInc1 = nums[i];
            }

            inc1 = newInc1;
            inc2 = newInc2;
            dec2 = newDec2;
            tri = newTri;

            ans = Math.max(ans, tri);
        }

        return ans == NEG ? 0 : ans;
    }

    public static void main(String[] args) {
        Day4 sol = new Day4();

        int[] nums1 = {1, 3, 5, 4, 2, 6, 8};
        int[] nums2 = {2, 1, 4, 7, 3, 2, 5};

        System.out.println("Output 1: " + sol.maxSumTrionic(nums1));
        System.out.println("Output 2: " + sol.maxSumTrionic(nums2));
    }
}
