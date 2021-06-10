import controller.Game;
import model.World;
import views.GameView;

import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        World world = new World();
        Game game = new Game(world);
        GameView view = new GameView(game);
        game.start();
        view.launch();
    }
}