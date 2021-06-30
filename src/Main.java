import controller.Game;
import enemy.Enemy;
import hero.Cowboy;
import hero.Cowgirl;
import hero.Hero;
import hero.Robot;
import hero.Santa;
import hero.Ninja;
import hero.Ninjagirl;
import zombie.FemaleZombie;
import zombie.MaleZombie;
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
        // Knight p2 = new Knight(150, new Point(300, 0));
        List<Hero> heros = new ArrayList<Hero>();
        heros.add(new Cowgirl(new Point(0, 0)));
        heros.add(new Ninjagirl(new Point(0, 0)));
        heros.add(new Robot(new Point(0, 0)));
        heros.add(new Santa(new Point(0, 0)));
        heros.add(new Cowboy(new Point(0, 0)));
        heros.add(new Ninja(new Point(0, 0)));
        // testing
        List<Zombie> zombies = new ArrayList<Zombie>();
        //zombies.add(new MaleZombie(new Point(500, 0)));
        //zombies.add(new FemaleZombie(new Point(600, 0)));
        World world = new World(new SpriteCollisionHandler());  // model
        Game game = new Game(world, heros, zombies);  // controller
        GameView view = new GameView(game);  // view
        game.start();  // run the game and the game loop
        view.launch(); // launch the GUI
    }
}
