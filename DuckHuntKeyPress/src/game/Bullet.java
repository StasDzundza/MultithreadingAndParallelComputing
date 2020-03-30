package game;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet extends Thread {
    private static final int DELAY = 20;
    private static final int DISCRETE_STEP_KOEFF = 10;
    private Image bullet;
    private Image boom;
    private ImageView imageView;
    private int delta_x;
    private int delta_y;

    private boolean isBoom;
    private boolean isDead;

    Bullet(Image bulletImage, Image boomImage, int pos_x, int pos_y, int dir_x, int dir_y) {
        super();
        int len = (int) Math.sqrt((dir_x - pos_x) * (dir_x - pos_x) + (dir_y - pos_y) * (dir_y - pos_y));
        this.delta_x = (DISCRETE_STEP_KOEFF * (dir_x - pos_x)) / len;
        this.delta_y = (DISCRETE_STEP_KOEFF * (dir_y - pos_y)) / len;

        this.bullet = bulletImage;
        this.boom = boomImage;

        imageView = new ImageView(bulletImage);
        imageView.setX(pos_x);
        imageView.setY(pos_y);

        this.isBoom = false;
        this.isDead = false;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            if (interrupted()) return;
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (interrupted()) return;

            Platform.runLater(() -> {
                imageView.setX(imageView.getX() + delta_x);
                imageView.setY(imageView.getY() + delta_y);
            });
            if (isBoom) {
                Platform.runLater(() -> imageView.setImage(boom));
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    this.isDead = true;
                    return;
                }
                this.isDead = true;
                return;
            }
        }
    }

    public ImageView getNode() {
        return imageView;
    }

    public boolean isOutOfRange() {
        return imageView.getX() > Game.WINDOW_WIDTH ||
                imageView.getY() < -imageView.getFitHeight();
    }

    public void explose() {
        this.isBoom = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public double getX() {
        return imageView.getX();
    }

    public double getY() {
        return imageView.getY();
    }
}
