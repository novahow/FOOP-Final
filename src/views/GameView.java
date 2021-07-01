package views;

import controller.Game;
import controller.GameLoop;
import model.Direction;
import model.Sprite;
import model.World;
import model.Homepage;
import model.RoleSelect;
import model.Pause;
import model.EndButton;
import model.WorldButton;

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
    private final Canvas canvas = new Canvas();
    private final Game game;

    public GameView(Game game) throws HeadlessException {
        this.game = game;
        game.setView(canvas);
        setName("topView");
        canvas.addGame(game);
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
                    case KeyEvent.VK_ESCAPE:
                        game.pause();
                        break;
                    case KeyEvent.VK_W:
                        game.moveKnight(Direction.UP);
                        break;
                    case KeyEvent.VK_S:
                        game.moveKnight(Direction.DOWN);
                        break;
                    case KeyEvent.VK_A:
                        game.moveKnight(Direction.LEFT);
                        break;
                    case KeyEvent.VK_D:
                        game.moveKnight(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_E:
                        game.attack();
                        break;
                    case KeyEvent.VK_R:
                        game.shoot();
                        break; 
                    case KeyEvent.VK_SPACE:
                        game.jump();
                        break;
                    case KeyEvent.VK_I:
                        game.moveKnight(Direction.UP);
                        break;
                    case KeyEvent.VK_K:
                        game.moveKnight(Direction.DOWN);
                        break;
                    case KeyEvent.VK_J:
                        game.moveKnight(Direction.LEFT);
                        break;
                    case KeyEvent.VK_L:
                        game.moveKnight(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_U:
                        game.attack();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    // case KeyEvent.VK_W:
                    //     game.stopKnight(Direction.UP);
                    //     break;
                    // case KeyEvent.VK_S:
                    //     game.stopKnight(Direction.DOWN);
                    //     break;
                    case KeyEvent.VK_A:
                        game.stopKnight(Direction.LEFT);
                        break;
                    case KeyEvent.VK_D:
                        game.stopKnight(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_I:
                        game.stopKnight(Direction.UP);
                        break;
                    // case KeyEvent.VK_K:
                    //     game.stopKnight(Direction.DOWN);
                    //     break;
                    case KeyEvent.VK_J:
                        game.stopKnight(Direction.LEFT);
                        break;
                    case KeyEvent.VK_L:
                        game.stopKnight(Direction.RIGHT);
                        break;
                }
            }
        });
        addMouseListener(new MouseInputAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                game.clickButton(e.getX(), e.getY(), 0);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                game.clickButton(e.getX(), e.getY(), 1);
                if(game.isExit()) {
                    setVisible(false); //you can't see me!
                    dispose(); //Destroy the JFrame object
                }
                canvas.world.clickButton(e.getX(), e.getY());
            }
        });
    }

    public static class Canvas extends JPanel implements GameLoop.View {
        private World world;
        private Homepage home;
        private RoleSelect roleselect;
        private Pause pausepage;
        private EndButton endButton;
        private Game game;
        int state;
        int ispause;
        public Canvas(){
            super();
            addMouseListener(new MouseInputAdapter(){
                @Override
                public void mousePressed(MouseEvent e) {
                    game.clickButton(e.getX(), e.getY(), 0);
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    game.clickButton(e.getX(), e.getY(), 1);
                    if(game.isExit()) {
                        setVisible(false); //you can't see me!
                        disposeParentFrame();
                    }
                }
            });
            
            setName("canvas");
        }

        @Override
        public void disposeParentFrame(){
            SwingUtilities.getWindowAncestor(this).dispose();//Destroy the JFrame object
        }

        @Override
        public void addGame(Game game){
            this.game = game;
        }

        @Override
        public void render(World world) {
            this.world = world;
            repaint(); // ask the JPanel to repaint, it will invoke paintComponent(g) after a while.
            state = 0;
            ispause = 0;
        }

        @Override
        public void render(Homepage home) {
            this.home = home;
            setLayout(new GridBagLayout());
            home.setVisible(true);
            repaint();
            state = 1;
        }

        @Override
        public void render(Pause pausepage) {
            this.pausepage = pausepage;
            pausepage.setVisible(true);
            ispause = 1;
            repaint();
        }

        @Override
        public void addPanel(JPanel roleselect){
            this.add(roleselect);
        }

        @Override
        public void addButton(JButton btn){
            this.add(btn);
        }

        @Override
        public void render(RoleSelect roleselect){
            roleselect.setVisible(true);
            roleselect.setOpaque(false);
            repaint();
            state = 2;
        }

        @Override
        protected void paintComponent(Graphics g /*paintbrush*/) {
            super.paintComponent(g);
            // Now, let's paint
            g.setColor(Color.WHITE); // paint background with all white
            g.fillRect(0, 0, GameView.WIDTH, GameView.HEIGHT);
            if(state == 0) {
                world.render(g); // ask the world to paint itself and paint the sprites on the canvas
                if(ispause == 1) {
                    // pausepage.render(g);
                    ispause = 0;
                }
            }
            else if(state == 1){
                ImageIcon i = new ImageIcon("assets/back.gif");
                Image bg = i.getImage();
                g.drawImage(bg, 0, 0, 1200, 800, null);
            }

            else if(state == 2){
                g.drawImage(new ImageIcon("./assets/Sprites/Sel.jpg").getImage(), 0, 0, 1200, 800, null);
            }
        }
        
    }
}
