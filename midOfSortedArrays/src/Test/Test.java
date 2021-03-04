package Test;

import learn.Coin;
import problems.Problem;
import problems.Solution;

import java.util.*;


/**
 * Date:2020.5.24
 * Description: optional class description
 **/
public class Test {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        Deque<Integer> deque = new LinkedList<>();
        Integer one = 1;
        String two = "2";
        output(one);
        output(two);
        Queue<Integer> queue = new LinkedList<>();
        List<String> strings = new LinkedList<>();
        strings.add("fuck");
        strings.add("shit");
    }
    private static void output(Object object) {
        if (object instanceof Integer) {
            System.out.println("this is a Integer");
        }else if (object instanceof String) {
            System.out.println("this is a String");
        }
    }
}
