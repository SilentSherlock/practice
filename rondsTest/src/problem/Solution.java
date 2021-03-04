package problem;

import tools.ListNode;
import tools.TreeNode;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Date:2020/8/23
 * Description: optional class description
 **/
public class Solution {

    public String findPublicPre(String[] strings) {

        if (strings.length == 0) return "";
        if (strings.length == 1) return strings[0];

        int minLength = Integer.MAX_VALUE;
        for (String str : strings) {
            minLength = Math.min(minLength, str.length());
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i < minLength;i++) {
            int j = 0;
            for (;j < strings.length-1;j++) {
                if (strings[j].charAt(i) != strings[j+1].charAt(i)) {
                    break;
                }
            }
            if (j == strings.length-1) stringBuilder.append(strings[j].charAt(i));
        }

        return stringBuilder.toString();
    }

    public int findNthNum(int[] nums, int n) {
        int length = nums.length;
        int flag = 0;
        String value = "";
        for (int i = 0;i < length && flag < n;i++) {
            value = String.valueOf(nums[i]);
            flag += value.length();
        }
        /*System.out.println("flag is" + flag);
        System.out.println("value is " + value);*/
        return value.charAt(value.length()-(flag-n)-1) - '0';
    }

    public List<Integer> getMaxInK(int[] nums, int k) {
        Deque<Integer> deque = new LinkedList<>();
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0;i < k;i++) {
            queue.add(nums[i]);
            if (deque.isEmpty()) {
                deque.addLast(nums[i]);
                continue;
            }
            while (!deque.isEmpty() && deque.peekLast() < nums[i]) {
                deque.pollLast();
            }
            deque.addLast(nums[i]);
        }
        System.out.println("deque length: " + deque.size());
        List<Integer> result = new LinkedList<>();
        result.add(deque.peekFirst());
        for (int i = k;i < nums.length;i++) {
            //向左弹出
            if (queue.poll().equals(deque.peekFirst())) {
                deque.pollFirst();
                System.out.println("here");
            }

            //向右加入
            System.out.println("deque: " + deque.peekFirst());
            System.out.println("deque length: " + deque.size());
            queue.add(nums[i]);
            while (!deque.isEmpty() && deque.peekLast() < nums[i]) {
                deque.pollLast();
            }
            deque.addLast(nums[i]);
            result.add(deque.peekFirst());
        }

        return result;
    }

    public int mySqrt(int x) {
        if (x <= 1) return x;
        if (x == 4 || x == 5) return 2;
        int left = 1;
        int right = (int) Math.sqrt(Integer.MAX_VALUE);
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (mid == left) break;
            /*System.out.println("left: " + left);
            System.out.println("right: " + right);
            System.out.println("mid: " + mid);
            System.out.println("multi: " + mid*mid);*/
            if (mid * mid > x) right = mid;
            else if (mid * mid < x) left = mid;
            else return mid;
        }
        return right*right > x ? left : right;
    }

    private int k = 0;
    public int findKth(int[] a, int n, int K) {
        // write code here
        quickSort(a, 0, n-1, K);
        return k;
    }

    private void quickSort(int[] nums, int left, int right, int target) {
        if (left == right) {
            if (left+1 == target) k = nums[left];
        }
        System.out.println("left: " + left + " right: " + right);
        int gap = nums[left];
        int flag = left+1;
        for (int i = flag;i <= right;i++) {
            if (nums[i] > gap) {
                int tmp = nums[i];
                nums[i] = nums[flag];
                nums[flag] = tmp;
                flag++;
            }
        }
        nums[left] = nums[flag-1];
        nums[flag-1] = gap;

        if (flag < target) quickSort(nums, flag, right, target);
        else if (flag > target) quickSort(nums, left, flag-2, target);
        else k = nums[flag-1];
    }

    public ListNode mergeTwoLists (ListNode l1, ListNode l2) {
        // write code here
        if (l1 == null || l2 == null) return l1 == null ? l2 : l1;
        ListNode head;
        if (l1.val <= l2.val) {
            head = l1;
            l1 = l1.next;
        }else {
            head = l2;
            l2 = l2.next;
        }
        ListNode current = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            }else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        if (l1 != null || l2 != null) {
            current.next = l1 != null ? l1 : l2;
        }
        return head;
    }

    public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        int length = num.length;
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (length <= 2) return result;
        Arrays.sort(num);
        if (num[0] > 0 || num[length-1] < 0) return result;

        if (num[0] == num[length-1]) {
            if (num[0] == 0) {
                ArrayList<Integer> tmp = new ArrayList<>();
                tmp.add(0);
                tmp.add(0);
                tmp.add(0);
                result.add(tmp);
                return result;
            }
        }

        for (int i = 0;i < length-2;i++) {
            if (i != 0 && num[i] == num[i-1]) continue;
            int left = i+1;
            int right = length-1;
            while (left < right) {
                int tmp = num[left] + num[right];
                if (tmp == -num[i]){
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(num[i]);
                    list.add(num[left]);
                    list.add(num[right]);
                    result.add(list);
                    while (left < right && num[left] == num[left + 1]) left++;
                    while (left < right && num[right] == num[right - 1]) right--;
                    left++;
                    right--;
                }else if (tmp > -num[i]) right--;
                else left++;
            }
        }
        return result;
    }

    public int upper_bound_ (int n, int v, int[] a) {
        // write code here
        if (v > a[n-1]) return n + 1;
        int left = 0, right = n-1;
        while(left < right) {
            int mid = left + ((right - left) >> 1);
            if (v < a[mid]) right = mid;
            else if (v > a[mid]) left = mid + 1;
            else {
                while (mid != 0 && a[mid-1] == v) mid--;
                return mid+1;
            }
        }
        return left+1;
    }

    public ListNode removeNthFromEnd (ListNode head, int n) {
        // write code here
        if (head == null) return head;
        LinkedList<ListNode> list = new LinkedList<>();
        ListNode ptr = head;
        while (ptr != null) {
            list.add(ptr);
            ptr = ptr.next;
        }
        int pos = list.size() - n;
        if (pos == 0) {
            ptr = head;
            head = head.next;
            ptr.next = null;
        }else if (pos == list.size()-1) {
            list.get(pos-1).next = null;
        }else {
            list.get(pos-1).next = list.get(pos).next;
            list.get(pos).next = null;
        }
        return head;
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }

    public ListNode addInList (ListNode l1, ListNode l2) {
        // write code here
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        LinkedList<ListNode> list1 = new LinkedList<>();
        LinkedList<ListNode> list2 = new LinkedList<>();

        ListNode ptr1 = l1;
        ListNode ptr2 = l2;

        while (ptr1 != null || ptr2 != null) {
            if (ptr1 != null) {
                list1.add(ptr1);
                ptr1 = ptr1.next;
            }
            if (ptr2 != null) {
                list2.add(ptr2);
                ptr2 = ptr2.next;
            }
        }

        while (list1.size() < list2.size()) {
            ListNode tmp = new ListNode(0);
            tmp.next = list1.get(0);
            list1.addFirst(tmp);
        }
        while (list2.size() < list1.size()) {
            ListNode tmp = new ListNode(0);
            tmp.next = list2.get(0);
            list2.addFirst(tmp);
        }

        int plus = 0;
        for (int i = list1.size()-1;i >= 0;i--) {
            int va11 = list1.get(i).val;
            int val2 = list2.get(i).val;
            int sum = va11 + val2 + plus;
            list1.get(i).val = sum % 10;
            plus = sum / 10;
        }

        if (plus != 0) {
            ListNode head = new ListNode(plus);
            head.next = list1.get(0);
            return head;
        }
        return list1.get(0);
    }

    public ArrayList<ArrayList<Integer>> zigzagLevelOrder (TreeNode root) {
        // write code here
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.addLast(root);
        int flag = 0;//为偶时自左向右，为奇数时自右向左
        while (!deque.isEmpty()) {
            int length = deque.size();
            ArrayList<Integer> cur = new ArrayList<>();
            for (int i = 0;i < length;i++) {
                if (flag % 2 == 0) {
                    TreeNode node = deque.pollFirst();
                    cur.add(node.val);
                    if (node.left != null) deque.addLast(node.left);
                    if (node.right != null) deque.addLast(node.right);
                }else if (flag % 2 == 1) {
                    TreeNode node = deque.pollLast();
                    cur.add(node.val);
                    if (node.right != null) deque.addFirst(node.right);
                    if (node.left != null) deque.addFirst(node.left);
                }
            }
            result.add(cur);
            flag++;
        }

        return result;
    }

    public boolean isValid (String s) {
        // write code here
        if (s == null) return true;
        int length = s.length();
        if (length == 0 || length % 2 == 1) return false;

        Stack<Character> stack = new Stack<>();
        for (int i = 0;i < length;i++) {
            char cur = s.charAt(i);
            switch(cur) {
                case '(':
                case '[':
                case '{':
                    stack.push(cur);
                    break;
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') return false;
                    break;
                case ']':
                    if (stack.isEmpty() || stack.pop() != '[') return false;
                    break;
                case '}':
                    if (stack.isEmpty() || stack.pop() != '{') return false;
                    break;
                default:
                    return false;
            }
        }

        return stack.isEmpty();
    }

    public ListNode reverseBetween (ListNode head, int m, int n) {
        // write code here
        if (n - m == 0 || head == null) return head;
        int flag = 1;
        ListNode reverseBefore = null;
        ListNode node = head;
        ListNode reverseHead = null;
        ListNode reverseTail = null;
        ListNode reverseAfter = null;
        while (node != null) {
            if (flag == m-1) reverseBefore = node;
            else if (flag == m) reverseHead = node;
            else if (flag == n) {
                reverseTail = node;
            }else if (flag == n+1) {
                reverseAfter = node;
                break;
            }
            flag++;
            node = node.next;
        }
        reverse(reverseHead, reverseTail);
        if (reverseBefore != null) reverseBefore.next = reverseTail;
        reverseHead.next = reverseAfter;
        return m != 1 ? head : reverseTail;
    }

    private void reverse(ListNode head, ListNode tail) {
        ListNode ptr = head;
        ListNode next = ptr.next;
        head.next = null;
        while (ptr != tail) {
            ListNode nextnext = next.next;
            next.next = ptr;
            ptr = next;
            next = nextnext;
        }
    }

    public int maxLength (int[] arr) {
        // write code here
        int length = arr.length;
        if (length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0;i < length;i++) {
            int left = i-1;
            int right = i+1;
            if (i == 0) left = i;
            if (i == length-1) right = length-1;
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(arr[i], 1);
            while (left >= 0 && right <= length-1) {
                if (!map.containsKey(arr[left])) {
                    map.put(arr[left], 1);
                    left--;
                }else if (!map.containsKey(arr[right])) {
                    map.put(arr[right], 1);
                    right++;
                }else {
                    break;
                }
            }
            max = Integer.max(max, map.size());
        }
        return max;
    }

    /*public int solve (char[][] grid) {
        // write code here
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        UnionFind union = new UnionFind(m, n, grid);
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (grid[i][j] == '1') {
                    if (i != 0 && grid[i-1][j] == '1') union.union((i-1)*m+j, i*m+j);
                    if (j != 0 && grid[i][j-1] == '1') union.union(i*m+(j-1), i*m+j);
                }
            }
        }
        return union.count;
    }

    class UnionFind {
        int[] record;
        int count;

        UnionFind(int m, int n, char[][] grid) {
            int length = m*n;
            record = new int[length];
            for (int i = 0;i < length;i++) {
                record[i] = i;
                if (grid[i/m][i%m] == '1') count++;
            }
        }

        int find(int p) {
            if (p != record[p]) record[p] = find(record[p]);
            return record[p];
        }

        void union(int p, int q) {
            int pid = find(p);
            int qid = find(q);
            if (pid == qid) return;
            record[pid] = qid;
            count--;
        }
    }*/

    public void Mirror(TreeNode root) {
        transform(root);
    }

    private TreeNode transform(TreeNode node) {
        if (node == null) return node;
        TreeNode tmp = node.left;
        node.left = transform(node.right);
        node.right = transform(tmp);
        return node;
    }

    private LinkedList<Integer> list;
    private LinkedList<Integer> tmp = new LinkedList<>();
    public int lowestCommonAncestor (TreeNode root, int o1, int o2) {
        // write code here
        traverse(root, o1);
        LinkedList l1 = list;
        traverse(root, o2);
        LinkedList l2 = list;
        int size1 = l1.size()-1;
        while (size1 >= 0) {
            if (l1.get(size1).equals(o1)) break;
            size1--;
        }
        int size2 = l2.size()-1;
        while (size2 >= 0) {
            if (l2.get(size2).equals(o2)) break;
            size2--;
        }

        while (size1 >= 0 && size2 >= 0) {
            if (l1.get(size1).equals(l2.get(size2))) break;
            size1--;
            size2--;
        }
        return (Integer)l1.get(size1);
    }

    private void traverse(TreeNode node, int target) {
        if (node.val == target) {
            tmp.add(node.val);
            list = new LinkedList<>(tmp);
            return;
        }
        tmp.addLast(node.val);
        if (node.left != null) traverse(node.left, target);
        if (node.right != null) traverse(node.right, target);
        tmp.removeLast();
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left == null ? right : left;
    }

    /*
    * k已经小于nums中0的个数，且大于0
    * 双指针法，保证窗口内始终将k个0转变为1
    * */
    public int getLongestSubstring(int[] nums, int k) {
        int left = 0, right = 0;
        int length = nums.length;
        int result = Integer.MIN_VALUE;
        while (right < length) {
            if (nums[right] == 0) k--;
            while (k < 0) {
                if (nums[left] == 0) {
                    k++;
                }
                left++;
            }
            result = Math.max(result, right-left+1);
            right++;
        }
        return result;
    }

    public int maxSumOfSubArray (int[] arr) {
        // write code here
        int length = arr.length;
        if (length == 1) return Math.max(arr[0], 0);
        int[] record = new int[length+1];
        for (int i = 1;i <= length;i++) {
            record[i] = Math.max(record[i-1]+arr[i-1], arr[i-1]);
        }
        return record[length];
    }

    public int leastInterval2(char[] tasks, int n) {
        int length = tasks.length;
        if (length <= 1) return length == 0 ? 0 : 1;

        Mission[] record = new Mission[26];
        for (int i = 0;i < record.length;i++) {
            record[i] = new Mission();
        }
        Queue<Mission> queue = new LinkedList<>();
        for (int i = 0;i < length;i++) {
            int type = tasks[i] - 'A';
            record[type].type = type;
            record[type].count++;
        }

        Arrays.sort(record, (o1, o2) -> o1.count > o2.count ? -1 : 1);

        for (Mission mission : record) {
            if (mission.count == 0) break;
            queue.add(mission);
        }

        int result = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            if (size >= n+1) {
                for (int i = 0;i < size;i++) {
                    Mission cur = queue.poll();
                    if (--cur.count > 0) queue.add(cur);
                }
                result += size;
            }else {
                for (int i = 0;i < n;i++) {
                    if (i < size) {
                        Mission cur = queue.poll();
                        if (--cur.count > 0) queue.add(cur);
                    }
                }
                if (queue.isEmpty()) result += size;//最后一轮
                else result += (n+1);
            }
        }
        return result;
    }

    class Mission {
        int type;
        int count;

        Mission() {
            type = -1;
            count = 0;
        }

        Mission(int type, int count) {
            this.type = type;
            this.count = count;
        }
    }

    public int leastInterval(char[] tasks, int n) {
        int[] map = new int[26];
        for (char c: tasks)
            map[c - 'A']++;
        Arrays.sort(map);
        int time = 0;
        while (map[25] > 0) {
            int i = 0;
            while (i <= n) {
                if (map[25] == 0)
                    break;
                if (i < 26 && map[25 - i] > 0)
                    map[25 - i]--;
                time++;
                i++;
            }
            Arrays.sort(map);
        }
        return time;
    }

    int result = 0;
    public int findTargetSumWays(int[] nums, int S) {
        dfs(nums, S, 0, 0);
        return result;
    }

    private void dfs(int[] nums, int s, int sum, int i) {
        if (i < nums.length) {
            dfs(nums, s, sum+nums[i], i+1);
            dfs(nums, s, sum-nums[i], i+1);
        }else if(i == nums.length) {
            if (sum == s) result++;
        }
    }

    List<List<Integer>> result1;
    LinkedList<Integer> tmp1;
    int[] array;
    public List<List<Integer>> subsets(int[] nums) {
        result1 = new LinkedList<>();
        tmp1 = new LinkedList<>();
        array = nums;
        dfs(0);
        return result1;
    }

    private void dfs(int index) {
        if (index == array.length) {
            result1.add(new LinkedList(tmp1));
        }else if (index < array.length) {
            tmp1.addLast(array[index]);
            dfs(index+1);
            tmp1.removeLast();
            dfs(index+1);
        }
    }

    int current = 19981128;
    boolean flag;
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        traverse(root);
        return !flag;
    }

    private void traverse(TreeNode node) {
        if (node.left != null) traverse(node.left);
        if (current != 19981128) {
            if (node.val <= current) {
                flag = true;
                return;
            }
        }
        current = node.val;
        if (node.right != null) traverse(node.right);
    }

    public int[] topKFrequent(int[] nums, int k) {
        int length = nums.length;
        if (length == 0) return nums;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0;i < length;i++) {
            if (!map.containsKey(nums[i])) map.put(nums[i], 1);
            else {
                int count = map.get(nums[i]);
                map.put(nums[i], ++count);
            }
        }

        PriorityQueue<Element> queue = new PriorityQueue<>(new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                return o1.freq > o2.freq ? -1 : 1;
            }
        });
        for (int key : map.keySet()) {
            Element e = new Element(key, map.get(key));
            queue.add(e);
        }
        int[] result = new int[k];
        for (int i = 0;i < k;i++) {
            result[i] = queue.poll().val;
        }
        return result;
    }

    class Element {
        int val;
        int freq;

        Element(int val, int freq) {
            this.val = val;
            this.freq = freq;
        }
    }

    public int findUnsortedSubarray(int[] nums) {
        int length = nums.length;
        if (length <= 1) return 0;
        if (length == 2) return nums[0] <= nums[1] ? 0 : length;

        int begin = 0, end = length - 1;
        if (nums[begin] > nums[end]) return length;
        boolean first = false, last = false;
        while (begin < end) {
            if (nums[begin] > nums[end]) return end - begin + 1;
            int[] tmp = Arrays.copyOf(nums, length);
            Arrays.sort(tmp, begin+1, end);
            if (!first && nums[begin] <= tmp[begin+1]) {
                begin++;
            }else {
                first = true;
            }
            if (!last && nums[end] >= tmp[end-1]) {
                end--;
            }else {
                last = true;
            }
            if (first && last) break;
        }
        return first && last ? end - begin + 1 : 0;
    }

    public int search(int[] nums, int target) {
        int length = nums.length;
        if (length == 0) return -1;
        if (length == 1) return nums[0] == target ? 0 : -1;
        int first = 0, last = length - 1;
        if (nums[first] == target || nums[last] == target) return nums[first] == target ? first : last;
        if (nums[first] < nums[last]) return binarySearch(nums, first, last, target);
        if (nums[first] < target) {
            int i = last;
            while(i > first && nums[i] > nums[i-1]) i--;
            if (i == first) return -1;
            else return binarySearch(nums, first, i-1, target);
        }
        if (nums[last] > target) {
            int i = last;
            while(i > first && nums[i] > nums[i-1]) {
                if (nums[i] == target) return i;
                i--;
            }
            if (nums[i] == target) return i;
        }
        return -1;
    }

    private int binarySearch(int[] nums, int beginIndex, int endIndex, int key) {
        int begin = beginIndex;
        int end = endIndex;

        while (begin < end) {
            int mid = begin + ((end - begin) >> 1);
            if (nums[mid] > key) end = mid;
            else if (nums[mid] < key) begin = mid + 1;
            else return mid;
        }
        if (begin == end && nums[end] == key) return end;
        return -1;
    }

    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] > o2[0]) return -1;
                else if (o1[0] < o2[0]) return 1;
                else {
                    return Integer.compare(o1[1], o2[1]);
                }
            }
        });

        List<int[]> list = new LinkedList<>();
        for (int[] cur : people) {
            list.add(cur[1], cur);
        }
        return list.toArray(new int[list.size()][2]);
    }

    List<Integer> tmp2 = new ArrayList<>();
    List<List<Integer>> result2 = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        for (int num : nums) {
            tmp2.add(num);
        }
        backTrack(0);
        return result2;
    }

    private void backTrack(int cur) {
        if (cur == tmp2.size()) {
            result2.add(new ArrayList<>(tmp2));
        }

        for (int i = cur;i < tmp2.size();i++) {
            Collections.swap(tmp2, cur, i);
            backTrack(cur+1);
            Collections.swap(tmp2, cur, i);
        }
    }

    int result4 = 0;
    public int pathSum(TreeNode root, int sum) {
        List<Integer> record = new ArrayList<>();
        dfs(root, record, sum);
        return result4;
    }

    private void dfs(TreeNode node, List<Integer> parentSum, int sum) {
        if (node == null) return;
        for (int i = 0;i < parentSum.size();i++) {
            parentSum.set(i, parentSum.get(i) + node.val);
            if (parentSum.get(i) == sum) result4++;
        }
        parentSum.add(node.val);
        dfs(node.left, parentSum, sum);
        dfs(node.right, parentSum, sum);
        for (int i = 0;i < parentSum.size();i++) {
            parentSum.set(i, parentSum.get(i) - node.val);
        }
        parentSum.remove(parentSum.size()-1);
    }

    List<String> result5 = new ArrayList<>();
    StringBuilder builder = new StringBuilder();
    public List<String> generateParenthesis(int n) {
        if (n == 0) return null;
        Stack<Character> stack = new Stack<>();
        dfs(0, n, stack, 0, 0);
        return result5;
    }

    private void dfs(int depth, int n, Stack<Character> stack, int leftNum, int rightNum) {
        if (depth == n*2 && stack.empty()) {
            //System.out.println(builder.toString());
            result5.add(builder.toString());
        }else {
            if (leftNum > n || rightNum > n) return;

            builder.append('(');
            stack.push('(');
            dfs(depth+1, n, stack, leftNum+1, rightNum);
            builder.deleteCharAt(depth);
            if (!stack.empty()) stack.pop();
            else {
                String str = builder.toString();
                for (int i = 0;i < str.length();i++) {
                    char c = str.charAt(i);
                    if (c == '(') stack.push('(');
                    else if (c == ')') stack.pop();
                }
            }

            if (stack.empty()) return;
            builder.append(')');
            if (stack.peek() == '(') stack.pop();
            dfs(depth+1, n, stack, leftNum, rightNum+1);
            builder.deleteCharAt(depth);
        }
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        int length = nums.length;
        if (length == 0) return null;
        List<Integer> result = new ArrayList<>();

        for (int i = 0;i < 2;i++) {
            for (int j = 0;j < length;j++) {
                if (nums[j] - 1 != j && nums[nums[j]-1] != nums[j]) {
                    int tmp = nums[j];
                    nums[j] = nums[nums[j]-1];
                    nums[tmp-1] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
        for (int i = 0;i < length;i++) {
            if (nums[i]-1 != i) result.add(i+1);
        }
        return result;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        int length = nums.length;
        List<List<Integer>> result = new LinkedList<>();
        if (length < 4) return result;

        Arrays.sort(nums);
        for (int i = 0;i < length-3;i++) {
            if (i != 0 && nums[i-1] == nums[i]) continue;
            for (int j = i+1;j < length-2;j++) {
                if (j-1 != i && nums[j-1] == nums[j]) continue;
                int cur = target - nums[i] - nums[j];
                int left = j+1;
                int right = length-1;
                while (left < right) {
                    int sum = nums[left] + nums[right];
                    if (sum == cur) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        left++;
                        right--;
                    }else if (sum < cur) left++;
                    else right--;
                    while (left-1 != j && nums[left-1] == nums[left]) left++;
                    while (right != length -1 && nums[right+1] == nums[right]) right--;
                }
            }
        }
        return result;
    }

    public int movingCount(int m, int n, int k) {

        int i = 0, j = 0;
        while (j < n && sum(split(i, j)) <= k) j++;
        if (j == n) j -= 1;
        int result = 0;
        while (i < m && j >= 0) {
            int cur = sum(split(i, j));
            if (cur == k) {
                result += (j + 1);
                i++;
            }else if (cur > k) j--;
            else j++;
        }
        return result;
    }

    //将坐标m, n分割成四个数位
    private int[] split(int m, int n) {
        int[] divs = new int[4];
        divs[0] = m/10;
        divs[1] = m%10;
        divs[2] = n/10;
        divs[3] = n%10;
        return divs;
    }

    private int sum(int[] nums) {
        int length = nums.length;
        int result = 0;
        for (int num : nums) {
            result += num;
        }
        return result;
    }

    public ListNode deleteNode(ListNode head, int val) {
        if (head == null || head.val != val) return head;
        if (head.val == val) return head.next;

        ListNode cur = head.next;
        ListNode before = head;
        while (cur != null) {
            if (cur.val == val) {
                before.next = cur.next;
                break;
            }
            cur = cur.next;
            before = before.next;
        }
        return head;
    }

    public int coinChange(int[] coins, int amount) {
        int length = coins.length;
        if (length == 0) return -1;
        if (length == 1) return amount % coins[0] == 0 ? amount / coins[0] : -1;

        int[] dp = new int[amount+1];
        Arrays.fill(dp, amount+1);
        dp[0] = 0;
        for (int i = 1;i <= amount;i++) {
            for (int j = 0;j < coins.length;j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] == amount+1 ? -1 : dp[amount];
    }

    public String[] permutation(String s) {
        if (s == null || s.length() == 0) return new String[]{};
        int length = s.length();
        if (length == 1) return (new String[]{s});

        List<String> result = new LinkedList<>();
        HashSet<String> set = new HashSet<>();
        dfs(s.toCharArray(), 0, result, set);
        return result.toArray(new String[]{});
    }

    public void dfs(char[] chars, int index, List<String> result, HashSet<String> set) {
        if (index == chars.length-1) {
            StringBuilder builder = new StringBuilder();
            builder.append(chars);
            //System.out.println(builder.toString());
            String str = builder.toString();
            if (!set.contains(str)) {
                result.add(builder.toString());
                set.add(str);
            }
        }else {
            for (int i = index, len = chars.length;i < len;i++) {
                char tmp = chars[i];
                if (i != index && tmp == chars[index]) continue;
                chars[i] = chars[index];
                chars[index] = tmp;
                dfs(chars, index+1, result, set);
                chars[index] = chars[i];
                chars[i] = tmp;
            }
        }
    }

    int res = 0;
    public int reversePairs2(int[] nums) {
        int length = nums.length;
        if (length <= 1) return 0;
        if (length == 2) return nums[0] > nums[1] ? 1 : 0;

        mergeSort(nums, 0, length-1);
        return res;
    }

    public int[] mergeSort(int[] nums, int begin, int end) {
        if (end - begin == 0) return new int[]{nums[begin]};
        int mid = begin + ((end - begin) >> 1);
        int[] left = mergeSort(nums, begin, mid);
        int[] right = mergeSort(nums, mid+1, end);
        return merge(left, right);
    }

    public int[] merge(int[] left, int[] right) {
        int m = left.length;
        int n = right.length;
        int[] result = new int[m+n];
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            if (left[i] > right[j]) {
                result[k] = right[j];
                j++;
            }else {
                result[k] = left[i];
                res += j;
                i++;
            }
            k++;
        }
        while (i < m) {
            result[k] = left[i];
            res += n;
            i++;
            k++;
        }
        while (j < n) {
            result[k] = right[j];
            j++;
            k++;
        }
        //System.out.println(Arrays.toString(result) + " " + res);
        return result;
    }

    public int nthUglyNumber(int n) {
        if (n <= 6) return n;
        int[] record = new int[n+1];
        record[1] = 1;
        int ptr2 = 1, ptr3 = 1, ptr5 = 1;
        for (int i = 2;i <= n;i++) {
            int cur = Math.min(Math.min(record[ptr2] * 2, record[ptr3] * 3), record[ptr5] * 5);
            record[i] = cur;
            if (cur == record[ptr2] * 2) ptr2++;
            if (cur == record[ptr3] * 3) ptr3++;
            if (cur == record[ptr5] * 5) ptr5++;
        }

        return record[n];
    }

    int result6 = 0;
    public int totalNQueens(int n) {
        if (n == 1 || n == 0) return n;
        backTrack(n, 0, new HashSet<>(), new HashSet<>(), new HashSet<>());
        return result6;
    }

    public void backTrack(int n, int row, HashSet<Integer> cols, HashSet<Integer> tilted1, HashSet<Integer> tilted2) {
        if (row == n) {
            result6 += 1;
        }else {
            for (int i = 0;i < n;i++) {
                if (cols.contains(i)) continue;
                int t1 = row - i;
                if (tilted1.contains(t1)) continue;
                int t2 = row + i;
                if (tilted2.contains(t2)) continue;

                cols.add(i);
                tilted1.add(t1);
                tilted2.add(t2);
                backTrack(n, row+1, cols, tilted1, tilted2);
                cols.remove(i);
                tilted1.remove(t1);
                tilted2.remove(t2);
            }
        }
    }

    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        if (m == 0) return;
        int n = matrix[0].length;
        if (n == 0) return;

        HashSet<Integer> rows = new HashSet<>();
        HashSet<Integer> cols = new HashSet<>();
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (matrix[i][j] == 0) {
                    rows.add(i);
                    cols.add(j);
                }
            }
        }
        for (int i = 0;i < m;i++) {
            for (int j = 0;j < n;j++) {
                if (rows.contains(i) || cols.contains(j)) matrix[i][j] = 0;
            }
        }
    }

    public int longestMountain(int[] A) {
        int length = A.length;
        if (length < 3) return 0;
        int result = 0;
        for (int i = 1;i < length-1;i++) {
            int left = i-1, right = i+1;
            int afterLeft = A[i], beforeRight = A[i];
            if (A[left] >= afterLeft || A[right] >= beforeRight) continue;
            while (left >= 0 || right <= length-1) {
                if (A[left] >= afterLeft && A[right] >= beforeRight) break;
                if (left >= 0 && A[left] < afterLeft) {
                    afterLeft = A[left];
                    left--;
                }
                if (right <= length-1 && A[right] < beforeRight) {
                    beforeRight = A[right];
                    right++;
                }

            }
            System.out.println("i:" + i);
            System.out.println("left:" + left + "right:" + right);
            result = Math.max(result, right - left - 1);
            System.out.println("result:" + result);
        }
        return result < 3 ? 0 : result;
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> result = new LinkedList<>();
        if (s.length() == 0) return result;
        Trie trie = new Trie(wordDict);
        List<List<String>> sentences = backTrack(s, s.length(), 0, trie, new HashMap<Integer, List<List<String>>>());
        for (List<String> sen : sentences) {
            String str = String.join(" ", sen);
            result.add(str);
        }
        return result;
    }

    private List<List<String>> backTrack(String s, int length, int index, Trie trie, HashMap<Integer, List<List<String>>> map) {
        if (!map.containsKey(index)) {
            List<List<String>> cur = new LinkedList<>();
            if (index >= length) cur.add(new LinkedList<>());
            for (int i = index+1;i <= length;i++) {
                String str = s.substring(index, i);
                if (!trie.startWith(str)) break;
                if (trie.search(str)) {
                    List<List<String>> subs = backTrack(s, length, i, trie, map);
                    for (List<String> sub : subs) {
                        LinkedList<String> tmp = new LinkedList<>(sub);
                        tmp.addFirst(str);
                        cur.add(tmp);
                    }
                }
            }
            map.put(index, cur);
        }
        return map.get(index);
    }

    class Trie {
        private Node root;

        public class Node {
            int val;
            Node[] nextNodes;

            public Node() {
                this.val = 0;
                nextNodes = new Node[26];
            }

            public Node(int val) {
                this.val = val;
                nextNodes = new Node[26];
            }
        }

        public Trie() {
            this.root = new Node();
        }

        public Trie(List<String> dic) {
            this.root = new Node();
            for (String word : dic) {
                insert(word);
            }
        }
        public void insert(String word) {
            root = insert(root, 0, word);
        }

        private Node insert(Node node, int index, String word) {
            if (node == null) node = new Node();
            if (index == word.length()) {
                node.val = 1;
                return node;
            }
            char cur = word.charAt(index);
            node.nextNodes[cur - 'a'] = insert(node.nextNodes[cur - 'a'], index+1, word);
            return node;
        }

        public boolean search(String word) {
            return search(root, 0, word) == 1;
        }

        public boolean startWith(String prefix) {
            return search(root, 0, prefix) != -1;
        }
        private int search(Node node, int index, String word) {
            if (node == null) return -1;
            if (index == word.length()) return node.val;
            char cur = word.charAt(index);
            return search(node.nextNodes[cur - 'a'], index+1, word);
        }
    }

    class UnionFind {
        HashMap<String, String> map;

        public UnionFind(String[] names) {
            map = new HashMap<>();
            for (String str : names) {
                int l = str.indexOf('(');
                int r = str.indexOf(')');
                String name = str.substring(0, l);
                String freq = str.substring(l+1, r);
                map.put(name, freq);
            }
        }

        public boolean isNum(String str) {
            //System.out.println("str:" + str);
            boolean result = Pattern.matches("0|([1-9][0-9]*)", str);
            //System.out.println("res:" + result);
            return result;
            //return Pattern.compile("0|([1-9][0-9]*)").matcher(str).matches();
        }

        public HashMap getMap() {
            return this.map;
        }
        public String find(String name) {
            if (!isNum(map.get(name))) {
                System.out.println("name:" + name);
                map.put(name, find(map.get(name)));
            } else return name;
            return map.get(name);
        }


        public void union(String name1, String name2) {
            if (!map.containsKey(name1)) map.put(name1, "0");
            if (!map.containsKey(name2)) map.put(name2, "0");
            String tmp1 = find(name1);
            String tmp2 = find(name2);
            if (tmp1.equals(tmp2)) return;
            int m = Integer.parseInt(map.get(tmp1));
            int n = Integer.parseInt(map.get(tmp2));
            if (tmp1.compareTo(tmp2) > 0) {
                map.put(tmp1, tmp2);
                map.put(tmp2, String.valueOf(m+n));
            }else {
                map.put(tmp2, tmp1);
                map.put(tmp1, String.valueOf(m+n));
            }
        }
    }
    public String[] trulyMostPopular(String[] names, String[] synonyms) {
        int len = names.length;
        if (len == 0) return new String[]{};

        UnionFind unionFind = new UnionFind(names);
        for (String s : synonyms) {
            int l = s.indexOf('(');
            int r = s.indexOf(')');
            int dot = s.indexOf(',');
            String name1 = s.substring(l+1, dot);
            String name2 = s.substring(dot+1, r);
            unionFind.union(name1, name2);
        }

        HashMap<String, String> map = unionFind.getMap();
        LinkedList<String> result = new LinkedList<>();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            String val = map.get(key);
            if (unionFind.isNum(val)) {
                String tmp = key + "(" + val + ")";
                result.addLast(tmp);
            }
        }

        return result.toArray(new String[]{});
    }

    public int findRotateSteps(String ring, String key) {
        if (ring == null || key == null) return 0;
        int rlen = ring.length();
        int klen = key.length();
        if (rlen == 0 || klen == 0) return 0;

        HashMap<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0;i < rlen;i++) {
            char c = ring.charAt(i);
            List<Integer> tmp = map.getOrDefault(c, new LinkedList<>());
            tmp.add(i);
            map.put(c, tmp);
        }

        return dfs(key, 0, 0, map, rlen, new int[rlen][klen]);
    }

    private int dfs(String key, int keyIndex, int ringIndex, HashMap<Character, List<Integer>> map, int length, int[][] record) {
        if (keyIndex == key.length()) {
            return 0;
        }
        if (record[ringIndex][keyIndex] != 0) return record[ringIndex][keyIndex];

        List<Integer> cur = map.get(key.charAt(keyIndex));
        int result = Integer.MAX_VALUE;
        for (Integer i : cur) {
            int sub = dfs(key, keyIndex+1, i, map, length, record);
            int tmp1 = Math.abs(ringIndex - i);
            int tmp = Math.min(tmp1, length - tmp1);
            result = Math.min(result, sub + tmp + 1);
        }
        record[ringIndex][keyIndex] = result;
        return result;
    }

    public int findNthDigit(int n) {
        if (n < 10) return n;

        int count = 9;
        int digits = 1;
        int result = 0;
        while (n >= 0) {
            long cur = (long) count * digits;
            if (n < cur) {
                int tmp1 = n / digits;
                int tmp2 = n % digits;
                StringBuilder builder = new StringBuilder();
                for (int i = 0;i < digits;i++) {
                    if (i == 0) builder.append('1');
                    else builder.append('0');
                }
                //System.out.println(builder.toString() + "tmp1: " + tmp1);
                if (tmp2 == 0) tmp1 -= 1;
                tmp1 += Integer.parseInt(builder.toString());

                builder = new StringBuilder(String.valueOf(tmp1));
                //System.out.println(builder.toString() + "tmp2: " + tmp2);
                if (tmp2 == 0) result = builder.charAt(builder.length()-1) - '0';
                else result = builder.charAt(tmp2-1) - '0';
                break;
            }else {
                n -= cur;
                count *= 10;
                digits++;
            }
        }

        return result;
    }

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        //System.out.println(Arrays.toString(arr1));
        int[] record = new int[1001];
        for (int num : arr1) {
            record[num]++;
        }

        int i = 0;
        for (int num : arr2) {
            while (record[num] > 0) {
                arr1[i] = num;
                i++;
                record[num]--;
            }
        }
        //System.out.println(Arrays.toString(arr1));
        for (int j = 0;j < 1001;j++) {
            while (record[j] > 0) {
                arr1[i] = j;
                i++;
                record[j]--;
            }
        }

        return arr1;
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new LinkedList<>();
        int length = candidates.length;
        if (length == 0 || target == 0) return result;
        Arrays.sort(candidates);
        dfs(candidates, 0, target, new LinkedList<>(), result);

        return result;
    }

    private void dfs(int[] candidates, int index, int target, LinkedList<Integer> cur, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new LinkedList(cur));
        }else {
            if (index == candidates.length || target < 0) return;
            for (int i = index, len = candidates.length;i < len;i++) {
                if (i == index || candidates[i] != candidates[i-1]) {
                    //System.out.println("cur:" + candidates[i]);
                    cur.add(candidates[i]);
                    dfs(candidates, i+1, target - candidates[i], cur, result);
                    cur.removeLast();
                }
            }
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        backTrack(nums, new LinkedList<>(), 0, result, new boolean[nums.length]);
        return result;
    }

    private void backTrack(int[] nums, LinkedList<Integer> list, int index, List<List<Integer>> result, boolean[] visited) {
        if (index == nums.length) {
            result.add(new LinkedList<>(list));
        }else {
            //以填数方式进行排列，不是交换
            //!visited[i-1]代表前一位刚好被撤销，实际上刚访问过
            for (int i = 0, len = nums.length;i < len;i++) {
                if (visited[i] || (i > 0 && nums[i] == nums[i-1] && !visited[i-1])) continue;
                list.add(nums[i]);
                visited[i] = true;
                backTrack(nums, list, index+1, result, visited);
                visited[i] = false;
                list.removeLast();
            }
        }
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int length = gas.length;
        int[] div = new int[length];
        for (int i = 0;i < length;i++) {
            div[i] = gas[i] - cost[i];
        }

        int sum = 0;
        for (int num : div) {
            sum += num;
        }
        if (sum < 0) return -1;
        sum = 0;
        for (int i = 0;i < length;i++) {
            if (div[i] < 0) continue;
            int j = i;
            sum = 0;
            do{
                sum += div[j];
                if (sum < 0) break;
                j++;
                if (j == length) j = 0;
            }while(j != i);
            if (j == i) return j;
            //i += j;
        }
        return -1;
    }

    public long tree4 (long n) {
        // write code here
        if (n == 1) return n;
        long mod = 998244353;
        long result = 1, count = 1, before = 1;
        int dep = 2;
        while (count < n) {
            long cur = before*2;
            long sum = 0;
            for (long i = count+1,len = Math.min(count+cur, n);i <= len;i++) {
                sum += i;
            }
            result = (result + ((sum * dep) % mod)) % mod;
            count += cur;
            before = cur;
            dep++;
        }
        return result % mod;
    }

    public String Maxsumforknumers (String x, int k) {
        // write code here
        int len = x.length();
        int[] nums = new int[len];
        for (int i = 0;i < len;i++) {
            nums[i] = x.charAt(i) - '0';
        }
        Arrays.sort(nums);
        long tmp = 0;
        for (int i = 0;i < k-1;i++) {
            tmp += nums[i];
        }
        if (k == len) return String.valueOf(tmp+nums[k-1]);
        String sum = new StringBuilder().append(tmp).reverse().toString();
        int plus = 0;
        for (int i = 0, length = sum.length();i < length;i++) {
            int cur = nums[i+k-1] + (sum.charAt(i) - '0') + plus;
            nums[i+k-1] = cur % 10;
            plus = cur / 10;
        }

        int length = sum.length() + k - 1;
        while (plus != 0 && length < len) {
            int cur = nums[length] + plus;
            nums[length] = cur % 10;
            plus = cur / 10;
            length++;
        }

        StringBuilder builder = new StringBuilder();
        if (plus != 0) builder.append(plus);
        for (int i = len-1;i >= k-1;i--) {
            builder.append(nums[i]);
        }

        return builder.toString();
    }

    public long tree2 (int k, int[] a) {
        // write code here
        //kn~kn+k
        long result = 0;
        int i = 0;
        int length = a.length;
        long tmp = k*i;
        while (tmp < length) {
            for (long j = tmp+1, len = tmp+k;j <= len && j < length;j++) {
                result += a[i] ^ a[(int)j];
            }
            tmp = k*(++i);
        }
        return result;
    }

    public int Answerforcn (long n) {
        // write code here
        long[] a = new long[(int) n];
        long[] b = new long[(int) n];
        long[] c = new long[(int) n];
        long mod = 1000000007;
        a[0] = 2;
        a[1] = 6;
        b[0] = 7;
        b[1] = 35;

        if (n <= 2) return (int)(a[(int) (n-1)]*b[(int) (n-1)]);
        for (long i = 0;i < n;i++) {
            if (i >= 3) {
                a[(int) i] = 2 * a[(int) (i-1)] + 3 * a[(int) (i-2)];
                b[(int) i] = 3 * a[(int) (i-1)] + 10 * b[(int) (i-2)];
            }
            c[(int) i] = a[(int) i] * b[(int) i] % mod;
        }
        return (int)c[(int) (n-1)];
    }

    public int reversePairs(int[] nums) {
        int length = nums.length;
        if (length <= 1) return 0;

        int result = 0;
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = length - 1;i >= 0;i--) {
            if (i == length - 1) {
                list.addLast(nums[i]);
                continue;
            }
            int cur = nums[i];
            for (int j = 0, len = list.size();j < len;j++) {
                if (j != 0 && list.get(j).equals(list.get(j - 1))) result++;
                else {
                    int tmp = list.get(j);
                    if (cur > (tmp << 1)) result++;
                    else break;
                }
            }
            //System.out.println("cur " + cur);
            if (cur >= list.getLast()) list.addLast(cur);
            else if (cur <= list.getFirst()) list.addFirst(cur);
            else {
                int index = Collections.binarySearch(list, cur);
                if (index < 0) index = - (index + 1);
                if (index == list.size()) --index;
                list.add(index, cur);
            }
        }

        return result;
    }

    public String reorganizeString(String S) {
        int length = S.length();
        if (length <= 1) return S;

        int[][] record = new int[26][2];
        for (char c : S.toCharArray()) {
            int index = c - 'a';
            record[index][0] = index;
            ++record[index][1];
        }

        Comparator<int[]> comp = (o1, o2) -> o1[1] - o2[1] != 0 ? o2[1] - o1[1] : o1[0] - o2[0];
        Arrays.sort(record, comp);

        StringBuilder builder = new StringBuilder();
        while (record[0][1] > 0 && record[1][1] > 0) {
            builder.append((char) (record[0][0] + 'a')).append((char) (record[1][0] + 'a'));
            record[0][1]--;
            record[1][1]--;
            Arrays.sort(record, comp);
        }
        if (record[0][1] > 1) return "";
        return record[0][1] == 0 ? builder.toString() : builder.append((char) (record[0][0] + 'a')).toString();
    }

    public int lengthOfLongestSubstring(String s) {
        int length = s.length();
        if (length <= 1) return length;

        int[] record = new int[128];
        Arrays.fill(record, -1);

        int result = 0;
        int left = 0, right = 0;
        while (right < length) {
            char c = s.charAt(right);
            if (record[(int) c] == -1) record[(int) c] = right;
            else {
                result = Math.max(result, right - left);
                while (left <= record[(int) c]) {
                    record[(int) s.charAt(left)] = -1;
                    left++;
                    //System.out.println("left:" + left);
                }
                record[(int) c] = right;
            }
            right++;
        }
        //System.out.println("left:" + left + "right:" + right);
        return Math.max(result, right - left);
    }

    public int countPrimes(int n) {
        if (n <= 1) return 0;

        int result = 0;
        boolean[] record = new boolean[n];

        for (int i = 2;i < n;i++) {
            if (!record[i]) {
                result++;
                int j = 1;
                int tmp;
                while ((tmp = i * j) < n) {
                    if (!record[tmp]) record[tmp] = true;
                    j++;
                }
            }
        }

        return result;
    }

    public List<Integer> splitIntoFibonacci(String S) {
        int length = S.length();
        List<Integer> result = new LinkedList<>();
        if (length <= 2) return result;

        char[] chars = S.toCharArray();
        int max = length / 3 + 1;
        long first = 0;
        long second = 0;
        for (int i = 1;i <= max;i++) {//第一个数的结束
            if (i != 1 && first == 0) continue;
            first = first * 10 + (chars[i-1] - '0');
            second = 0;
            if (first > Integer.MAX_VALUE) return result;
            for (int j = i+1, len = max*2;j <= len;j++) {//第二个数的结束
                if (j != i+1 && second == 0) continue;
                second = second * 10 + (chars[j-1] - '0');
                if (second > Integer.MAX_VALUE) break;

                // System.out.println("first:" + first);
                // System.out.println("second:" + second);
                long tmp1 = first;
                long tmp2 = second;
                int index = j;
                result.add((int) tmp1);
                result.add((int) tmp2);
                while (tmp1 + tmp2 <= Integer.MAX_VALUE && index <= length) {
                    long cur = tmp1 + tmp2;
                    String str = String.valueOf(cur);
                    int numSize = str.length();
                    if (index + numSize > length) break;
                    if (S.substring(index, index + numSize).equals(str)) {
                        tmp1 = tmp2;
                        tmp2 = cur;
                        index += numSize;
                        result.add((int) cur);
                    } else break;
                }

                if (index == length) return result;
                else result.clear();
            }
        }

        return result;
    }
}
