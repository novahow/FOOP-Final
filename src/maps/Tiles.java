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
    // public static ArrayList<Image>[] tiles = new ArrayList[3];
    public static ArrayList<ArrayList<Image> > tiles = 
                  new ArrayList<ArrayList<Image> >(3);
    private static void initTiles(){
        for(int i = 0; i < 3; i++){
            tiles.add(new ArrayList<Image>());
        }
    }
    
    private static String tileoffset = "./assets/obstacles/";

    private static void addTiles(String filename, int idx){
        tiles.get(idx).add(loadImage(filename));
    }

    public static void setUp(){
        initTiles();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String tmp = String.format("%s%d/%d.png", tileoffset, i, j);
                addTiles(tmp, i);
            }
        }
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

    public Image getTile(int index, int world){
        return tiles.get(world).get(index);
    }

}