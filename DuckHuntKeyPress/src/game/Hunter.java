package game;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class Hunter extends Thread{
    private static final int MOVE_STEP = 20;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    ImageView hunter;
    public Hunter(ImageView hunterView){
        hunter = hunterView;
    }

    public void moveLeft(){
        moveLeft = true;
    }

    public void moveRight(){
        moveRight = true;
    }

    @Override
    public void run() {
        while(true){
            if(moveLeft){
                hunter.setX(hunter.getX() - MOVE_STEP);
                moveLeft = false;
            }else if(moveRight){
                hunter.setX(hunter.getX() + MOVE_STEP);
                moveRight = false;
            }
        }
    }
}
