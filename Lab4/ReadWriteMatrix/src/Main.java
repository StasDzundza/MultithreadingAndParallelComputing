import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[]args) throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Garden garden = new Garden(10,10);
        Gardener gardener = new Gardener(garden,lock);
        Weather weather = new Weather(garden,lock);
        Viewer viewer = new Viewer(garden,lock);

        Thread t1 = new Thread(gardener);
        Thread t2 = new Thread(weather);
        Thread t3 = new Thread(viewer);
        t1.setDaemon(true);
        t2.setDaemon(true);
        t3.setDaemon(true);
        t1.start();t2.start();t3.start();
        t1.join();t2.join();t3.join();
    }
}
