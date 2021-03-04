package solution;

public class Solution {

    private static final long INT_MIN = -2147483648;
    private static final long INT_MAX = -INT_MIN - 1;

    public int solute(String str) {

        int index = find(str);
        if (index != -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str.charAt(index));
            System.out.println(str.charAt(index));
            System.out.println("the index is " + index + " " + stringBuilder.toString());
            for (int i = index + 1; i < str.length(); i++) {
                char current = str.charAt(i);
                if (current >= '0' && current <= '9') {
                    stringBuilder.append(current);
                    System.out.println(stringBuilder.toString());
                }else break;
            }

            String strResult = stringBuilder.toString();
            if (strResult.length() > 11) {
                if ((strResult.charAt(0) == '+' || strResult.charAt(0) == '-') && strResult.charAt(1) != '0'){
                    return strResult.charAt(0) == '-' ? (int)INT_MIN : (int)INT_MAX;
                }else if (strResult.charAt(0) != '+' && strResult.charAt(0) != '-' && strResult.charAt(0) != '0') {
                    return strResult.charAt(0) == '-' ? (int)INT_MIN : (int)INT_MAX;
                }

            }
            long result = Long.parseLong(strResult);
            if (result >= 0) return result > INT_MAX ? (int)INT_MAX : (int) result;
            else return result < INT_MIN ? (int)INT_MIN : (int) result;
        }
        return 0;
    }
    /*找到字符串中第一个有意义的字符，并返回下标，没有返回-1*/
    private int find(String str) {

        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            if (current != ' ') {
                if (current == '+' && i != str.length() - 1 || current == '-' && i != str.length() - 1 ||
                        current >= '0' && current <= '9') {//符号如果是最后一位没有意义
                    if (current == '+' || current == '-') {//是符号再检查一位
                        char next = str.charAt(i+1);
                        if (next < '0' || next > '9') {//不是数字，无意义
                            return -1;
                        }
                    }
                    return i;
                }
                return -1;
            }
        }
        return -1;
    }

    public int reverse(int x) {

        String str = String.valueOf(x);
        if (str.equals("0") || str.equals("-0")) return 0;
        int end = str.length()-1;
        for(;end > 0;end--) {
            if(str.charAt(end) != 0) break;
        }

        int result = 0;
        for(int i = end;i >= 0;i--) {
            char current = str.charAt(i);
            if(current != '-') {
                int tmp = Integer.parseInt(String.valueOf(current));
                if((Integer.MAX_VALUE - tmp)/10 < result) return 0;
                result = result * 10 + tmp;
            }else return -result;
        }
        return result;
    }
}
