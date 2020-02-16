import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Garden {
    public int [][] matrix;

    public Garden(int rows,int columns){
        matrix = new int[rows][columns];
    }
}

class Gardener implements Runnable{
    private Garden garden;
    private ReentrantReadWriteLock lock;
    public Gardener(Garden garden, ReentrantReadWriteLock lock){
        this.garden = garden;
        this.lock = lock;
    }

    public void waterGarden(){
        for(int i = 0; i < garden.matrix.length; i++){
            for(int j = 0; j < garden.matrix[0].length; j++){
                lock.writeLock().lock();
                garden.matrix[i][j] = 1;
                System.out.println(String.format("Gardener watered flower %dx%d",i,j));
                lock.writeLock().unlock();
            }
        }
    }

    @Override
    public void run() {
        while (true){
            waterGarden();
        }
    }
}

class Weather implements Runnable{
    private Garden garden;
    private ReentrantReadWriteLock lock;
    public Weather(Garden garden, ReentrantReadWriteLock lock){
        this.garden = garden;
        this.lock = lock;
    }

    public void brokeGarden(int n,int m){
        lock.writeLock().lock();
        garden.matrix[n][m] = 0;
        System.out.println(String.format("Weather broken flower %dx%d",n,m));
        lock.writeLock().unlock();
    }

    @Override
    public void run() {
        Random r = new Random();
        while (true){
            int n = r.nextInt(garden.matrix.length);
            int m = r.nextInt(garden.matrix[0].length);
            brokeGarden(n,m);
        }
    }
}

class Viewer implements Runnable{
    private Garden garden;
    private ReentrantReadWriteLock lock;
    public Viewer(Garden garden, ReentrantReadWriteLock lock){
        this.garden = garden;
        this.lock = lock;
    }

    public void writeInFile() {
        lock.readLock().lock();
        try {
            FileWriter writer = new FileWriter("Garden.txt",true);
            for(int i = 0; i < garden.matrix.length; i++){
                for(int j = 0; j < garden.matrix[0].length; j++){
                    writer.write(garden.matrix[i][j] + " ");
                }
                writer.write('\n');
            }
            writer.write("\n\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
    }

    public void writeInConsole(){
        lock.readLock().lock();
        for(int i = 0; i < garden.matrix.length; i++){
            for(int j = 0; j < garden.matrix[0].length; j++){
                System.out.print(String.format("%d ",garden.matrix[i][j]));
            }
            System.out.println('\n');
        }
        System.out.println('\n');
        System.out.println('\n');
        System.out.println('\n');
        lock.readLock().unlock();
    }

    @Override
    public void run() {
        Random r = new Random();
        while (true){
            writeInFile();
            writeInConsole();
        }
    }
}
