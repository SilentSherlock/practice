import java.util.HashMap;
import java.util.Map;

public class Solution {

    public static void main(String[] args) {

    }
    private static int solute(String s){//滑动窗口解决
        int result = 0;
        Map<Character, Integer> record = new HashMap<>();
        int begin = 0, end = 0;
        for (;end < s.length();end++){
            Character cEnd = s.charAt(end);
            if (record.containsKey(cEnd)){//包含重复字符
                begin = Math.max(begin, record.get(cEnd));//将滑动窗口的前端重新置位
            }
            result = Math.max(result, end-begin+1);
            record.put(s.charAt(end), end+1);
        }
        return result;
    }
}
