package Game;

import javafx.application.Platform;

public class GenerationCounter implements Runnable {

    @Override
    public void run() {
        Platform.runLater(() ->
                Game.label_counter.setText(String.valueOf(Integer.parseInt(Game.label_counter.getText()) + 1))
        );
    }
}
