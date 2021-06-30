package model;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
public class GetSizedImage {
    private Image sized;
    public  GetSizedImage(String name, int width, int height){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(name));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        int w = img.getWidth(), h = img.getHeight();
        int newidth = (int) ((float)height / (float)h * w);
        int newheight = (int) ((float)width / (float)w * h);
        if(width == height){
            sized = img.getScaledInstance(width, height,
                Image.SCALE_SMOOTH);
        }

        else if(w > h){
            sized = img.getScaledInstance(newidth, height,
                Image.SCALE_SMOOTH);
        }

        else{
            sized = img.getScaledInstance(width, newheight,
                Image.SCALE_SMOOTH);
        }
        
    }

    public Image getImage(){
        return sized;
    }
}


