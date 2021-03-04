package problems;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Date:2020.5.24
 * Description: leetcode problem 6 28 287 142 141 974 394 35 198 84 101 11 15 1343 837 238 128
 * 740 1014 139 41 378 63
 **/
public class Problem {

    //6,字符转换
    public String convert(String s, int numRows) {
        if (numRows == 0) return "";
        int length = s.length();
        if (numRows == 1 || length < numRows) return s;

        List<List<String>> strs = new LinkedList<>();
        for (int i = 0; i < numRows; i++) strs.add(new LinkedList<>());
        int index = 0;
        boolean scanFlag = true;//扫描方向，向右为true，向左为false;
        for (int i = 0; i < length; i++) {
            if (index == numRows - 1) scanFlag = false;
            else if (index == 0) scanFlag = true;
            strs.get(index).add(String.valueOf(s.charAt(i)));
            if (scanFlag) index++;
            else index--;
        }
        StringBuilder result = new StringBuilder();
        for (List<String> str : strs) {
            for (String tmp : str) {
                result.append(tmp);
            }
        }
        return result.toString();
    }

    //28,实现strStr,即在一个字符串中找到另一个字符串第一次出现的位置
    //暴力法,会超时
    public int strStr(String haystack, String needle) {
        int hlength = haystack.length();
        int nlength = needle.length();
        if (nlength == 0) return 0;
        if (hlength < nlength) return -1;

        int i = 0;
        while (i < hlength) {
            for (int j = i;j >= 0;j--) {
                if (haystack.substring(j, i+1).equals(needle)) return j;
            }
            i++;
        }
        return -1;
    }

    //双指针,方法实现参照的是String.indexOf(String)方法,通过
    public int strStr2(String haystack, String needle) {
        int hlength = haystack.length();
        int nlength = needle.length();
        if (nlength == 0) return 0;
        if (hlength < nlength) return -1;

        for (int i = 0; i < hlength; i++) {
            //在haystack中找到needle第一个字符出现的位置
            while (i < hlength && haystack.charAt(i) != needle.charAt(0)) i++;
            if (i < hlength) {//找到第一个字符后比对之后的字符
                for (int j = i;j < hlength && haystack.charAt(j) == needle.charAt(j-i);) {
                    j++;
                    if (j-i == nlength) return i;
                }
            }
        }
        return -1;
    }

    //一种更简单的滑动窗口
    public int strStr3(String haystack, String needle) {
        int L = needle.length(), n = haystack.length();
        for (int start = 0; start < n - L + 1; ++start) {
            if (haystack.substring(start, start + L).equals(needle)) {
                return start;
            }
        }
        return -1;
    }

    //287
    //找到数组中重复的数字
    //将数组虚拟为链表，通过快慢指针完成
    int findDuplicate(int[] nums) {
        int fast = 0, slow = 0;
        do {
            fast = nums[nums[fast]];
            slow = nums[slow];
        } while (fast != slow);
        int find = 0;
        while (find != slow) {
            find = nums[find];
            slow = nums[slow];
        }
        return slow;
    }

    //142
    //查看链表是否有环，与287解法类似
    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode fast = head, slow = head;
        while (true) {
            if (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
                if (fast == slow) break;
            }else return null;
        }
        slow = head;
        while(slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    //141
    //判断链表是否有环，更直接一点
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode fast = head, slow = head;
        while (true) {
            if (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
                if (fast == slow) return true;
            }else return false;
        }
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode (int x) {
            val = x;
        }
    }
    //974
    //找到和能被K整除的子数组个数
    //暴力法超时
    public int subarraysDivByK(int[] A, int K) {
        int length = A.length;
        if (length == 0 || length == 1 && A[0] % K != 0) return 0;
        int count = 0;
        for (int i = 0;i < length;i++) {
            int sum = 0;
            for (int j = i;j >= 0;j--) {
                sum += A[j];
                if (sum % K == 0) count++;
            }
        }
        return count;
    }

    //前缀和方法，前缀和模K同余的相减肯定能被K整除，然后再两两结合，计算同余的有多少种组合
    public int subarrayDivByK2(int[] A, int K) {
        int length = A.length;
        if (length == 0 || length == 1 && A[0] % K != 0) return 0;

        HashMap<Integer, Integer> record = new HashMap<>();
        record.put(0, 1);
        int sum = 0;
        for (int item : A) {
            sum += item;
            int key = (sum % K + K) % K;//注意java中符号不同时，取模和取余的差别，有函数floormod
            Integer value = record.get(key);
            if (value != null) record.put(key, ++value);
            else record.put(key, 1);
        }
        int count = 0;
        for (int key : record.keySet()) {
            int value = record.get(key);
            count += value * (value - 1) / 2;
        }

        return count;
    }

    //394
    //将编码字符串解码，递归写法
    public String decodeString(String s) {
        int length = s.length();
        if (length < 4) return s;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i < length;) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                stringBuilder.append(c);
                i++;
            }else {//找到第一个数字
                int count = 0;
                while (c != '[') {
                    count = count * 10 + (c - '0');
                    i++;
                    c = s.charAt(i);
                }
                int flag = 1;
                i = i + 1;//指向[后的第一个字符
                int j = i;
                while (flag != 0) {
                    c = s.charAt(i);
                    switch (c) {
                        case '[':
                            flag++;
                            break;
                        case ']':
                            flag--;
                            break;
                        default:
                            break;
                    }
                    i++;
                }
                String sub = decodeString(s.substring(j, i - 1));
                while (count > 0) {
                    stringBuilder.append(sub);
                    count--;
                }
            }
        }
        return stringBuilder.toString();
    }

    //35
    //有序数组中查找数字，找到返回下标，找不到返回索引
    public int searchInsert(int[] nums, int target) {

        int length = nums.length;
        if (length == 0) return 0;
        if (target < nums[0]) return 0;
        else if (target > nums[length-1]) return length;

        int begin = 0, end = length - 1, mid = 0;
        while (begin <= end) {
            mid = (begin + end) / 2;
            System.out.println(mid);
            if (nums[mid] < target) begin = mid + 1;
            else if (nums[mid] > target) end = mid - 1;
            else return mid;
        }
        if (nums[mid] < target) return mid + 1;
        return mid;
    }

    //198
    //偷窃，找出数组中不相邻且和最大，返回最大和，动态规划解法
    public int rob(int[] nums) {
        int length = nums.length;
        if (length == 0) return 0;
        if (length == 1) return nums[0];
        if (length == 2) return Math.max(nums[0], nums[1]);

        int[] sums = new int[length];
        for (int i = 0;i < length;i++) {
            if (i == 0) sums[i] = nums[i];
            else if (i == 1) sums[i] = Math.max(sums[0], nums[i]);
            else sums[i] = Math.max(sums[i-2] + nums[i], sums[i-1]);
        }

        return sums[length -1];
    }

    //84
    //找出柱状图中最大矩形
    //暴力法，超时，草
    public int largestRectangleArea(int[] heights) {
        int length = heights.length;
        if (length == 0) return 0;
        if (length == 1) return heights[0];

        List<List<Integer>> diagrams = new LinkedList<>();
        for (int i = 0;i < length;i++) {
            List<Integer> list = new LinkedList<>();
            while (i < length && heights[i] != 0) {
                list.add(heights[i]);
                i++;
            }
            if (!list.isEmpty()) diagrams.add(list);
        }
        int result = 0;
        for (List list : diagrams) {
            for (int i = 0;i < list.size();i++) {
                int min = 0, sum;
                for (int j = i;j >= 0;j--) {
                    if (min == 0 || (int)list.get(j) < min) min = (int)list.get(j);
                    sum = min * (i - j + 1);
                    if (sum > result) result = sum;
                }
            }
        }

        return result;
    }

    //栈方法
    public int largestRectangleArea2(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];

        Stack<Integer> mono_stack = new Stack<Integer>();
        for (int i = 0; i < n; ++i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                mono_stack.pop();
            }
            left[i] = (mono_stack.isEmpty() ? -1 : mono_stack.peek());
            mono_stack.push(i);
        }

        mono_stack.clear();
        for (int i = n - 1; i >= 0; --i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                mono_stack.pop();
            }
            right[i] = (mono_stack.isEmpty() ? n : mono_stack.peek());
            mono_stack.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }

    //101
    //检测二叉树是否是对称二叉树，使用递归
    public boolean isSymmetric(TreeNode root) {
        if (root != null) return isSame(root.left, root.right);
        return true;
    }

    private boolean isSame(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) return true;
        if (node1 == null || node2 == null) return false;

        if (node1.val == node2.val) return isSame(node1.left, node2.right) &&
                isSame(node1.right, node2.left);

        return false;
    }

    //11
    //盛水最多的容器
    public int maxArea(int[] height) {
        int length = height.length;
        if (length == 1 || length == 0) return 0;

        int left = 0, right = length - 1;
        int result = (right - left) * Math.min(height[left], height[right]);
        while (left < right) {
            if (height[left] <= height[right]) {
                int i;
                for (i = left;i < right && height[i] <= height[left];i++);
                left = i;
            }else {
                int i;
                for (i = right;i > left && height[i] <= height[right];i--);
                right = i;
            }
            int tmp = (right - left) * Math.min(height[left], height[right]);
            System.out.println("tmp is " + tmp + " left is " + left + " right is " + right);
            if (tmp > result) result = tmp;
        }
        return result;
    }


    //15
    //三数之和
    //使用了哈希表，但是在比对元素是否重复上耗时较多，导致超时
    public List<List<Integer>> threeSum(int[] nums) {
        int length = nums.length;

        List<List<Integer>> result = new LinkedList<>();
        if (length < 3) return result;
        HashMap<Integer, Integer> map = new HashMap<>();
        for(Integer num : nums) {
            Integer count = map.get(num);
            if (count == null) map.put(num, 1);
            else {
                count++;
                map.put(num, count);
            }
        }

        for (int i = 0;i < length - 1;i++) {
            for (int j = i + 1;j < length;j++) {
                int k = 0 - nums[i] - nums[j];
                List<Integer> tmpList = Arrays.asList(nums[i], nums[j], k);
                if ((k == nums[i] || k == nums[j]) && map.get(k) <= 2) continue;
                if (map.containsKey(k) && !isContain(result, tmpList)) result.add(tmpList);
            }
        }
        return result;
    }

    private boolean isContain(List<List<Integer>> list, List<Integer> target) {
        if (list.size() == 0) return false;
        HashMap targetMap = getMap(target);
        for (List<Integer> item : list) {
            HashMap map = getMap(item);
            if (targetMap.equals(map)) return true;
        }
        return false;
    }

    private HashMap getMap(List<Integer> list) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(Integer num : list) {
            Integer count = map.get(num);
            if (count == null) map.put(num, 1);
            else {
                count++;
                map.put(num, count);
            }
        }
        return map;
    }


    //解法二，先排序，再遍历搜索
    List<List<Integer>> threeSum2(int[] nums) {
        int length = nums.length;
        List<List<Integer>> result = new LinkedList<>();
        if (length < 3) return result;
        Arrays.sort(nums);

        for (int i = 0;i < length - 2;i++) {
            if (nums[i] > 0) break;
            if (i != 0 && nums[i] == nums[i-1]) continue;
            int left = i + 1, right = length - 1;
            while (left < right) {
                int tmp = nums[left] + nums[right];
                if (tmp == -nums[i]){
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                }else if (tmp > -nums[i]) right--;
                else left++;
            }
        }
        return result;
    }

    //面试64
    //没有条件语句的递归Fibonacci数列
    public int sumNums(int n) {
        boolean flag = n > 0 && (n += sumNums(n - 1)) > 0;
        return n;
    }

    //1343
    //大小为K且均值大于阈值
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int length = arr.length;
        if (length == 0 || length < k) return 0;

        int i = 0, sum = 0, count = 0;
        while (i < k) {
            sum += arr[i];
            i++;
        }
        for (;i < length;i++) {
            if (sum/k >= threshold) count++;
            System.out.println("sum is " + sum);
            sum = sum + arr[i] - arr[i-k];
        }
        if (sum/k >= threshold) count++;
        return count;
    }

    //面试16.04
    //检测井字游戏
    //直接扫描检查
    public String tictactoe(String[] board) {
        int row = board.length;
        if (row == 1) return board[0].equals(" ") ? "Draw" : board[0];
        int result1 = rowCheck(board);
        int result2 = colCheck(board);
        int result3 = diagonalCheck(board);

        if (result1 == -1 && result2 == -1 && result3 == -1) return "Draw";
        if (result1 == 0 && result2 == 0 && result3 == 0) return "Pending";
        if (result1 != 0 && result1 != -1) return result1 == 'X' ? "X" : "O";
        if (result2 != 0 && result2 != -1) return result2 == 'X' ? "X" : "O";
        return result3 == 'X' ? "X" : "O";
    }
    private int rowCheck(String[] strs) {
        boolean blank = false;
        for (String str : strs) {
            System.out.println("strlength is " + str.length());
            int j;
            for (j = 0; j < strs[0].length(); j++) {
                if (str.charAt(j) == ' ') {
                    blank = true;
                    break;
                } else if (j != 0 && str.charAt(j - 1) != str.charAt(j)) break;
            }
            if (j == strs[0].length()) return str.charAt(0);
        }
        return blank ? 0 : -1;
    }

    private int colCheck(String[] strs) {
        boolean blank = false;
        for (int i = 0;i < strs[0].length();i++) {
            int j;
            for (j = 0;j < strs.length;j++) {
                if (strs[j].charAt(i) == ' ') {
                    blank = true;
                    break;
                } else if (j != 0 && strs[j - 1].charAt(i) != strs[j].charAt(i)) break;
            }
            if (j == strs.length) return strs[j-1].charAt(i);
        }
        return blank ? 0 : -1;
    }

    private int diagonalCheck(String[] strs) {
        int begin = 0, end = strs.length - 1;
        boolean blank = false;
        while (begin < strs.length) {
            if (strs[begin].charAt(begin) == ' ') {
                blank = true;
                break;
            }else if (begin != 0 && strs[begin].charAt(begin) != strs[begin-1].charAt(begin-1))
                break;
            begin++;
        }
        if (begin == strs.length) return strs[0].charAt(0);
        begin = 0;
        while (end >= 0) {
            if (strs[begin].charAt(end) == ' ') return 0;
            else if (end != strs.length && begin != 0
                    && strs[begin].charAt(end) != strs[begin-1].charAt(end+1))
                return blank ? 0 : -1;
            begin++;
            end--;
        }
        return strs[0].charAt(strs.length-1);
    }

    //837
    //新21点，动态规划解法
    public double new21Game(int N, int K, int W) {
        if (K == 0) {
            return 1.0;
        }
        double[] dp = new double[K + W];
        for (int i = K; i <= N && i < K + W; i++) {
            dp[i] = 1.0;
        }
        dp[K - 1] = 1.0 * Math.min(N - K + 1, W) / W;
        for (int i = K - 2; i >= 0; i--) {
            dp[i] = dp[i + 1] - (dp[i + W + 1] - dp[i + 1]) / W;
        }
        return dp[0];
    }

    //238
    //除自己外数组乘积
    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int [] result = new int[nums.length];
        if (length == 0) return result;
        if (length == 1) return new int[]{0};

        int left = 0;
        for (int i = 0;i < length;i++) {
            if (i == 0) {
                result[i] = 0;
                left = 1;
            }else {
                left *= nums[i - 1];
                result[i] = left;
            }
        }

        int right = 0;
        for (int i = length-1;i >= 0;i--) {
            if (i == length-1) {
                right = 1;
            }else if (i == 0){
                right *= nums[i + 1];
                result[i] = right;
            }else {
                right *= nums[i + 1];
                result[i] *= right;
            }
        }

        return result;
    }

    //面试29，顺时针打印矩阵
    //直接扫描，层层打印法
    public int[] spiralOrder(int[][] matrix) {
        int matrixRow = matrix.length;
        if (matrixRow == 0) return new int[matrixRow];
        if (matrixRow == 1) return matrix[0];
        int matrixCol = matrix[0].length;
        if (matrixCol == 0) return new int[matrixCol];

        int half = (int) Math.ceil((double)Math.min(matrixRow, matrixCol)/2), begin = 0;
        //System.out.println(Math.min(matrixRow, matrixCol)/2);
        List<Integer> result = new LinkedList<>();
        //System.out.println("half is " + half);
        while (begin < half) {
            result.addAll(oneRound(matrix, matrixRow, matrixCol, begin, begin));
            begin++;
        }
        return result.stream().mapToInt(Integer::valueOf).toArray();
    }

    private List<Integer> oneRound(int[][] matrix, int row, int col, int beginX, int beginY) {
        List<Integer> result = new LinkedList<>();
        int endX = row - beginX - 1;
        int endY = col - beginY - 1;

        //System.out.println("beginx is " + beginX + "\n beginy is " + beginY + "\n endx is "
        //+ endX + "\n endy is " + endY);
        if (endX == beginX && endY == beginY) {
            result.add(matrix[beginX][beginY]);
            return result;
        }
        if (endX == beginX) {
            while (beginY <= endY) {
                result.add(matrix[beginX][beginY]);
                beginY++;
            }
            return result;
        }
        if (endY == beginY) {
            while (beginX <= endX) {
                result.add(matrix[beginX][beginY]);
                beginX++;
            }
            return result;
        }
        int x = beginX, y = beginY;
        while (y < endY) {
            result.add(matrix[beginX][y]);
            y++;
        }
        while (x < endX) {
            result.add(matrix[x][endY]);
            x++;
        }
        while (y > beginY) {
            result.add(matrix[endX][y]);
            y--;
        }
        while (x > beginX) {
            result.add(matrix[x][beginY]);
            x--;
        }

        return result;
    }

    //128
    //未排序数组中找到最长连续子序列，时间复杂度O(n)，使用并查集
    private HashMap<Integer, Integer> map = new HashMap<>();

    //找到数组中x所能到达的最远处
    private int find(int x) {
        int next = map.get(x);
        if (map.containsKey(next)) next = find(next);
        map.put(x, next);
        return next;
    }

    public int longestConsecutive(int[] nums) {
        for (int num : nums) map.put(num, num+1);
        int result = 0;
        for (int num : nums) {
            int tmp = find(num);
            System.out.println("tmp is " + tmp + "\n num is "+ num);
            result = Math.max(result, tmp - num);
        }
        return result;
    }

    //126
    //单词转换，没做出来操
    /*public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        HashMap<String, List<String>> map = new HashMap<>();

        queue.add(beginWord);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0;i < size;i++) {
                String front = queue.poll();
                if (front.equals(endWord))
            }
        }
    }*/

    //比较是否只相差一个字符
    private boolean compare(String str1, String str2) {
        int flag = 0;
        for (int i = 0;i < str1.length() && flag <= 1;i++) {
            if (str1.charAt(i) != str2.charAt(i)) flag++;
        }
        return flag == 1;
    }

    //990
    //等式方程的可满足性，利用并查集
    private HashMap<Character, Character> record = new HashMap<>();

    public boolean equationsPossible(String[] equations) {
        int length = equations.length;
        if (length == 0 || length == 1 && equations[0].charAt(1) == '=') return true;

        //将equations分为两组，一组==，一组!=
        List<String> equals = new LinkedList<>();
        List<String> unequals = new LinkedList<>();
        for (String equation : equations) {
            if (equation.charAt(1) == '=') equals.add(equation);
            else unequals.add(equation);
        }

        if (unequals.size() == 0) return true;

        //根据等式建立并查集
        for (String equal : equals) {
            char left = equal.charAt(0);
            char right = equal.charAt(3);
            if (record.containsKey(left) && record.containsKey(right)) {
                char leftNext = record.get(left);
                char rightNext = record.get(right);
                if (leftNext == rightNext) continue;
                if (record.containsKey(leftNext)) record.put(rightNext, find(leftNext));
                else record.put(leftNext, find(rightNext));
            }
            else if (record.containsKey(left)) record.put(right, find(left));
            else if (record.containsKey(right)) record.put(left, find(right));
            else {
                record.put(right, right);
                record.put(left, right);
            }
        }

        //建立完后，更新一遍并查集，合并
        record.replaceAll((k, v) -> find(k));
        //根据不等式检查是否成立
        for (String unequal : unequals) {
            char left = unequal.charAt(0);
            char right = unequal.charAt(3);
            if (left == right) return false;
            if (record.containsKey(left) && record.containsKey(right) &&
                    record.get(left) == record.get(right)) return false;
        }

        return true;
    }

    private char find(char c) {
        char fa = record.get(c);
        if (record.containsKey(fa) && fa != record.get(fa)) fa = find(fa);
        record.put(c, fa);

        return fa;
    }

    //官方更简洁的并查集
    public boolean equationsPossible2(String[] equations) {
        int length = equations.length;
        int[] parent = new int[26];
        for (int i = 0; i < 26; i++) {
            parent[i] = i;
        }
        for (String str : equations) {
            if (str.charAt(1) == '=') {
                int index1 = str.charAt(0) - 'a';
                int index2 = str.charAt(3) - 'a';
                union(parent, index1, index2);
            }
        }
        for (String str : equations) {
            if (str.charAt(1) == '!') {
                int index1 = str.charAt(0) - 'a';
                int index2 = str.charAt(3) - 'a';
                if (find(parent, index1) == find(parent, index2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void union(int[] parent, int index1, int index2) {
        parent[find(parent, index1)] = find(parent, index2);
    }

    private int find(int[] parent, int index) {
        while (parent[index] != index) {
            parent[index] = parent[parent[index]];
            index = parent[index];
        }
        return index;
    }

    //面试46
    //数字翻译字符串，动态规划
    public int translateNum(int num) {
        String src = String.valueOf(num);
        int p = 0, q = 0, r = 1;
        for (int i = 0; i < src.length(); ++i) {
            p = q;
            q = r;
            r = 0;
            r += q;
            if (i == 0) {
                continue;
            }
            String pre = src.substring(i - 1, i + 1);
            if (pre.compareTo("25") <= 0 && pre.compareTo("10") >= 0) {
                r += p;
            }
        }
        return r;
    }

    //96
    //不同的二叉搜索树，利用动态规划
    public int numTrees(int n) {
        int[] result = new int[n+1];
        result[0] = 1;
        result[1] = 1;

        for (int i = 2;i <= n;i++) {
            for (int j = 1;j <= i;j++) {
                result[i] += result[j-1]*result[i-j];//卡特兰数
            }
        }
        return result[n];
    }

    //直接使用卡特兰数公式，可查询卡特兰数定义
    public int numTrees2(int n) {
        long result = 0;
        for (int i = 0;i < n;i++) {
            result = result * 2 * (2*i+1) / (i+2);
        }
        return (int) result;
    }

    //739
    //每日温度，单调栈
    public int[] dailyTemperatures(int[] T) {
        int length = T.length;
        int[] result = new int[length];
        if (length <= 1) return result;

        Stack<Integer> indexs = new Stack<>();

        for (int i = 0;i < length;i++) {
            while (!indexs.empty() && T[i] > T[indexs.peek()]) {
                int tmp = indexs.pop();
                result[tmp] = i - tmp;
            }
            indexs.push(i);
        }
        return result;
    }

    //1300
    //转变数组接近目标值
    //遍历法
    public int findBestValue(int[] arr, int target) {
        int length = arr.length;
        int avg = target/length;
        Arrays.sort(arr);
        if (avg <= arr[0]) return Math.abs(target - avg * length) < Math.abs(target - (avg+1)*length) ? avg : avg+1;
        if (avg >= arr[length-1]) return arr[length-1];
        System.out.println("I am here");
        int leftSum = 0, sum = 0, result = 0;
        for (int i = 0;i < length;i++) {
            sum = leftSum + arr[i]*(length-i);
            if (sum >= target) {//找到前i个数之和加result乘后续个数最接近的那个
                System.out.println("sum is " + sum + " i is " + i);
                if (length - i - 1 == 0) return arr[length-1];
                result = (target-leftSum)/(length-i);
                System.out.println("result is " + result);
                return Math.abs(result*(length-i)+leftSum - target) <= Math.abs((result+1)*(length-i)+leftSum - target) ? result : result + 1;
            }
            leftSum += arr[i];
        }
        return arr[length-1];
    }

    //二分查找
    public int findBestValue2(int[] arr, int target) {
        int max = arr[0];
        for(int i = 1; i< arr.length; i++) {
            max = Math.max(arr[i], max);
        }
        int min = 0;
        while(min < max) {
            int mid = (min+max)/2;
            int sum = sum(arr, mid);
            if(sum < target) {
                min = mid + 1;
            } else {
                max = mid;
            }
        }
        int sum1 = sum(arr, min);
        int sum2 = sum(arr, min-1);
        if(sum1 - target < target-sum2)
            return min;
        return min-1;
    }

    private int sum(int[] arr, int num) {
        int sum = 0;
        for (int value : arr) {
            sum += Math.min(value, num);
        }
        return sum;
    }

    //740
    //删除获取点数，打家劫舍提升版
    public int deleteAndEarn(int[] nums) {
        int length = nums.length;
        if (length == 0) return 0;
        //if (length == 1) return nums[0];
        int[] record = new int[10001];
        for (int num : nums) record[num]++;
        int[] result = new int[10001];
        for (int i = 1;i < 10001;i++) {
            if (record[i] == 0) {
                result[i] = result[i-1];
                continue;
            }
            if (i == 1) result[i] = record[i] * i;
            else result[i] = Math.max(result[i-2] + record[i] * i, result[i-1]);
        }
        return result[10000];
    }

    //1014
    //观景得分
    //暴力法，超时
    public int maxScoreSightseeingPair(int[] A) {
        int length = A.length;
        if (length == 2) return getScore(A, 0, 1);

        int result = 0;
        for (int j = 1;j < length;j++) {
            for (int i = j - 1;i >= 0;i--) {
                int tmp = getScore(A, i, j);
                if (tmp > result) result = tmp;
            }
        }

        return result;
    }

    private int getScore(int[] A, int i, int j) {
        return A[i] + A[j] + i - j;
    }


    //拆分为A[i] + i + A[j] - j，同步更新两个最大值
    public int maxScoreSightseeingPair2(int[] A) {
        int length = A.length;
        if (length == 2) return A[0] + A[1] - 1;

        int result = 0, iMax = A[0];
        for (int j = 1;j < length;j++) {//同步更新两个最大值
            result = Math.max(result, A[j] - j + iMax);
            iMax = Math.max(iMax, A[j] + j);
        }
        return result;
    }

    //1028
    //从先序遍历构建二叉树
    public TreeNode recoverFromPreorder(String S) {
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();

        if (S.length() == 0) return null;
        int begin = 0;
        String rootStr = getNum(S, begin);
        TreeNode root = new TreeNode(Integer.parseInt(rootStr));
        begin += rootStr.length();

        nodeStack.push(root);
        depthStack.push(0);
        while (!nodeStack.empty()) {

            if (begin >= S.length()) return root;
            int depth = getDegree(S, begin);
            begin += depth;
            String nodeStr = getNum(S, begin);
            TreeNode node = new TreeNode(Integer.parseInt(nodeStr));
            begin += nodeStr.length();

            if (depth > depthStack.peek()) {
                nodeStack.peek().left = node;
                nodeStack.push(node);
                depthStack.push(depth);
            }else {
                while (depth <= depthStack.peek()) {
                    nodeStack.pop();
                    depthStack.pop();
                }
                nodeStack.peek().right = node;
                nodeStack.push(node);
                depthStack.push(depth);
            }
        }

        return root;
    }

    // 提取深度
    private int getDegree(String str, int beginIndex) {
        int depth = 0;
        for (int i = beginIndex;i < str.length();i++) {
            if (str.charAt(i) != '-') break;
            depth++;
        }
        return depth;
    }

    // 提取节点值
    private String getNum(String str, int beginIndex) {
        int i = beginIndex;
        for (;i < str.length();i++) {
            if (str.charAt(i) == '-') return str.substring(beginIndex, i);
        }
        if (i == str.length()) return str.substring(beginIndex, i);
        return "";
    }

    public boolean isMatch(String s, String p) {
        int sLength = s.length();
        int pLength = p.length();

        boolean[][] result = new boolean[sLength+1][pLength+1];
        result[0][0] = true;
        for (int i = 1;i < result.length;i++) result[i][0] = false;
        for (int i = 1;i <= sLength;i++) {
            for (int j = 1;j <= pLength;j++) {
                if (p.charAt(j - 1) == '*') {
                    result[i][j] = result[i][j - 2];
                    if (compared(s, p, i, j - 1))
                        result[i][j] = result[i][j] || result[i - 1][j];
                }else {
                    if (compared(s, p, i, j))
                        result[i][j] = result[i - 1][j - 1];
                }
            }
        }
        return result[sLength][pLength];
    }

    private boolean compared(String s, String p, int i, int j) {
        if (p.charAt(j - 1) == '.') return true;
        return s.charAt(i - 1) == p.charAt(j - 1);
    }

    //139
    //单词拆分，动态规划
    public boolean wordBreak(String s, List<String> wordDict) {
        int length = s.length();
        if (length == 0 || wordDict.size() == 0) return false;
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : wordDict) map.put(word, 1);

        boolean[] result = new boolean[length + 1];
        result[0] = true;
        for (int i = 1;i <= length;i++) {
            for (int j = 0;j < i;j++) {//判断前j个和j到i-1是否都在字典里
                if (result[j] && map.get(s.substring(j, i)) != null){
                    result[i] = true;
                    break;
                }
            }
        }

        return result[length];
    }

    //41
    //找到缺失的第一个整数
    public int firstMissingPositive(int[] nums) {

        int min = Integer.MAX_VALUE;
        int max = 0;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            if (num > 0 && num < min) min = num;
            if (num > max) max = num;
            map.put(num, 1);
        }

        if (min > 1) return 1;
        for (int i = 2;i < max;i++) {
            if (map.containsKey(i)) continue;
            return i;
        }
        return max + 1;
    }

    //378
    //二分法，找到有序二维数组中第K小的数
    public int kthSmallest(int[][] matrix, int k) {
        int length = matrix.length;
        int left = matrix[0][0], right = matrix[length - 1][length - 1];

        if (k == 1) return left;
        if (k == length*length) return right;

        while(left < right) {
            int mid = left + ((right - left) >> 1);//防止溢出
            if (calculate(matrix, length, mid) >= k) right = mid;
            else left = mid + 1;
        }

        return left;
    }

    // 计算根据mid分割出来小于等于mid和大于mid各有多少个
    // mid 根据矩阵中元素计算出来
    private int calculate(int[][] matrix, int n, int mid) {
        int i = n - 1;
        int j = 0;
        int count = 0;

        while (i >= 0 && j < n) {
            if (matrix[i][j] <= mid) {
                count += i + 1;//将一竖列加入count中
                j++;
                //System.out.println("current count is " + count);
            }else i--;
        }
        //System.out.println("result count is " + count);
        return count;
    }

    //最长合法括号
    public int longestValidParentheses(String s) {
        int length = s.length();
        if (length == 0 || length == 1) return 0;
        char[] braces = s.toCharArray();

        int[] record = new int[length];
        int result = 0;
        for (int i = 1;i < length;i++) {
            if (braces[i] == ')' && braces[i-1] == '(') record[i] = (i > 1 ? record[i-2] : 0 )+ 2;
            else if (braces[i] == ')' && braces[i-1] == ')'
                    && i - record[i-1] - 1 >= 0
                    && braces[i-record[i-1]-1] == '(')
                record[i] = record[i-1] + (i-record[i-1]-2 >= 0 ?
                        record[i-record[i-1]-2] : 0) + 2;//新的子串加上位置i-record[i-1]-2位置的子串长度
            result = Math.max(record[i], result);
        }

        return result;
    }


    //196场周赛
    //交换想领数字k次，得到最小整数
    public String minInteger(String num, int k) {
        int length = num.length();
        if (length == 1 || k == 0) return num;

        for (int i = 1;i < length;i++) {
            if (num.charAt(i) < num.charAt(i-1)) break;
            if (i == length - 1) return num;
        }

        int currentK = k;
        for (int i = 0;i < length;i++) {
            int minIndex = getMin(num, i, length);
            while (minIndex - i > currentK) {
                minIndex = getMin(num, i, minIndex);
            }
            num = swap(num, i, minIndex);
            currentK = currentK - minIndex + i;
            if (currentK <= 0) break;
        }
        return num;
    }

    private int getMin(String num, int beginIndex, int endIndex) {
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = beginIndex;i < endIndex;i++) {
            if (min > num.charAt(i) - '0') {
                min = num.charAt(i) - '0';
                minIndex = i;
            }
        }
        return minIndex;
    }

    private String swap(String num, int beginIndex, int minIndex) {
        StringBuilder builder = new StringBuilder();
        if (beginIndex != 0) builder.append(num.substring(0, beginIndex));
        builder.append(num.charAt(minIndex)).append(num.substring(beginIndex, minIndex));
        if (minIndex < num.length() - 1) builder.append(num.substring(minIndex+1));
        return builder.toString();
    }


    //44
    //通配符匹配
    public boolean isMatch2(String s, String p) {
        int slen = s.length();
        int plen = p.length();
        if (slen == 0 && plen == 0) return true;
        if (plen == 0) return false;

        boolean[][] result = new boolean[slen+1][plen+1];
        result[0][0] = true;
        for (int i = 0;i <= slen;i++) {
            for (int j = 1;j <= plen;j++) {
                if (p.charAt(j-1) == '*') {
                    if (i == 0) result[i][j] = result[i][j-1];
                    else result[i][j] = result[i][j-1] || result[i-1][j];
                }else {
                    if (check(s, p, i, j)) {
                        //System.out.println("i is " + i + "\nj is " + j);
                        result[i][j] = result[i-1][j-1];
                    }
                }
            }
        }
        // for (int i = 0;i < result.length;i++){
        //     for (int j = 0;j < result[0].length;j++)
        //         System.out.print(result[i][j] + " ");
        //     System.out.println();
        // }

        return result[slen][plen];
    }

    private boolean check(String s, String p, int i, int j) {
        if (i == 0) return false;
        if (p.charAt(j-1) == '?') return true;
        return s.charAt(i-1) == p.charAt(j-1);
    }

    //63
    //动态规划解机器人寻路计数
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        if (m == 0) return 0;
        int n = obstacleGrid[0].length;
        if (n == 0) return 0;

        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) return 0;

        int[][] result = new int[m+1][n+1];
        result[1][1] = 1;
        for (int i = 1;i <= m;i++) {
            for (int j = 1;j <= n;j++) {
                if (i == 1 || j == 1) {
                    if (i == 1 && j == 1) continue;
                    else if (i == 1 && obstacleGrid[i-1][j-2] == 0)
                        result[i][j] = result[i][j-1];
                    else if (j == 1 && obstacleGrid[i-2][j-1] == 0)
                        result[i][j] = result[i-1][j];
                    continue;
                }
                if (obstacleGrid[i-2][j-1] == 0) result[i][j] += result[i-1][j];
                if (obstacleGrid[i-1][j-2] == 0) result[i][j] += result[i][j-1];
            }
        }

        return result[m][n];
    }


    //面试题17.13 恢复空格
    public int respace(String[] dictionary, String sentence) {

        class Trie {
            Trie[] next;
            boolean isEnd;

            public Trie () {
                next = new Trie[26];
                isEnd = false;
            }

            public void insert(String s) {
                Trie node = this;
                for (int i = s.length() - 1;i >= 0;i--) {
                    int value = s.charAt(i) - 'a';
                    if (node.next[value] == null) node.next[value] = new Trie();
                    node = node.next[value];
                }
                node.isEnd = true;
            }
        }


        int length = sentence.length();
        if (length == 0 || dictionary.length == 0) return 0;

        Trie record = new Trie();
        for (int i = 0;i < dictionary.length;i++) {
            record.insert(dictionary[i]);
        }

        int[] result = new int[length+1];
        Arrays.fill(result, Integer.MAX_VALUE);
        result[0] = 0;
        for (int i = 1;i <= length;i++) {
            result[i] = result[i-1] + 1;
            Trie current = record;
            for (int j = i;j >= 1;j--) {
                int value = sentence.charAt(j-1) - 'a';
                if (current.next[value] == null) break;
                else if (current.next[value].isEnd) {
                    result[i] = Math.min(result[i], result[j-1]);
                }
                if (result[i] == 0) break;
                current = current.next[value];
            }
        }
        return result[length];
    }


    //315 计算右侧小于当前数的个数
    //倒序遍历+快排+二分
    public List<Integer> countSmaller(int[] nums) {
        int length = nums.length;
        List<Integer> result = new LinkedList<>();
        if (length == 0) return result;
        if (length == 1) { result.add(0); return result;}

        for (int i = length - 1;i >= 0;i--) {
            if (i == length - 1) {
                result.add(0, 0);
                continue;
            }
            Arrays.sort(nums, i+1, length);
            int begin = i + 1;
            int end = length - 1;
            if (begin == end) {
                result.add(0, nums[i] > nums[begin] ? 1 : 0);
                continue;
            }
            if (nums[begin] == nums[end]) {
                result.add(0, nums[i] > nums[begin] ? end - begin + 1 : 0);
                continue;
            }
            if (nums[i] <= nums[begin] || nums[i] >= nums[end]) {
                if (nums[i] <= nums[begin]) result.add(0, 0);
                else if (nums[i] == nums[end]) {
                    int tmp = end-1;
                    while(nums[tmp] == nums[tmp+1]) tmp--;
                    result.add(0, tmp-begin+1);
                }else result.add(0, end-begin+1);
                continue;
            }
            while (begin < end) {
                int mid = begin + ((end - begin) >> 1);
                if (nums[mid] >= nums[i]) end = mid;
                if (nums[mid] < nums[i]) begin = mid + 1;
            }
            result.add(0, begin-i-1);
        }
        return result;
    }

    //174
    //动态规划解地下城拯救公主
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        if (m == 0) return 0;
        int n = dungeon[0].length;
        if (n == 0) return 0;

        int[][] result = new int[m][n];
        for (int i = m-1;i >= 0;i--) {
            for (int j = n-1;j >= 0;j--) {
                if (i == m-1 || j == n-1) {
                    if (i == m-1 && j == n-1) result[i][j] = dungeon[i][j] <= 0 ?
                            -dungeon[i][j] + 1: 1;
                    else if (i == m-1) result[i][j] = dungeon[i][j] >= 0 ?
                            Math.max(1, result[i][j+1] - dungeon[i][j]) : result[i][j+1] - dungeon[i][j];
                    else if (j == n-1) result[i][j] = dungeon[i][j] >= 0 ?
                            Math.max(1, result[i+1][j] - dungeon[i][j]) : result[i+1][j] - dungeon[i][j];
                    continue;
                }
                result[i][j] = Math.min(result[i+1][j], result[i][j+1]);
                result[i][j] = dungeon[i][j] >= 0 ?
                        Math.max(1, result[i][j] - dungeon[i][j]) : result[i][j] - dungeon[i][j];
            }
        }
        // for (int i = 0;i < result.length;i++) {
        //     for (int j = 0;j < result[0].length;j++)
        //         System.out.print(result[i][j] + " ");
        //     System.out.println();
        // }
        return result[0][0];
    }

    //350
    //两数组交集
    public int[] intersect(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        if (length1 == 0 || length2 == 0) return new int[0];

        HashMap<Integer, Integer> map1 = new HashMap<>();
        HashMap<Integer, Integer> map2 = new HashMap<>();
        int length = Math.max(length1, length2);
        for (int i = 0;i < length;i++) {
            if (i < length1) {
                int count = map1.getOrDefault(nums1[i], 0);
                map1.put(nums1[i], ++count);
            }
            if (i < length2) {
                int count = map2.getOrDefault(nums2[i], 0);
                map2.put(nums2[i], ++count);
            }
        }
        Set<Integer> keys = map1.size() > map2.size() ? map2.keySet() : map1.keySet();
        int[] result = new int[Math.min(length1, length2)];
        int index = 0;
        for (Integer key : keys) {
            if (map1.containsKey(key) && map2.containsKey(key)) {
                int count = Math.min(map1.get(key), map2.get(key));
                while (count > 0) {
                    result[index] = key;
                    index++;
                    count--;
                }
            }
        }
        return Arrays.copyOf(result, index);
    }

    //120
    //三角形最小路径和
    //递归写法
    int[] record2 = null;
    int result = Integer.MAX_VALUE;

    public int minimumTotal(List<List<Integer>> triangle) {
        int length = triangle.size();
        if (length == 0) return 0;
        if (triangle.get(0).size() == 0) return 0;
        record2 = new int[length];
        calculate(triangle, 0, 0);
        return result;
    }

    private void calculate(List<List<Integer>> triangle, int i, int j) {
        record2[i] = triangle.get(i).get(j) + (i == 0 ? 0 : record2[i-1]);
        if (i == triangle.size()-1) result = Math.min(record2[i], result);
        if (i + 1 < triangle.size()) {
            calculate(triangle, i+1, j);
            calculate(triangle, i+1, j+1);
        }
    }

    //动态规划解法
    public int minimumTotal2(List<List<Integer>> triangle) {
        int length = triangle.size();
        if (length == 0) return 0;
        int[] record = new int[length];

        record[0] = triangle.get(0).get(0);
        for (int i = 1;i < length;i++) {
            record[i] = record[i-1] + triangle.get(i).get(i);
            for (int j = i-1;j > 0;j--) {
                record[j] = Math.min(record[j-1], record[j]) + triangle.get(i).get(j);
            }
            record[0] += triangle.get(i).get(0);
        }
        int result = record[0];
        for (int tmp : record)
            result = Math.min(result, tmp);
        return result;
    }
}
