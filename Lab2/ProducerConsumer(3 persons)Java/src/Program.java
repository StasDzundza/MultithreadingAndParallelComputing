import java.util.LinkedList;
import java.util.Queue;

public class Program {

    public static void main(String[] args) throws InterruptedException {

        Product product = new Product();
        Ivanov ivanov = new Ivanov(product);
        Petrov petrov = new Petrov(product);
        Necheporuk necheporuk = new Necheporuk(product);
        Thread t1 = new Thread(ivanov,"1");
        Thread t2 = new Thread(petrov,"2");
        Thread t3 = new Thread(necheporuk,"3");
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
    }
}

class Product{
    public Queue<Integer>queue1 = new LinkedList<Integer>();
    public Queue<Integer>queue2 = new LinkedList<Integer>();
}

class Ivanov implements Runnable{

    Product product;
    Ivanov(Product product){
        this.product=product;
    }
    public void run() {
        while (true) {
            synchronized (product.queue1) {
                while (product.queue1.size() >= 1) {
                    try {
                        product.queue1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                product.queue1.offer(1);
                System.out.println("Іванов взяв товар з машини");
                product.queue1.notify();
            }
        }
    }
}

class Petrov implements Runnable{

    Product product;
    Petrov(Product product){
        this.product=product;
    }
    public void run() {
        while (true) {
            synchronized (product.queue1) {
                while (product.queue1.size() == 0) {
                    try {
                        product.queue1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                product.queue1.poll();
                System.out.println("Петров взяв товар у Іванова");
                product.queue1.notify();
            }
            synchronized (product.queue2) {
                while (product.queue2.size() >= 1) {
                    try {
                        product.queue2.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                product.queue2.offer(1);
                System.out.println("Петров передав товар товар Нечепоруку");
                product.queue2.notify();
            }
        }
    }

}

class Necheporuk implements Runnable{
    Product product;
    Necheporuk(Product product){
        this.product=product;
    }
    public void run() {
        while (true) {
            synchronized (product.queue2) {
                while (product.queue2.size() == 0) {
                    try {
                        product.queue2.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                product.queue2.poll();
                System.out.println("Нечепорук взяв товар у Петрова");
                product.queue2.notify();
            }
        }
    }
}