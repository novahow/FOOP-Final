package controller;

import model.Direction;
import model.Homepage;
import model.TutorPage;
import model.World;
import model.WorldButton;
import model.EndButton;
import states.Attacking;
import states.Walking;
import model.RoleSelect;
import model.Sprite;
import model.SpriteCollisionHandler;
import controller.Game;
import enemy.Enemy;
import hero.*;
import hero.Robot;
import zombie.FemaleZombie;
import zombie.MaleZombie;
import zombie.Zombie;
import model.HealthPointSprite;
import model.World;
import model.Pause;
import java.util.List;
import views.GameView;

import java.awt.*;
import java.io.File;

import static media.AudioPlayer.addAudioByFilePath;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Game extends GameLoop {
    private List<Hero> heros;
    private List<Zombie> zombies;
    private World world;
    private Homepage homepage;
    private RoleSelect roleselect;
    private Pause pausepage;
    private EndButton endButton;
    private int choose;

    public Game(World world, List<Hero> heros, List<Zombie> zombies) {
        this.heros = heros;
        this.zombies = zombies;
        // this.p2 = p2;
        this.world = world;
        this.homepage = new Homepage();
        this.roleselect = new RoleSelect();
        this.pausepage = new Pause();
        this.homepage.setTutor(new TutorPage());
        this.endButton = new EndButton();
        for(Sprite z: zombies) {
            world.addSprite(z);
        }
    }

    @Override
    protected void setChoose(int c) {
        choose = c;
        world.setHero(getPlayer(choose));
        world.addSprite(getPlayer(choose));
    }

    @Override
    protected void addButton(WorldButton b) {
        world.addButton(b);
    }

    @Override
    protected void restart() {
        this.world = new World(new SpriteCollisionHandler());
        this.heros.clear();
        this.heros.add(new Cowgirl(new Point(0, 0)));
        this.heros.add(new Ninjagirl(new Point(0, 0)));
        this.heros.add(new Robot(new Point(0, 0)));
        this.heros.add(new Santa(new Point(0, 0)));
        this.heros.add(new Cowboy(new Point(0, 0)));
        this.heros.add(new Ninja(new Point(0, 0)));
        /*
        this.zombies.clear();
        this.zombies.add(new MaleZombie(new Point(2000, 0)));
        this.zombies.add(new MaleZombie(new Point(600, 0)));
        this.zombies.add(new FemaleZombie(new Point(400, 0)));
        for(Sprite z: zombies) {
            world.addSprite(z);
        }*/
    }

    public void moveKnight(Direction direction) {
        getPlayer(choose).move(direction);
    }

    public void stopKnight(Direction direction) {
        getPlayer(choose).stop(direction);
    }

    public void attack() {
        getPlayer(choose).attack();
    }

    public void shoot() {
        getPlayer(choose).shoot();
    }

    public void jump() {
        Hero hero = getPlayer(choose);
        hero.jump(hero.getWorld().obstacleAbove(hero.getLocation()));
    }

    public Hero getPlayer(int playerNumber) {
        // return playerNumber == 1 ? p1 : p2;
        return heros.get(playerNumber);
    }

    public void pause() {
        if(world.isRunning()) {
            world.setPause();
            pausepage.setPause();
        }
    }

    @Override
    protected World getWorld() {
        return world;
    }

    @Override 
    protected Homepage getHome() {
        return homepage;
    }

    @Override 
    protected RoleSelect getRoleSelect() {
        return roleselect;
    }

    @Override 
    protected EndButton getEnd() {
        return endButton;
    }


    @Override
    protected Pause getPause() {
        return pausepage;
    }

    public void clickButton(int x, int y, int release) {
        if(homepage.isRunning()){
            if(release == 1){
                int res = homepage.getClicked();
                if(res >= 0){
                    homepage.leave();
                    world.setWorldNum(res);
                }
            }
        }
        
        if(roleselect.isRunning()){
            if(release == 1){
                int res = roleselect.getClicked();
                if(res >= 0){
                    this.setChoose(res);
                    roleselect.leave();
                }
            }
        }

        if(world.isPause()) {
            if(release == 1) {
                int res = pausepage.getClicked();    
                if(res == 1) {
                    world.setPause();
                }
                else if(res == 2) {
                    world.stop();
                }
                else if(res == 3) {
                    stop();
                }

            }            
        }

        if(!world.isRunning()){
            if(release == 1){
                int res = endButton.getClicked();
                if(res > 0){
                    endButton.setVisible(false);
                }
            }
        }
    }

}
