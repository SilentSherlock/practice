package Test;

import Solution.Solution;

/**
 * Date:2020.5.18
 * Description: optional class description
 **/
public class Test {
    public static void main(String[] args) {
        int[] nums = {-1,-2,-9,-6};
        int[] result = new Solution().maxProduct(nums);
        for (int n:result) {
            System.out.print(" " + n);
        }
    }
}
