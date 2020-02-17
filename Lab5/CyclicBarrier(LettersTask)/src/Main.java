import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class StringChanger implements Runnable{
    StringBuilder stringBuilder;
    CyclicBarrier cyclicBarrier;
    public StringChanger(StringBuilder stringBuilder,CyclicBarrier cyclicBarrier){
        this.stringBuilder = stringBuilder;
        this.cyclicBarrier = cyclicBarrier;
    }

    public void changeLetters(){
        Random random = new Random();
        int letterIndex = random.nextInt(4);
        char letter = stringBuilder.charAt(letterIndex);
        switch (letter){
            case 'A':
                stringBuilder.setCharAt(letterIndex,'C');
                System.out.println(String.format("Thread %s changed letter %c to %c",Thread.currentThread().getName(),'A', 'C'));
                break;
            case 'B':
                stringBuilder.setCharAt(letterIndex,'D');
                System.out.println(String.format("Thread %s changed letter %c to %c",Thread.currentThread().getName(),'B', 'D'));
                break;
            case 'C':
                stringBuilder.setCharAt(letterIndex,'A');
                System.out.println(String.format("Thread %s changed letter %c to %c",Thread.currentThread().getName(),'C', 'A'));
                break;
            case 'D':
                stringBuilder.setCharAt(letterIndex,'B');
                System.out.println(String.format("Thread %s changed letter %c to %c",Thread.currentThread().getName(),'D', 'B'));
                break;
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        changeLetters();
    }
}

class Checker implements Runnable{

    private ArrayList<StringBuilder> strings;
    private Boolean condition;
    public Checker(ArrayList<StringBuilder>strings,Boolean condition){
        this.strings = strings;
        this.condition = condition;
    }

    @Override
    public void run() {
        int[]arr = new int[4];
        int i = 0;
        for (StringBuilder s : strings) {
            for (int k = 0; k < s.length(); k++) {
                if(s.charAt(k) == 'A' || s.charAt(k) == 'B'){
                    arr[i]++;
                }
            }
            i++;
        }

        boolean cond1 = (arr[0]==arr[1] &&arr[0]==arr[2]);
        boolean cond2 = (arr[0]==arr[2] &&arr[0]==arr[3]);
        boolean cond3 = (arr[0]==arr[1] &&arr[0]==arr[3]);
        boolean cond4 = (arr[1]==arr[2] &&arr[2]==arr[3]);

        if(cond1||cond2||cond3||cond4){
            System.out.println("Finish");
            condition = true;
        }
    }
}
public class Main {
    static volatile Boolean condition = false;
    public static void main(String[]args) throws InterruptedException {
        StringBuilder s1 = new StringBuilder("ABCD");
        StringBuilder s2 = new StringBuilder("CBCC");
        StringBuilder s3 = new StringBuilder("ACCD");
        StringBuilder s4 = new StringBuilder("ACBB");
        ArrayList<StringBuilder>list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4,new Checker(list,condition));

        while (condition.booleanValue() == false){
            new Thread(new StringChanger(s1,cyclicBarrier)).start();
            new Thread(new StringChanger(s2,cyclicBarrier)).start();
            new Thread(new StringChanger(s3,cyclicBarrier)).start();
            new Thread(new StringChanger(s4,cyclicBarrier)).start();
            Thread.sleep(500);
        }
    }
}
