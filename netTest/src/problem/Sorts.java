package problem;

import javax.script.AbstractScriptEngine;

/**
 * Date:2020/8/27
 * Description: optional class description
 **/
public class Sorts {

    /*选择排序,升序*/
    public int[] selectSort(int[] array) {
        for (int i = 0; i < array.length-1; i++) {
            int minIndex = i;
            for (int j = minIndex; j < array.length; j++) {
                minIndex = array[j] < array[minIndex] ? j : minIndex;
            }
            int tmp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = tmp;
        }

        return array;
    }

    /*插入排序，升序*/
    public int[] insertSort(int[] array) {
        for (int i = 1;i < array.length;i++) {
            int current = array[i];
            if (current > array[i-1]) continue;
            int j = i - 1;
            for (;j >= 0;j--) {
                if (array[j] > current) {
                    array[j+1] = array[j];
                }else {
                    array[j+1] = current;
                    break;
                }
            }
            if (j == -1) array[0] = current;
        }
        return array;
    }

    /*归并排序，升序*/
    public int[] mergeSort(int[] array, int begin, int end) {
        int length = end - begin + 1;
        if (length == 1) return new int[]{array[begin]};
        int middle = begin + ((end-begin) >> 1);

        int[] left = mergeSort(array, begin, middle);
        int[] right = mergeSort(array, middle+1, end);
        return merge(left, right);
    }

    private int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length+right.length];
        int i = 0, j = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[i+j] = left[i];
                i++;
            }else {
                result[i+j] = right[j];
                j++;
            }
        }
        if (i == left.length) {
            while (j < right.length) {
                result[i+j] = right[j];
                j++;
            }
        }else if (j == right.length) {
            while (i < left.length) {
                result[i+j] = left[i];
                i++;
            }
        }

        return result;
    }

    /*快速排序，升序*/
    public void quickSort(int[] array, int begin, int end) {
        if (begin >= end) return;
        int storeFlag = begin + 1;

        for (int i = begin + 1; i <= end; i++) {
            if (array[i] < array[storeFlag]) {
                int tmp = array[i];
                array[i] = array[storeFlag];
                array[storeFlag] = tmp;
                storeFlag++;
            }
        }
        int tmp = array[storeFlag-1];
        array[storeFlag-1] = array[begin];
        array[begin] = tmp;

        quickSort(array, begin, storeFlag-1 );
        quickSort(array, storeFlag, end);
    }

    public void test(int[] array) {
        int tmp= array[array.length-1];
        array[array.length-1] = array[0];
        array[0] = tmp;
    }
}
