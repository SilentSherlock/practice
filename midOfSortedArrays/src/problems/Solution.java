package problems;

public class Solution {

    public double solute(int[] nums1, int[] nums2){
        int length1 = nums1.length;
        int length2 = nums2.length;
        int leftIndex = (length1 + length2 + 1)/2;//中位数左边数的的下标,如果长度的奇数即为中位数坐标
        int rightIndex = (length1 + length2 + 2)/2;//中位数右边数的下标
        //数组长度之和为奇时返回两个一样的中位数，为偶时返回中位数两边的数并求平均数
        return (getKth(nums1, 0, length1-1, nums2, 0, length2-1, leftIndex) +
                getKth(nums1, 0, length1-1, nums2, 0, length2-1, rightIndex)) / 2;
    }
    //参数K代表要查找的第K个数
    private double getKth(int [] array1, int begin1, int end1, int[] array2,
                                 int begin2, int end2, int K){
        int length1 = end1 - begin1 + 1;
        int length2 = end2 - begin2 + 1;
        if (length1 > length2){//使得array1始终为最短的那个
            return getKth(array2, begin2, end2, array1, begin1, end1, K);
        }
        if (length1 == 0){//array1长度为0时,直接返回array2中第K小的数
            return array2[begin2 + K -1];
        }
        if (K == 1){//正常相减情况下的临界条件,返回两个数组中第一小的数
            return Math.min(array1[begin1], array2[begin2]);
        }
        //普通情况，比较array1[x]和array2[y]大小来确定舍弃哪个数组的前K/2
        int x = begin1 + Math.min(length1, K/2) - 1;
        int y = begin2 + Math.min(length2, K/2) - 1;
        if (array1[x] > array2[y]){
            return getKth(array1, begin1, end1, array2, y+1, end2, K - (y+1-begin2));
        }else {
            return getKth(array1, x+1, end1, array2, begin2, end2, K - (x+1-begin1));
        }

    }
}
