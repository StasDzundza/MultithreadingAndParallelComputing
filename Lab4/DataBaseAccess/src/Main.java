import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[]args){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Writer w = new Writer(lock);
        Reader r = new Reader(lock);
        Scanner numIn = new Scanner(System.in);
        Scanner textIn = new Scanner(System.in);
        String info;
        while(true){
            System.out.println("1 - read, 2 - write, 3 - exit");
            numIn.reset();
            int n = numIn.nextInt();
            if(n == 1){
                String text = textIn.nextLine();
                r.setDBInfo(text,"DB.txt");
                executorService.submit(r);
            }else if(n == 2){
                String text = textIn.nextLine();
                w.setFindInfo(text,"DB.txt");
                executorService.submit(w);
            }else{
                break;
            }
        }
        executorService.shutdown();
    }
}
