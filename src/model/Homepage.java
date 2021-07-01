package model;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;

public class Homepage extends JPanel{
    private ArrayList<CircleButton> butts; 
    private Boolean running;
    private MouseInputAdapter mouseListener ;
    private int clickedNum = -1;
    private int width = 1200, height = 760;
    private TutorPage tutor;
    private JPanel firstPage = new JPanel();
    GridBagLayout griadLayout;
    public Homepage(){
        butts = new ArrayList<>();
        LayoutManager ovl = new OverlayLayout(this);
        this.setLayout(ovl);
        setPreferredSize(new Dimension(width, height));
        setVisible(false);
        setOpaque(false);
        firstPage.setBorder(BorderFactory.createEmptyBorder(100, 30, 30, 100));
        griadLayout = new GridBagLayout();
        firstPage.setLayout(griadLayout);
        firstPage.setVisible(true);
        firstPage.setOpaque(false);
        firstPage.setPreferredSize(new Dimension(width, height));
        running = true;

        mouseListener =  new MouseInputAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                int cnt = 0;
                for(CircleButton c: butts){
                    if(c == e.getSource()){
                        clickedNum = cnt;
                        Component src = (Component) e.getSource();
                        while(src.getParent() != null){
                            src = src.getParent();
                            if(src.getName() != null && src.getName().equals("canvas")){
                                break;
                            }
                        }

                        src.dispatchEvent(e);
                        // leave();
                    }
                    cnt += 1;       
                }

                if(tutor.isVisible()){
                    tutor.setVisible(false);
                    firstPage.setVisible(true);
                }
            }
        };

        MouseListener tutorListener = new MouseInputAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                if(tutor.isVisible()){
                    tutor.setVisible(false);
                    firstPage.setVisible(true);
                }
            }
        };

        addMouseListener(tutorListener);

        
        GridBagConstraints c0 = new GridBagConstraints();
        Image dimg = new GetSizedImage("./assets/gamebuttons/Welcome.png", 600, 300).getImage();
        JLabel test = new JLabel(new ImageIcon(dimg), JLabel.CENTER);
        c0.gridx = 0; 
        c0.gridy = 0;
        c0.gridwidth = 7;
        c0.gridheight = 4;
        c0.weightx = 0;
        c0.weighty = 0;
        c0.fill = GridBagConstraints.BOTH;
        c0.anchor = GridBagConstraints.CENTER;
        firstPage.add(test, c0);
        GridBagLayout gbl = new GridBagLayout();
        for(int i = 1; i <= 3; i++){
            Integer i1 = i;
            String name = "./assets/gamebuttons/" + i1.toString() + ".png";
            dimg = new GetSizedImage(name, 150, 150).getImage();
            
            CircleButton button = new CircleButton(new ImageIcon(dimg));
            name = "./assets/gamebuttons/" + i1.toString() + "_1.png";
            dimg = new GetSizedImage(name, 150, 150).getImage();

            button.addHover(new ImageIcon(dimg), dimg);
            butts.add(button);
        }

        for(CircleButton e: butts){
            e.setVisible(true);
            e.addMouseListener(mouseListener);
        }

        dimg = new GetSizedImage(
            "./assets/gamebuttons/tutorial.png", 300, 100).getImage();
        
        JButton tutor = new HoverButton(new ImageIcon(dimg));
        tutor.setPreferredSize(new Dimension(dimg.getWidth(null), dimg.getHeight(null)));
        c0.gridx = 2;
        c0.gridy = 4;
        c0.gridheight = 1;
        c0.gridwidth = 3;
        c0.weightx = 0;
        c0.weighty = 0;
        c0.fill = GridBagConstraints.NONE;
        c0.anchor = GridBagConstraints.CENTER;
        c0.insets = new Insets(20, 70, 20, 70);
        gbl.setConstraints(tutor, c0);
        firstPage.add(tutor, c0);

        for(int i = 0; i < butts.size(); i++){
            CircleButton e = butts.get(i);
            c0.gridx = i + 2;
            c0.gridy = 6;
            c0.gridwidth = 1;
            c0.gridheight = 1;
            c0.weightx = 0;
            c0.weighty = 0;
            c0.fill = GridBagConstraints.NONE;
            c0.anchor = GridBagConstraints.NORTH;
            c0.insets = new Insets(20, 70, 70, 70);
            gbl.setConstraints(e, c0);
            e.setPreferredSize(new Dimension(130, 130));
            // e.setBorder(BorderFactory.createLineBorder(Color.RED));
            e.setHorizontalAlignment(SwingConstants.CENTER);
            // e.setBorder(BorderFactory.createLineBorder(Color.RED));
            firstPage.add(e, c0);
        }

        
            // add(buttonContainer);
        // super.setVisible(true);
        add(firstPage);
    }
    public void restart() {
        for(CircleButton e: butts){
            e.setVisible(true);
        }
        setVisible(false);
        running = true;
    }

    public void leave(){
        for(CircleButton e: butts){
            e.setVisible(false);
        }
        setVisible(false);
        running = false;

    }

    public Boolean isRunning() {
        return running;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public int getClicked(){
        int tmp = clickedNum;
        clickedNum = -1;
        return tmp;
    }


    public void setTutor(TutorPage tutor){
        this.tutor = tutor;
        tutor.setAlignmentX(Component.CENTER_ALIGNMENT);
        tutor.setMaximumSize(new Dimension(600, 600));
        add(tutor);        
    }

    public TutorPage getTutor(){
        return tutor;
    }

    public class HoverButton extends JButton{
        private boolean mouseOver = false;
        private boolean mousePressed = false;
        private ImageIcon hovered;
        private ImageIcon original;
        private Image hoverImg;
        private Cursor handCursor;
        private Cursor defaultCursor;
        public HoverButton(ImageIcon img){
            
            super(img);
            defaultCursor = this.getCursor();
            handCursor = new Cursor(Cursor.HAND_CURSOR);
            original = img;
            setFocusable(false);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            MouseAdapter mouseListener = new MouseAdapter(){
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    // setIcon(hovered);
                    setCursor(handCursor);
                    // repaint();
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    // setIcon(original); 
                    setCursor(handCursor);
                    // repaint();           
                }

                @Override
                public void mouseReleased(MouseEvent me){
                    if(!tutor.isVisible()){
                        tutor.setVisible(true);
                        firstPage.setVisible(false);
                    }   
                }
            };
            
            addMouseListener(mouseListener);
            addMouseMotionListener(mouseListener);	
        }
    }
}

