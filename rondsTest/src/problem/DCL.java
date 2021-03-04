package problem;

/**
 * Date:2020/12/1
 * Description: DCL单例模式，双重锁定检查，懒加载，在使用时进行加载，也可以用static修饰单例，创建时就进行加载，即饿汉式
 **/
class Singleton {
    private volatile Singleton singleton;

    public Singleton getSingleton() {
        if (null == singleton) {
            synchronized (Singleton.class) {
                if (null == singleton) singleton = new Singleton();
            }
        }
        return singleton;
    }

    public Singleton() {
        System.out.println("单例创建");
    }

    public void use() {
        System.out.println("单例使用");
    }
}
public class DCL {


}


