package test;

import problem.Solution;
import tools.ListNode;
import tools.TreeNode;

import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Date:2020/8/23
 * Description: optional class description
 **/
public class MyTest {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {3, 14, 1, 5, 1, 6, 9, 7, 789, 679, 678};
        int[] nums2 = {1, 0, 0, 1, 0, 1, 0, 1, 0, 1};
        int[] nums3 = {1, 1, 1, 1, 1};
        int[] nums4 = {0,1,2};
        int[] nums5 = {-1,-1,-1,-1, 0, 3};
        int[] nums6 = {2,3,1,3,2,4,6,7,9,2,19};
        int[] nums7 = {2,1,4,3,9,6};
        int[] threeNums = {-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0
};
        //list
        ListNode ptr1 = new ListNode(1);
        ListNode ptr2 = new ListNode(2);
        ListNode ptr3 = new ListNode(3);
        ListNode ptr4 = new ListNode(4);
        ListNode ptr5 = new ListNode(5);
        ListNode ptr6 = new ListNode(6);
        ListNode ptr7 = new ListNode(7);
        ListNode ptr8 = new ListNode(8);
        ListNode ptr9 = new ListNode(9);
        ListNode ptr0 = new ListNode(0);
        ptr1.next = ptr2;
        ptr2.next = ptr3;
        ptr3.next = ptr4;
        ptr4.next = ptr5;
        ptr5.next = ptr6;
        ptr6.next = ptr7;
        ptr7.next = ptr8;
        ptr8.next = ptr9;
        ptr9.next = ptr0;
        //ptr0.next = ptr1;
        //Tree
        TreeNode node1 = new TreeNode(-2147483648);
        TreeNode node2 = new TreeNode(-2147483648);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);
        node1.left = node2;
        /*node1.right = node3;
        node2.left = node4;
        node4.left = node5;
        node3.left = node6;
        node3.right = node7;
        node7.left = node8;*/
        char[][] grid = {{1,1,0,1,1,1,1,1,1,1,1,1,1,0,0,1,0,1,1,1,0,1,1,1,1,1,0,1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,0,1,0,0,1,1,0,0,1,1,1,1,0,1,0,0,1,1,1,0,0,1,1},{1,1,0,1,1,1,1,1,0,1,0,0,0,1,1,0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,0,0,1,0,1,0,0,1,0,1,0,1,0,1,1,0,1,1,1,1,0,1,0,0,0,0,1,1,1,1,0,0,0,1,1},{1,1,1,1,1,1,1,0,0,1,1,1,0,1,1,1,1,0,1,1,1,0,1,0,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1},{1,0,1,1,0,0,1,1,0,1,1,1,0,1,1,0,1,1,0,1,1,0,0,0,1,1,0,0,0,1,1,0,0,1,1,1,1,1,1,1,1,0,1,1,1,0,1,0,0,0,1,1,1,0,0,1,0,1,0,1,0,1,1,1,1},{0,1,0,1,0,1,1,1,1,0,1,0,1,1,1,0,1,1,1,1,1,0,0,1,0,1,1,0,1,0,1,1,1,0,0,1,1,1,1,0,1,0,1,1,1,1,0,1,1,1,1,1,0,0,0,0,1,0,0,1,0,1,1,1,1},{1,1,1,1,1,1,1,1,0,1,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,0,1,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,0,1,1,0,1},{1,0,1,0,1,1,0,1,1,1,1,0,0,0,1,1,0,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,0,1,0,1,1,0,1,0,1,1,1,1,0,0,0,0,1,0,0,1,1,1,0,0,1,0,0,0,1,1,1,0},{1,1,1,1,0,1,1,1,1,1,1,1,1,0,0,0,1,0,0,0,1,1,1,1,1,0,0,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,1,0,0,1,0,1,0,0,1,1,0,1,1,1,0,1,1,1,1,1,1,1,0},{1,0,0,1,1,0,1,1,1,1,1,1,1,1,1,0,0,1,0,1,1,1,0,0,0,1,1,1,1,0,1,0,1,0,1,0,0,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1},{1,1,1,0,1,1,0,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,0,1,0,1,1,0,1,1,1,1,1,1,0,0,0,1,0,1,1,1,1,1,1},{0,1,1,0,1,1,1,1,1,1,1,0,1,0,0,0,1,1,1,1,0,1,1,1,0,0,1,1,1,1,0,1,0,1,1,0,1,1,0,0,0,0,1,1,1,1,1,0,1,0,1,0,1,0,1,1,0,1,0,0,1,0,1,1,1},{1,1,0,0,1,1,1,1,1,1,0,1,1,0,0,1,1,0,1,0,0,0,1,0,1,1,0,1,1,1,0,1,1,0,0,1,0,1,0,1,1,1,1,1,1,0,1,1,0,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,0,0,1,0,0,1,1,1,1,1,0,1,1,0,0,1,0,0,1,0,1,1,1,1,1,0,1,0,1,1,1,0,1,1,1,0,0,1,1,0,1,0,0,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0},{1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,0,0,1,1,1,1,0,1,0,1,0,1,1,1,1,1,0,1,1,1,0,1,1,0,0,1,1,1,0,1,0,0,1},{1,0,1,0,0,1,0,0,1,1,1,1,0,1,1,1,0,1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,1,0,0,0,0,1,0,1,0,1,1,1,0,0,0,1,1,1,0,1,1,1,1,1,0,1,0,1,1,0,1,1,1}};

        /*if (result != null) {
            for (ArrayList<Integer> cur : result) {
                for (Integer integer : cur) {
                    System.out.print(integer + " ");
                }
                System.out.println();
            }
            while (result != null) {
                System.out.print(result.val + " ");
                result = result.next;
            }
        }*/
        char[] chars = {'A', 'A', 'A', 'B', 'B', 'B'};
        test.Solution solution1 = new test.Solution();
        int[][] friend = {
                {1, 1, 0},
                {1, 1, 0},
                {0, 0, 1}
        };
        String[] name = {"John(15)","Jon(12)","Chris(13)","Kris(4)","Christopher(19)"};
        String[] cp = {"(Jon,John)","(John,Johnny)","(Chris,Kris)","(Chris,Christopher)"};
        List<Integer> result = solution.splitIntoFibonacci("0123");

        System.out.println(result);
        StringBuffer
        /*char c = '3';
        int i = c;
        System.out.println(i);*/
        /*ArrayList<Integer> list = new ArrayList<>();
        list.add(8);
        list.add(9);
        Collections.sort(list);
        System.out.println(Collections.binarySearch(list, 1));*/
        /*for (List<Integer> list : result) {
            System.out.println(list);
        }*/
    }

}
