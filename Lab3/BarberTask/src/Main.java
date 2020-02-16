
public class Main {
    public static void main(String[]args) throws InterruptedException {
        Thread[]threads = new Thread[10];
        for(int i = 0; i < 10; i++){
            threads[i] = new Thread(new Client(),Integer.toString(i));
            threads[i].start();
        }

        for(Thread t:threads){
            t.join();
        }
    }
}
