package GUI;

import Task.ChangeFunction;
import Task.SliderChanger;

import javax.swing.*;
import java.awt.*;

public class MainWindowUI extends JFrame {
    private JSlider slider;
    private JButton start1,start2,stop1,stop2;
    private JLabel message;
    private Thread t1,t2;
    private Integer semaphore;

    public MainWindowUI(String title){
        super(title);
        semaphore = new Integer(0);
        setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUiElements();
        Container container = getContentPane();
        container.setLayout(new GridLayout(6,1,1,1));
        container.add(start1);
        container.add(stop1);
        container.add(start2);
        container.add(stop2);
        container.add(slider);
        container.add(message);
    }

    public void createUiElements(){
        //slider
        slider = new JSlider(0,100,50);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSize(700,20);
        //buttons
        start1 = new JButton("Start1");
        stop1 = new JButton("Stop1");
        start2 = new JButton("Start2");
        stop2 = new JButton("Stop2");
        //labels
        message = new JLabel("Information : ");
        addListeners();
    }
    public void addListeners(){
        start1.addActionListener((ActionEvent)->{
            ChangeFunction functionIncrease = (mySlider) -> {
                mySlider.setValue(90);
            };
            t1 = new Thread(new SliderChanger(slider, functionIncrease), "Thread1");
            t1.setPriority(Thread.MAX_PRIORITY);
            t1.setDaemon(true);
            t1.start();
        });
        start2.addActionListener((ActionEvent)->{
            ChangeFunction functionDecrease = (mySlider) -> {
                mySlider.setValue(10);
            };
            t2 = new Thread(new SliderChanger(slider, functionDecrease), "Thread2");
            t2.setPriority(Thread.MAX_PRIORITY);
            t2.setDaemon(true);
            t2.start();
        });

        stop1.addActionListener((ActionEvent)-> {
            if(t1.isAlive()){
                t1.interrupt();
                slider.setValue(50);
            }
        });

        stop2.addActionListener((ActionEvent)-> {
            if(t2.isAlive()){
                t2.interrupt();
                slider.setValue(50);
            }
        });
    }
}
