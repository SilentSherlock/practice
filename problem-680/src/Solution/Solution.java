package Solution;

import java.util.Arrays;

/**
 * Date:2020.5.19
 * Description: leetcode problem 680, 1371, 5
 **/
public class Solution {
    //680
    //使用的其实是双指针
    public boolean validPalindrome(String s) {

        int len = s.length();
        if (len == 0|| len > 50000) return false;
        if (len == 1 || len == 2) return true;
        String reverseStr = reverse(s);
        if (reverseStr.equals(s)) return true;

        int half = s.length()/2, neq = 0;
        for (; neq <= half; neq++) if (s.charAt(neq) != reverseStr.charAt(neq)) break;
        if (neq == half) return true;
        for (int i = neq;i < half;i++) {
            if (s.charAt(i) != reverseStr.charAt(i+1)) break;
            if (i == half - 1) return true;
        }
        for (int i = neq;i < half;i++) {
            if (s.charAt(i+1) != reverseStr.charAt(i)) return false;
        }
        return true;
    }
    private String reverse(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = str.length()-1; i >= 0; i--) {
            stringBuilder.append(str.charAt(i));
        }
        return stringBuilder.toString();
    }

    //更为简洁的双指针
    public boolean validPalindrome2(String s) {
        int low = 0, high = s.length() - 1;
        while (low < high) {
            char c1 = s.charAt(low), c2 = s.charAt(high);
            if (c1 == c2) {
                low++;
                high--;
            } else {
                boolean flag1 = true, flag2 = true;
                for (int i = low, j = high - 1; i < j; i++, j--) {
                    char c3 = s.charAt(i), c4 = s.charAt(j);
                    if (c3 != c4) {
                        flag1 = false;
                        break;
                    }
                }
                for (int i = low + 1, j = high; i < j; i++, j--) {
                    char c3 = s.charAt(i), c4 = s.charAt(j);
                    if (c3 != c4) {
                        flag2 = false;
                        break;
                    }
                }
                return flag1 || flag2;
            }
        }
        return true;
    }

    //1371
    //暴力法,会超时
    public int findTheLongestSubstring(String s) {
        int maxLength = 0;
        if (s.length() < 1 || s.length() > 500000) return 0;
        for (int i = 0; i < s.length(); i++) {
            int aCount = 0, eCount = 0, iCount = 0, oCount = 0, uCount = 0;
            for (int j = i; j >=0 ; j--) {
                char current = s.charAt(j);
                switch (current) {
                    case 'a':
                        aCount++;
                        break;
                    case 'e':
                        eCount++;
                        break;
                    case 'i':
                        iCount++;
                        break;
                    case 'o':
                        oCount++;
                        break;
                    case 'u':
                        uCount++;
                        break;
                    default:
                        break;
                }
                if (aCount%2 == 0 && eCount%2 == 0 && iCount%2 == 0 && oCount%2 == 0 && uCount%2 == 0 &&
                        i - j + 1 > maxLength) {
                    maxLength = i - j + 1;
                }
            }
        }
        return maxLength;
    }
    //官方优美题解
    public int findTheLongestSubstring2(String s) {
        int n = s.length();
        int[] pos = new int[1 << 5];
        Arrays.fill(pos, -1);
        int ans = 0, status = 0;
        pos[0] = 0;
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch == 'a') {
                status ^= (1 << 0);
            } else if (ch == 'e') {
                status ^= (1 << 1);
            } else if (ch == 'i') {
                status ^= (1 << 2);
            } else if (ch == 'o') {
                status ^= (1 << 3);
            } else if (ch == 'u') {
                status ^= (1 << 4);
            }
            if (pos[status] >= 0) {
                ans = Math.max(ans, i + 1 - pos[status]);
            } else {
                pos[status] = i + 1;
            }
        }
        return ans;
    }

    //5,找到字符串中最长回文子串
    //暴力法，列举所有子串，检验是否是回文，会超时
    public String longestPalindrome(String s) {
        int strLength = s.length();
        if (strLength == 1 || s.equals(reverse(s))) return s;
        String result = "";
        for (int i = 0;i < strLength;i++) {
            for (int j = i;j >= 0;j--) {
                if (s.charAt(i) != s.charAt(j)) continue;
                String current = s.substring(j, i+1);
                if (current.equals(reverse(current)) && current.length() > result.length())
                    result = current;
            }
        }
        return result;
    }

    //递归方法，超时，草
    public String longestPalindrome2(String s) {
        int strLength = s.length();
        if (strLength == 1 || strLength == 0) return s;
        int low = 0, high = strLength-1;
        while (low < high) {
            if (s.charAt(low) == s.charAt(high)) {
                low++;
                high--;
            }else break;
        }
        if (s.charAt(low) == s.charAt(high)) return s;
        System.out.println(s);
        String left = longestPalindrome2(s.substring(0, strLength-1));
        String right = longestPalindrome2(s.substring(1, strLength));
        return left.length() > right.length()?left:right;
    }

    //方法3，动态规划，通过，str(first,end)是回文的条件是str(first+1, end-1)是回文，且str[first] = str[end-1]
    //方法的重点在于如何建立第一个回文串，第一个可能是aba式，也可能是baab式，其实就是奇数还是偶数，采用的方法是每个都建立一遍，最后取长的那个
    //这里的区间下标都是包括左，不包括右
    //通过后查看官方题解，此方法原来叫中心扩展法
    public String longestPalindrome3(String s) {
        int strLength = s.length();
        if (strLength == 0 || strLength == 1) return s;
        String result = String.valueOf(s.charAt(0));
        int low = 0, high = strLength - 1;

        while (low < high) {
            if (s.charAt(low) == s.charAt(high)) {
                low++;
                high--;
            }else break;
        }
        if (s.charAt(low) == s.charAt(high)) return s;
        for (int i = 0;i < strLength;i++) {
            low = high = i;
            while (low >= 0 && high < strLength) {
                if (s.charAt(low) == s.charAt(high)) {
                    if (high - low + 1 > result.length()) {
                        result = s.substring(low, high+1);
                    }
                    low--;
                    high++;
                }else break;
            }
            low = high = i;
            if (i != 0) low = i-1;
            while (low >= 0 && high < strLength) {
                if (s.charAt(low) == s.charAt(high)) {
                    if (high - low + 1 > result.length()) {
                        result = s.substring(low, high+1);
                    }
                    low--;
                    high++;
                }else break;
            }
        }
        return result;
    }
}
