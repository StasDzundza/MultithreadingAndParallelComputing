package game;

import javafx.application.Platform;

public class BulletController extends Thread {
    private static final int DELAY = 100;
    private static final int MIN_DISTANCE = 100;

    BulletController() {
        super();
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

            for (int i = 0; i < Game.bullets.size(); i++) {
                Bullet bullet = Game.bullets.get(i);
                synchronized (bullet) {
                    if (bullet.isDead() || bullet.isOutOfRange()) {
                        Game.bullets.remove(bullet);
                        Platform.runLater(() -> Game.item_group.getChildren().remove(bullet.getNode()));
                        i--;
                    } else {
                        for (int j = 0; j < Game.ducks.size(); j++) {
                            Duck duck = Game.ducks.get(j);
                            synchronized (duck) {
                                double distance = Math.sqrt(
                                        (duck.getX() - bullet.getX()) * (duck.getX() - bullet.getX()) +
                                                (duck.getY() - bullet.getY()) * (duck.getY() - bullet.getY())
                                );
                                if (distance <= MIN_DISTANCE) {
                                    bullet.explose();
                                    duck.makeFall();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}