package Task;

import javax.swing.*;
import java.util.concurrent.Semaphore;

public class SliderChanger implements Runnable {
    private JSlider slider;
    private ChangeFunction sliderChanger;
    private static final int WaitTime = 100;
    private static int semaphore = 0;
    private static Semaphore s = new Semaphore(1);;
    public SliderChanger(JSlider slider, ChangeFunction sliderChanger){
        this.slider = slider;
        this.sliderChanger = sliderChanger;
    }

    @Override
    public void run() {
        //if(semaphore == 0) {
        if(s.tryAcquire()){
            //synchronized(slider){
                semaphore = 1;
                while (!Thread.currentThread().isInterrupted()) {
                    sliderChanger.changeSlider(slider);
                    try {
                        Thread.sleep(WaitTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                semaphore = 0;
                s.release();
            //}
        }
        else{
            JOptionPane.showMessageDialog(null,"This slider is using by other thread");
        }
    }
}
