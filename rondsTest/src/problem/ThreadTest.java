package problem;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

/**
 * Date:2020/11/2
 * Description: optional class description
 **/
class Producer implements Runnable {
    private BufferZone bufferZone;
    public Producer(BufferZone bufferZone) {
        this.bufferZone = bufferZone;
    }
    @Override
    public void run() {
        try {
            bufferZone.put(Thread.currentThread().getName(), new Random().nextInt());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class Consumer implements Runnable {
    private BufferZone bufferZone;
    public Consumer(BufferZone bufferZone) {
        this.bufferZone = bufferZone;
    }
    @Override
    public void run() {
        try {
            int tmp = bufferZone.get(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
//缓冲区，存放生产者生成的物品，提供消费者使用
class BufferZone {
    private final static int MAX_SIZE = 1000;//缓冲区最大长度
    private final static int DEFAULT_SIZE = 10;//缓冲区默认长度
    private static int size;//缓冲区长度
    private Queue<Integer> products;

    public BufferZone() {
        products = new ArrayDeque<>(DEFAULT_SIZE);
        size = DEFAULT_SIZE;
    }
    public BufferZone(int size) {
        if (size < MAX_SIZE) products = new ArrayDeque<>(size);
        else products = new ArrayDeque<>(MAX_SIZE);
        BufferZone.size = Math.min(size, MAX_SIZE);
    }

    public synchronized void put(String name, int productId) throws InterruptedException {
        while (products.size() == size) wait();
        notifyAll();
        products.add(productId);
        System.out.println("Producer:" + name + " put " + productId + " zone size: " + products.size());
    }

    public synchronized int get(String name) throws InterruptedException {
        while (products.size() == 0) wait();
        notifyAll();
        int tmp = products.poll();
        System.out.println("Consumer:" + name + " get " + tmp + " zone size: " + products.size());
        return tmp;
    }

}
public class ThreadTest {

    public static void main(String[] args) {
        BufferZone bufferZone = new BufferZone(1);
        for (int i = 0;i < 100;i++) {
            Thread thread = new Thread(new Producer(bufferZone), "p" + i);
            thread.start();
            //thread.join();
        }
        for (int i = 0;i < 100;i++) {
            Thread thread = new Thread(new Consumer(bufferZone), "c" + i);
            thread.start();
        }
//        Thread client = new Thread(new Client(new byte[]{0,0,0,0}, 27149), "client");
//        client.start();
    }
}
