package GUI;

import Task.ChangeFunction;
import Task.SliderChanger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainWindowUI extends JFrame {
    private JSlider slider;
    private JButton start,stop,inreasePriorityFirst,inreasePrioritySecond,decreasePriorityFirst,decreasePrioritySecond;
    private Thread t1,t2;
    JLabel priority1,priority2;

    public MainWindowUI(String title){
        super(title);
        setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUiElements();
        Container container = getContentPane();
        container.setLayout(new GridLayout(9,1,1,1));
        container.add(start);
        container.add(stop);
        container.add(inreasePriorityFirst);
        container.add(decreasePriorityFirst);
        container.add(inreasePrioritySecond);
        container.add(decreasePrioritySecond);
        container.add(slider);
        container.add(priority1);
        container.add(priority2);
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
        start = new JButton("Start");
        stop = new JButton("Stop");
        inreasePriorityFirst = new JButton("Increase 1");
        inreasePrioritySecond = new JButton("Increase 2");
        decreasePriorityFirst = new JButton("Decrease 1");
        decreasePrioritySecond = new JButton("Decrease 2");
        //labels
        priority1 = new JLabel("Thread1 priority : 1");
        priority2 = new JLabel("Thread2 priority : 9");
        addListeners();
    }
    public void addListeners(){
        start.addActionListener((ActionEvent)->{
            ChangeFunction functionIncrease = (mySlider)->{mySlider.setValue(mySlider.getValue()+1);};
            ChangeFunction functionDecrease = (mySlider)->{mySlider.setValue(mySlider.getValue()-1);};
            t1 = new Thread(new SliderChanger(slider,functionIncrease),"Thread1");
            t1.setPriority(1);
            t2 = new Thread(new SliderChanger(slider,functionDecrease),"Thread2");
            t2.setPriority(9);
            t1.start();
            t2.start();
        });

        stop.addActionListener((ActionEvent)-> {
            t1.interrupt();
            t2.interrupt();
            slider.setValue(50);
        });

        inreasePriorityFirst.addActionListener((ActionEvent)->{
            if(t1.getPriority()<10) {
                t1.setPriority(t1.getPriority() + 1);
                priority1.setText(String.format("Thread1 priority : %d", t1.getPriority()));
            }
        });

        inreasePrioritySecond.addActionListener((ActionEvent)->{
            if(t2.getPriority()<10) {
                t2.setPriority(t2.getPriority() + 1);
                priority2.setText(String.format("Thread2 priority : %d", t2.getPriority()));
            }
        });

        decreasePriorityFirst.addActionListener((ActionEvent)->{
            if(t1.getPriority()>1) {
                t1.setPriority(t1.getPriority() - 1);
                priority1.setText(String.format("Thread1 priority : %d", t1.getPriority()));
            }
        });

        decreasePrioritySecond.addActionListener((ActionEvent)-> {
            if(t2.getPriority()>1) {
                t2.setPriority(t2.getPriority() - 1);
                priority2.setText(String.format("Thread2 priority : %d", t2.getPriority()));
            }
        });
    }
}
