import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Barbershop {
    private static Semaphore semaphore;
    private static Queue<Client> clients;

    static {
        semaphore = new Semaphore(1);
        clients = new LinkedList<>();
    }

    public static void stayInQueue(Client client) throws InterruptedException {
        synchronized (clients){
            clients.offer(client);
            System.out.println(Thread.currentThread().getName() + " stayed in queue");
        }
        Barbershop.makeHaircut();
    }

    public static void makeHaircut() throws InterruptedException {
        semaphore.acquire();
        System.out.println(Thread.currentThread().getName() + " is servicing");
        Thread.sleep(100);
        clients.remove();
        System.out.println(Thread.currentThread().getName() + " has been serviced");
        semaphore.release();
    }
}
