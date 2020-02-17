import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Army implements Runnable{

    private int[]army;
    private int leftIndex,rightIndex;
    private CyclicBarrier cyclicBarrier;
    public Army(CyclicBarrier cyclicBarrier,int[]army,int leftIndex, int rightIndex){
        this.army = army;
        this.cyclicBarrier = cyclicBarrier;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    public void makeTurn(){
        Random r = new Random();
        for(int i = leftIndex; i < rightIndex; i++){
            int turnSide = r.nextInt(2);
            if(turnSide == 0){//left turn
                army[i] = -1;
                if(i-1>=leftIndex && army[i-1]==1){
                    army[i-1]=-1;
                    army[i]=1;
                }
            }else {
                army[i] = 1;
                if(i+1<rightIndex && army[i+1]==-1){
                    army[i+1]=1;
                    army[i]=-1;
                }
            }
        }
    }

    @Override
    public void run() {
        makeTurn();
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class Commander implements Runnable{
    private int[]army;
    public Commander(int[]army){
        this.army = army;
    }

    public void checkPositionsOfSoldiers(){
        for (int i = 0; i < army.length-1; i++){
            if(army[i]!=army[i+1]){
                System.out.println("Bad turn");
                return;
            }
        }
        System.out.println("Good turn!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void run() {
        checkPositionsOfSoldiers();
    }
}
public class Main {
    public static void main(String[]args) throws InterruptedException {
        final int size = 100;
        int []arr = new int[100];

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new Commander(arr));
        while(true){
            for(int i = 0; i < size; i+=50){
                new Thread(new Army(cyclicBarrier,arr,i,i+50)).start();
            }
            Thread.sleep(100);
        }
    }
}
