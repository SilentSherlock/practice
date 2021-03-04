package problem;

import java.util.*;

/**
 * Date:2020/8/29
 * Description: optional class description
 **/
class A {
    public A(int x) {
        System.out.println(x);
    }
    protected void fuck() {
        System.out.println("fuck A");
    }
    public A() {
        this(2);
        System.out.println("class A");
    }

    {
        System.out.println("In a class");
    }
    static {
        System.out.println("in a class static");
    }
}

class B extends A {
    public B() {
        System.out.println("class B");
    }

    @Override
    public void fuck() {
        super.fuck();
    }

    {
        System.out.println("In b class");
    }
    static {
        System.out.println("in b class static");
    }

    public static void main(String[] args) {
        //new B();
        Integer o1 = new Integer(47);
        Integer o2 = new Integer(47);
        System.out.println(o1 == o2);
        System.out.println(o1 != o2);
    }

}
