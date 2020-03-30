package game;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Game extends Application implements EventHandler<Event> {
    public static void main(String[] args) {
        launch(args);
    }

    static final int WINDOW_WIDTH = 1400;
    static final int WINDOW_HEIGHT = 700;

    private Image bulletImage;
    private Image boomImage;
    private Image duckFlyImage;
    private Image duckFallImage;
    private Image hunterImage;

    public static Group item_group;
    public static volatile ArrayList<Bullet> bullets;
    public static volatile ArrayList<Duck> ducks;
    public static BulletController bulletController;
    public static DuckController duckController;

    private ImageView hunterView;

    @Override
    public void start(Stage stage) {

        File fileBullet = new File("bullet.png");
        File fileBoom = new File("explosion.png");
        File fileDuckFly = new File("duckFly.jpg");
        File fileDuckFall = new File("duckFall.jpg");
        File fileHunter = new File("hunter.png");

        try {
            bulletImage = new Image(fileBullet.toURI().toURL().toString());
            boomImage = new Image(fileBoom.toURI().toURL().toString());
            duckFlyImage = new Image(fileDuckFly.toURI().toURL().toString());
            duckFallImage = new Image(fileDuckFall.toURI().toURL().toString());
            hunterImage = new Image(fileHunter.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        item_group = new Group();
        Scene scene = new Scene(item_group, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnMousePressed(this);
        stage.setScene(scene);
        stage.show();

        bullets = new ArrayList<>(100);
        ducks = new ArrayList<>(100);

        bulletController = new BulletController();
        bulletController.setDaemon(true);
        bulletController.start();

        duckController = new DuckController(duckFlyImage, duckFallImage);
        duckController.setDaemon(true);
        duckController.start();

        makeHunter();
    }

    @Override
    public void handle(Event event) {
        if (event instanceof MouseEvent) {
            makeBullet((int) ((MouseEvent) event).getX(), (int) ((MouseEvent) event).getY());
        }
    }

    private void makeHunter() {
        hunterView = new ImageView(hunterImage);
        hunterView.setX(-100);
        hunterView.setY(WINDOW_HEIGHT - hunterImage.getHeight());
        item_group.getChildren().add(hunterView);
    }

    private void makeBullet(int x, int y) {
        Bullet bulletThread = new Bullet(bulletImage, boomImage, 230, (int) (WINDOW_HEIGHT - bulletImage.getHeight() - 250), x, y);
        item_group.getChildren().add(bulletThread.getNode());
        bulletThread.setDaemon(true);
        bullets.add(bulletThread);
        bulletThread.start();
    }
}