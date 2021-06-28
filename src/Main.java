import controller.Game;
import enemy.Enemy;
import hero.Hero;
import hero.Robot;
import hero.Ninja;
import zombie.Zombie;
import model.HealthPointSprite;
import model.SpriteCollisionHandler;
import model.World;
import states.Attacking;
import states.Walking;
import views.GameView;

import java.awt.*;
import java.io.File;

import java.util.List;
import java.util.ArrayList;

import static media.AudioPlayer.addAudioByFilePath;

/**
 * Demo route: Main, GameView, Game, GameLoop, World, Sprite, Knight, FiniteStateMachine
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Main {
    public static void main(String[] args) {
        addAudioByFilePath(Walking.AUDIO_STEP1, new File("assets/audio/step1.wav"));
        addAudioByFilePath(Walking.AUDIO_STEP2, new File("assets/audio/step2.wav"));
        addAudioByFilePath(Attacking.AUDIO_SWORD_CLASH_1, new File("assets/audio/sword-clash1.wav"));
        addAudioByFilePath(Attacking.AUDIO_SWORD_CLASH_2, new File("assets/audio/sword-clash2.wav"));
        addAudioByFilePath(HealthPointSprite.AUDIO_DIE, new File("assets/audio/die.wav"));

        // initialization procedure
        Hero p1 = new Robot(new Point(0, 534));
        Hero p2 = new Ninja(new Point(0, 0));
        // Knight p2 = new Knight(150, new Point(300, 0));
        List<Hero> heros = new ArrayList<Hero>();
        heros.add(p1);
        heros.add(p2);
        // testing
        Enemy e = new Enemy(20, new Point(300, 534), p1);
        Zombie z1 = new Zombie(0, 30, new Point(500, 0));
        Zombie z2 = new Zombie(1, 30, new Point(600, 0));
        World world = new World(new SpriteCollisionHandler(), e, z1, z2);  // model
        Game game = new Game(world, heros);  // controller
        GameView view = new GameView(game);  // view
        game.start();  // run the game and the game loop
        view.launch(); // launch the GUI
    }
}
