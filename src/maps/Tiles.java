package maps;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;


import javax.imageio.*;
import java.nio.file.*;
public abstract class Tiles 
{
    public static ArrayList<Image> tiles = new ArrayList<Image>();
    private String tileoffset = "./assets/obstacles/";
    // private GraphicsConfiguration gc;

    // host sprites used for cloning

    /**
        Creates a new ResourceManager with the specified
        GraphicsConfiguration.
    
    public Tiles() 
    {
        tiles = new ArrayList<Image>();
        for(int i = 0; i < 3; i++){
            Integer i1 = i;
            tiles.add(loadImage(tileoffset + i1.toString() + ".png"));
        }
        
    }
    */
    public static void addTiles(String offset, String filename){
        tiles.add(loadImage(offset + filename));
    }

    /**
        Gets an image from the images/ directory.
    */
    public static Image loadImage(String name) 
    {
        return getScaledImage(new ImageIcon(name).getImage(), 50, 50);
    }

    public static Image getScaledImage(Image img, float x, float y) 
    {

        // set up the transform
        /*try
        {
            Image picture = ImageIO.read(new File("picture.png"));
            Image newImage = picture.getScaledInstance((int) x, (int) y, Image.SCALE_DEFAULT);
            return newImage;

        }
        catch (IOException e)
        {
            String workingDir = System.getProperty("user.dir");
            System.out.println("Current working directory : " + workingDir);
            e.printStackTrace();
        }*/
        Image newImage = img.getScaledInstance((int) x, (int) y, Image.SCALE_DEFAULT);
        return newImage;
        // draw the transformed image
    }

    public Image getTile(int index){
        return tiles.get(index);
    }

}