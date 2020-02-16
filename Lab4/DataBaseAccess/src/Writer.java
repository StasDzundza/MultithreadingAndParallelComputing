import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Writer implements Runnable{
    private ReentrantReadWriteLock lock;
    private String text,filename;

    public Writer(ReentrantReadWriteLock lock){
        this.lock = lock;
    }

    public void setFindInfo(String text,String filename){
        this.text = text;
        this.filename = filename;
    }

    public void addInfoToFile() {
        lock.writeLock().lock();
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(text + '\n');
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally{
            lock.writeLock().unlock();
        }
    }

    @Override
    public void run() {
        addInfoToFile();
    }
}
