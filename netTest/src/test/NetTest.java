package test;

import problem.Sorts;

import java.util.Arrays;
import java.util.List;

/**
 * Date:2020/8/24
 * Description: optional class description
 **/
public class NetTest {

    public static void main(String[] args) {
        int[] array = {3, 1, 8, 9, 2, 3, 0, 7, 232, 111, 334343, 117147};
        Sorts sorts = new Sorts();
        //System.out.println("Select:" + Arrays.toString(sorts.selectSort(array)));
        //sorts.insertSort(array);
        //System.out.println("Insert:" + Arrays.toString(sorts.insertSort(array)));
        //sorts.mergeSort(array, 0, array.length-1);
        //System.out.println("Merge:" + Arrays.toString(sorts.mergeSort(array, 0, array.length-1)));
        //sorts.quickSort(array, 0 , array.length-1);
        sorts.quickSort(array, 0, array.length-1);
        System.out.println("Quick:" + Arrays.toString(array));
        /*sorts.test(array);
        System.out.println(Arrays.toString(array));*/
        String s = new String();
        s.split("\\s");//
    }
}
