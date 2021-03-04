package problem;

import java.time.Instant;
import java.util.concurrent.*;

/**
 * Date:2020/11/2
 * Description: optional class description
 **/
class myThread implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CurrentThread:" + Thread.currentThread().getName() + " Date:" + Instant.now());
    }
}
class myThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
public class ThreadPoolTest {
    public static void main(String[] args) {
        //ExecutorService service = Executors.newFixedThreadPool(100);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                30,
                400,
                20000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(),
                new myThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        for (int i = 0;i < 100;i++) {
            executor.execute(new myThread());
        }
        executor.shutdown();
        //service.shutdown();
    }
}
