package problem;

import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Date:2020/9/3
 * Description: optional class description
 **/
public class Baidu {

    public static void main(String[] args) {
        /*Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[2];
        for (int i = 0;i < n;i++) {
            int current = scanner.nextInt();
            if (current == 0) nums[0]++;
            else if (current == 5) nums[1]++;
        }
        Baidu baidu = new Baidu();
        int a = 3 > 2 ? 1 : -1 + 4;*/
        String pattern = "\\{\\([A-Z]+->[A-Z]+\\)(,\\s*\\([A-Z]+->[A-Z]+\\))*}";
        String str = "{(A->B),(B->C)}";
        boolean isMatch = Pattern.matches(pattern, str);

        System.out.println(isMatch);

    }

    //nums只有两位
    //nums[0]代表0的个数
    //num[1]代表5的个数
    public long getFive(int[] nums) {
        if (nums[1] < 9 || nums[0] == 0) return -1;
        int fiveCount = nums[1] / 9;
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i < fiveCount;i++) {
            builder.append("999999999");
        }
        builder.append("0");
        return Long.parseLong(builder.toString());
    }

}
