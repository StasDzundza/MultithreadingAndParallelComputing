import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reader implements Runnable {
    private ReentrantReadWriteLock lock;
    private String name,filename;

    public Reader(ReentrantReadWriteLock lock){
        this.lock = lock;
    }

    public void setDBInfo(String name,String filename){
        this.name = name;
        this.filename = filename;
    }
    public void findPhoneByName(){
        String number = null;
        try{
            lock.readLock().lock();
            FileReader fr= new FileReader(filename);
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
              String n = scan.next();
              if(name.equalsIgnoreCase(n)){
                 scan.next();
                 number = scan.next();
                 System.out.println(String.format("%s - %s",name,number));
              }
            }
            fr.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally {
            lock.readLock().unlock();
        }

    }

    @Override
    public void run() {
        findPhoneByName();
    }
}
