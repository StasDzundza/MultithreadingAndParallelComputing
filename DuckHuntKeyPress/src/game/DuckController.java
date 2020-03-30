package game;

import javafx.application.Platform;
import javafx.scene.image.Image;

import java.util.Random;

public class DuckController extends Thread {
    private static final int DELAY = 3000;
    private static final int MAX_DUCK_COUNT = 5;
    private Image duckFlyImage;
    private Image duckFallImage;

    DuckController(Image duckFlyImage, Image duckFallImag) {
        super();
        this.duckFlyImage = duckFlyImage;
        this.duckFallImage = duckFallImage;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            if (interrupted()) return;
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                return;
            }
            if (interrupted()) return;

            if (Game.ducks.size() < MAX_DUCK_COUNT) {
                Random random = new Random();
                Duck duck = new Duck(duckFlyImage, random.nextBoolean(), random.nextBoolean());
                Platform.runLater(() -> Game.item_group.getChildren().add(duck.getNode()));
                duck.setDaemon(true);
                Game.ducks.add(duck);
                duck.start();
            }

            for (int i = 0; i < Game.ducks.size(); i++) {
                Duck duck = Game.ducks.get(i);
                if (duck.getY() >= Game.WINDOW_HEIGHT - 50) {
                    Game.ducks.remove(duck);
                    i--;
                    Platform.runLater(() -> Game.item_group.getChildren().remove(duck));
                }
            }
        }
    }
}