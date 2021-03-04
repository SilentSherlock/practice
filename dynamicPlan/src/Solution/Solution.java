package Solution;

/**
 * Date:2020.5.18
 * Description: leetcode problem 53 and 152, use dynamic plan
 **/
public class Solution {

    public int maxSubArray(int[] nums) {
        int[] sums = new int[nums.length];
        for (int i = 0;i < nums.length;i++) {
            if (i == 0) sums[i] = nums[i];
            if (i > 0) sums[i] = Math.max(sums[i - 1] + nums[i], nums[i]);
        }
        int maxSum = sums[0];
        for (int sum : sums) {
            if (sum > maxSum) maxSum = sum;
        }
        return maxSum;
    }

    public int maxProduct(int[] nums) {
        int[] maxPositive = new int[nums.length];
        int[] minNegative = new int[nums.length];
        for (int i = 0;i < nums.length;i++) {
            if (nums[i] >= 0) {
                if (i == 0) {
                    maxPositive[i] = minNegative[i]= nums[i];
                }
                else {
                    maxPositive[i] = Math.max(maxPositive[i-1] * nums[i], nums[i]);
                    minNegative[i] = minNegative[i-1] * nums[i];
                }
            }else{
                if (i == 0) {
                    minNegative[i] = maxPositive[i] = nums[i];
                }
                else {
                    maxPositive[i] = minNegative[i-1] * nums[i];
                    minNegative[i] = Math.min(maxPositive[i-1] * nums[i], nums[i]);
                }
            }
        }
        int maxProduct = maxPositive[0];
        for (int n : maxPositive){
            if (maxProduct < n) maxProduct = n;
        }
        return maxProduct;
    }
}
