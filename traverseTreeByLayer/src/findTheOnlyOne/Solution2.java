package findTheOnlyOne;

import Solution.TreeNode;

import java.util.HashMap;
import java.util.Stack;

/**
 * leetcode problem xxx 105 14 76 20
 * */
public class Solution2 {

    public int singleNumber(int[] nums) {
        HashMap<Integer, Integer> table = new HashMap<>();

        for (int num : nums) {
            if (!table.containsKey(num)) table.put(num, 1);
            else table.remove(num);
        }
        int result = 0;
        for (int key : table.keySet()) {
            if (table.get(key) == 1) result = key;
        }
        return result;
    }
    //解法2，利用的是异或运算，leetcode题解，实在是厉害，难以想到，任何数和0异或都不变，和本身异或都是0
    //且异或满足交换律和结合律，那找到这个只出现一次数，就可以对将数组的数进行异或，最后的结果就是只出现
    //了一次的那个，出现的两次的都被异或为0
    public int singleNumber2(int[] nums) {
        int single  = 0;
        for (int num : nums) {
            single = single ^ num;
        }
        return single;
    }

    //105，根据前序遍历和中序遍历，建立二叉树
    //程序的耗时在于每次查找根节点，可以改用hashmap
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }
    private TreeNode build(int[] pre, int preB, int preE, int[] in, int inB, int inE) {

        if (preE < preB) return null;
        int i;
        for (i = inB;i <= inE;i++) {
            if (pre[preB] == in[i]) break;
        }
        TreeNode root = new TreeNode(pre[preB]);
        root.left = build(pre, preB+1, preB+i-inB, in, inB, i-1);
        root.right = build(pre, preB+i-inB+1, preE, in, i+1, inE);//preB的计算易错
        return root;
    }

    //14
    //字符串数组的最长公共前缀，方法是垂直扫描字符串数组
    public String longestCommonPrefix(String[] strs) {
        int count = strs.length;

        if (count == 0) return "";
        if (count == 1) return strs[0];

        int minLength = strs[0].length();
        for(String str : strs) {
            if (minLength > str.length()) minLength = str.length();
        }

        int lastIndex, i;
        for (i = 0;i < minLength;i++) {
            char num = '0';
            if (count % 2 == 0) {
                for (String str : strs) {
                    num ^= str.charAt(i);
                }
                if (num != '0') {
                    break;
                }
            }else {
                for (int j = 0;j < count - 1;j++) {
                    num ^= strs[j].charAt(i);
                }
                if (num != '0' || (num == '0' && strs[count-1].charAt(i) != strs[count-2].charAt(i))) {
                    break;
                }
            }
            System.out.println(i);
        }
        lastIndex = i;
        return strs[0].substring(0, lastIndex);
    }

    //76
    //暴力法，会超时
    //在字符串s中找到包含t中所有字符的最小子串，包含字符即可，顺序不限
    public String minWindow(String s, String t) {

        if (t.equals("") || s.equals("")) return "";

        HashMap<Character, Integer> tMap = new HashMap<>();
        for (int i = 0;i < t.length();i++) {
            if (!tMap.containsKey(t.charAt(i))) {
                tMap.put(t.charAt(i), 1);
            }else {
                int count = tMap.get(t.charAt(i));
                count++;
                tMap.put(t.charAt(i), count);
            }
        }
        int begin = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            HashMap<Character, Integer> tmpMap = new HashMap<>();
            for (int j = i;j >= 0;j--) {
                if (!tmpMap.containsKey(s.charAt(j))) {
                    tmpMap.put(s.charAt(j), 1);
                }else {
                    int count = tmpMap.get(s.charAt(j));
                    count++;
                    tmpMap.put(s.charAt(j), count);
                }
                boolean equalFlag = false;
                for (Character key : tMap.keySet()) {
                    if (tmpMap.containsKey(key) && tmpMap.get(key) >= tMap.get(key)) {
                        equalFlag = true;
                    }else {
                        equalFlag = false;
                        break;
                    }
                }
                if (equalFlag) {
                    if ((begin == 0 && end == 0) || i+1-j < end-begin) {
                        begin = j;
                        end = i + 1;
                    }
                    break;
                }
            }
        }
        return s.substring(begin, end);
    }

    //使用滑动窗口
    //双指针，不包含t中所有字符则右指针向右移一位，包含则左指针向右移一位
    public String minWindow2(String s, String t) {
        if (t.equals("") || s.equals("")) return "";

        HashMap<Character, Integer> tMap = new HashMap<>();
        for (int i = 0;i < t.length();i++) {
            if (!tMap.containsKey(t.charAt(i))) {
                tMap.put(t.charAt(i), 1);
            }else {
                int count = tMap.get(t.charAt(i));
                count++;
                tMap.put(t.charAt(i), count);
            }
        }
        int begin = 0, end = 1;
        int resultB = 0, resultE = 0;
        HashMap<Character, Integer> tmpMap = new HashMap<>();
        tmpMap.put(s.charAt(begin), 1);

        while (end <= s.length()) {
            if (isContain(tmpMap, tMap)) {
                if ((resultB == 0 && resultE == 0) || end - begin < resultE - resultB) {
                    resultB = begin;
                    resultE = end;
                }
                int count = tmpMap.get(s.charAt(begin));
                count--;
                tmpMap.put(s.charAt(begin), count);
                begin++;
            }else {
                if (end != s.length()) {
                    if (!tmpMap.containsKey(s.charAt(end))) {
                        tmpMap.put(s.charAt(end), 1);
                    }else {
                        int count = tmpMap.get(s.charAt(end));
                        count++;
                        tmpMap.put(s.charAt(end), count);
                    }
                }
                end++;
            }
        }
        return s.substring(resultB, resultE);
    }

    private boolean isContain(HashMap<Character, Integer> current, HashMap<Character, Integer> goal) {
        for (Character key : goal.keySet()) {
            if (!current.containsKey(key) || current.get(key) < goal.get(key)) return false;
        }
        return true;
    }

    //20，括号匹配
    public boolean isValid(String s) {
        int length = s.length();
        if (length%2 == 1) return false;
        if (length == 0) return true;

        Stack<Character> stack = new Stack<>();
        for (int i = 0;i < length;i++) {
            Character current = s.charAt(i);
            switch(current) {
                case '(':
                case '{':
                case '[':
                    stack.push(current);
                    break;
                case ')':
                    if (!stack.empty() && stack.peek() == '(') stack.pop();
                    else return false;
                    break;
                case '}':
                    if (!stack.empty() && stack.peek() == '{') stack.pop();
                    else return false;
                    break;
                case ']':
                    if (!stack.empty() && stack.peek() == '[') stack.pop();
                    else return false;
                    break;
            }
        }
        return stack.empty();
    }
}
