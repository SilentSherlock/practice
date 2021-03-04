package problem;
import java.util.LinkedList;

/**
 * Date:2020/8/24
 * Description: optional class description
 **/
public class Solution {

    public void theDay(int[][] date) {
        LinkedList<Integer> bigMonths = new LinkedList<>();
        LinkedList<Integer> midMonths = new LinkedList<>();
        bigMonths.add(1);
        bigMonths.add(3);
        bigMonths.add(5);
        bigMonths.add(7);
        bigMonths.add(8);
        bigMonths.add(10);
        bigMonths.add(12);
        midMonths.add(4);
        midMonths.add(6);
        midMonths.add(9);
        midMonths.add(11);


        for (int i = 0;i < date.length;i++) {
            int second = 28;
            if ((date[i][0] % 100 == 0 && date[i][0] % 400 == 0) || (date[i][0] % 100 != 0 && date[i][0] % 4 == 0)) second = 29;
            int result = 0;
            for (int j = 1;j < date[i][1];j++) {
                if (bigMonths.contains(j)) result += 31;
                else if (midMonths.contains(j)) result += 30;
                else if (j == 2) result += second;
            }
            result += date[i][2];
            System.out.println(result);
        }
    }
}
