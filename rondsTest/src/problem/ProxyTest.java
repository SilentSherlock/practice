package problem;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Date:2020/11/5
 * Description: optional class description
 **/

interface Service {
    void send(String msg);
    void receive(String msg);
}

class ServiceImp implements Service {

    @Override
    public void send(String msg) {
        System.out.println("ServiceImp send " + msg);
    }

    @Override
    public void receive(String msg) {
        System.out.println("ServiceImp receive " + msg);
    }
}

//动态代理日志记录
class LogProxy implements InvocationHandler {

    //代理的对象
    private Object proxyObj;

    //绑定并返回对象代理的示例
    public Object bind(Object obj) {
        this.proxyObj = obj;
        return Proxy.newProxyInstance(proxyObj.getClass().getClassLoader(), proxyObj.getClass().getInterfaces(), this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("get into method" + method.getName());
        method.invoke(proxyObj, args);
        System.out.println(method.getName() + "method finished");
        return null;
    }
}
public class ProxyTest {
    public static void main(String[] args) {
        Service service = (Service) new LogProxy().bind(new ServiceImp());
        service.send("fUCK");
        service.receive("FUCKTOO");
    }
}
