package Task;

import javax.swing.*;

public class SliderChanger implements Runnable {
    private JSlider slider;
    private ChangeFunction sliderChanger;
    private static final int WaitTime = 100;
    public SliderChanger(JSlider slider, ChangeFunction sliderChanger){
        this.slider = slider;
        this.sliderChanger = sliderChanger;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            synchronized (slider) {
                sliderChanger.changeSlider(slider);
                try {
                    Thread.sleep(WaitTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
