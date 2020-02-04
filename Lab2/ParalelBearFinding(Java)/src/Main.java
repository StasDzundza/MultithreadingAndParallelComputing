import java.util.Random;

public class Main {
    public static void main(String[]argv) throws InterruptedException {
        final int SIZE = 10000;
        int[][]bearField = new int[SIZE][SIZE];
        int bearRow = new Random().nextInt(SIZE);
        int bearColumn = new Random().nextInt(SIZE);
        bearField[bearRow][bearColumn] = 1;
        TasksCollection tasksCollection = new TasksCollection(bearField);
        BearFinder bearFinder = new BearFinder(tasksCollection);
        for(int i = 0; i < 3; i++){
            new Thread(bearFinder).start();
        }
        Thread.sleep(500);
    }
}
