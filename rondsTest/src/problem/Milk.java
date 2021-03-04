package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Date:2020/9/3
 * Description: optional class description
 **/
public class Milk {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testDatas = scanner.nextInt();
        for (int i = 0;i < testDatas;i++) {
            int num = scanner.nextInt();//奶牛数
            int props = scanner.nextInt();//特性数
            int[] milks = new int[num+1];
            for (int j = 0;j < props;j++) {
                int sets = scanner.nextInt();
                for (int k = 0;k < sets;k++) {
                    int first = scanner.nextInt();
                    int end = scanner.nextInt();
                    for (int x = first;x <= end;x++) {
                        milks[x]++;
                    }
                }
            }
            int count = 0;
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int j = 0;j < milks.length;j++) {
                if (milks[j] == props) {
                    count++;
                    arrayList.add(j);
                }
            }
            System.out.println(count);
            System.out.println(Arrays.toString(arrayList.toArray()));
        }
    }
}
