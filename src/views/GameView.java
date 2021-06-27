package views;

import controller.Game;
import controller.GameLoop;
import model.Direction;
import model.Sprite;
import model.World;
import model.Homepage;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GameView extends JFrame {
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1200;
    public static final int P1 = 1;
    public static final int P2 = 2;
    private final Canvas canvas = new Canvas();
    private final Game game;

    public GameView(Game game) throws HeadlessException {
        this.game = game;
        game.setView(canvas);
    }

    public void launch() {
        // GUI Stuff
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(canvas);
        setSize(WIDTH, HEIGHT);
        setContentPane(canvas);
        setVisible(true);

        // Keyboard listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_W:
                        game.moveKnight(P1, Direction.UP);
                        break;
                    case KeyEvent.VK_S:
                        game.moveKnight(P1, Direction.DOWN);
                        break;
                    case KeyEvent.VK_A:
                        game.moveKnight(P1, Direction.LEFT);
                        break;
                    case KeyEvent.VK_D:
                        game.moveKnight(P1, Direction.RIGHT);
                        break;
                    case KeyEvent.VK_E:
                        game.attack(P1);
                        break;
                    case KeyEvent.VK_SPACE:
                        game.jump(P1);
                        break;
                    case KeyEvent.VK_I:
                        game.moveKnight(P2, Direction.UP);
                        break;
                    case KeyEvent.VK_K:
                        game.moveKnight(P2, Direction.DOWN);
                        break;
                    case KeyEvent.VK_J:
                        game.moveKnight(P2, Direction.LEFT);
                        break;
                    case KeyEvent.VK_L:
                        game.moveKnight(P2, Direction.RIGHT);
                        break;
                    case KeyEvent.VK_U:
                        game.attack(P2);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_W:
                        game.stopKnight(P1, Direction.UP);
                        break;
                    case KeyEvent.VK_S:
                        game.stopKnight(P1, Direction.DOWN);
                        break;
                    case KeyEvent.VK_A:
                        game.stopKnight(P1, Direction.LEFT);
                        break;
                    case KeyEvent.VK_D:
                        game.stopKnight(P1, Direction.RIGHT);
                        break;
                    case KeyEvent.VK_I:
                        game.stopKnight(P2, Direction.UP);
                        break;
                    case KeyEvent.VK_K:
                        game.stopKnight(P2, Direction.DOWN);
                        break;
                    case KeyEvent.VK_J:
                        game.stopKnight(P2, Direction.LEFT);
                        break;
                    case KeyEvent.VK_L:
                        game.stopKnight(P2, Direction.RIGHT);
                        break;
                }
            }



        });
        addMouseListener(new MouseInputAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                game.clickButton(e.getX(), e.getY(), 0);
                //System.out.printf("clicked at %d %d\n", e.getX(), e.getY());
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                game.clickButton(e.getX(), e.getY(), 1);
                //System.out.printf("released %d %d\n", e.getX(), e.getY());
            }
        });
    }

    public static class Canvas extends JPanel implements GameLoop.View {
        private World world;
        private Homepage home;
        int state;

        @Override
        public void render(World world) {
            this.world = world;
            repaint(); // ask the JPanel to repaint, it will invoke paintComponent(g) after a while.
            state = 0;
        }

        @Override
        public void render(Homepage home) {
            this.home = home;
            repaint();
            state = 1;
        }

        @Override
        protected void paintComponent(Graphics g /*paintbrush*/) {
            super.paintComponent(g);
            // Now, let's paint
            g.setColor(Color.WHITE); // paint background with all white
            g.fillRect(0, 0, GameView.WIDTH, GameView.HEIGHT);
            if(state == 0) {
                world.render(g); // ask the world to paint itself and paint the sprites on the canvas
            }
            else {
                home.render(g);
            }
        }
    }
}
