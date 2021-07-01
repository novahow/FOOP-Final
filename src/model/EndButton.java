package model;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;
public class EndButton extends JButton{
    private boolean mouseOver = false;
    private boolean mousePressed = false;
    private ImageIcon hovered;
    private ImageIcon original;
    private Image hoverImg;
    private Cursor handCursor;
    private Cursor defaultCursor;
    private MouseAdapter mouseListener;
    private int clickNum = -1;
    public EndButton(){
        

        super(new ImageIcon("assets/gamebuttons/home.png"));
        defaultCursor = this.getCursor();
        handCursor = new Cursor(Cursor.HAND_CURSOR);
        // original = img;
        setFocusable(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setVisible(false);
        mouseListener =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                clickNum = 1;
                Component src = (Component) e.getSource();
                while(src.getParent() != null){
                    src = src.getParent();
                    // System.out.printf("%s\n", src.getName());
                    if(src.getName() != null && src.getName().equals("canvas")){
                        break;
                    }
                    
                }
                src.dispatchEvent(e);
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                setCursor(handCursor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setCursor(handCursor);
            }
        };
        addMouseMotionListener(mouseListener);
        addMouseListener(mouseListener);
    }

    public int getClicked(){
        int tmp = clickNum;
        clickNum = -1;
        return tmp;
    }
}
