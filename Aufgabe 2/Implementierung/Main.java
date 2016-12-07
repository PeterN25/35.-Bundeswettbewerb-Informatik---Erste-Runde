import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.File;

import javax.imageio.ImageIO;

public class Main
{
    public Main(String s) throws java.io.IOException
    {
        makeImage(s);
    }
    
    /**
     * Erstellt die gefärbten Bilder und speichert sie in das Verzeichnis
     * @param s Dateiname des ursprünglichen Bildes
     */
    private void makeImage(String s) throws java.io.IOException
    {
        //Orginalbild 1
        BufferedImage imgOr = ImageIO.read(new File(s));
        //Orginalbild 2
        BufferedImage imgAnd = ImageIO.read(new File(s));
        //Horizontal bearbeitetes Bild
        BufferedImage imgH = imgHorizontal(ImageIO.read(new File(s)));
        // Vertikal bearbeitetes Bild
        BufferedImage imgV = imgVertical(ImageIO.read(new File(s))); 
        // RGB von weiß
        int rgb = new Color(255,255,255).getRGB();
        //Überprüfung jedes Pixels
        for(int i=0; i<imgAnd.getHeight(); i++)
        {
            for(int j=0; j<imgAnd.getWidth()-1; j++)
            {
                //Wenn in beiden Bearbeitungen Pixel weiß ist, dann werden beide Originalbilder bearbeitet
                if(imgH.getRGB(j,i) == rgb && imgV.getRGB(j,i) == rgb)
                {
                    imgAnd.setRGB(j,i,rgb);
                    imgOr.setRGB(j,i,rgb);
                }
                //Wenn nur bei einem, wird nur eins weiß gefärbt
                else if(imgH.getRGB(j,i) == rgb || imgV.getRGB(j,i) == rgb)
                {
                    imgOr.setRGB(j,i,rgb);
                }
            }
        }
        //Speicherung der bearbeiteten Bilder
        ImageIO.write(imgAnd,"PNG",new File("And"+s));
        ImageIO.write(imgOr,"PNG",new File("Or"+s));
    }
    
    /**
     * Gibt ein Bild zurück, bei dem Pixel weiß gefärbt wurden, wenn horizontal benachbarte Pixel die gleiche Farbe haben
     * @param img Originalbild
     * @return bearbeitetes Bild
     */
    private BufferedImage imgHorizontal(BufferedImage img)
    {
        //Untersuchung aller Pixel
        for(int i=0; i<img.getHeight(); i++)
        {
            for(int j=0; j<img.getWidth()-1; j++)
            {
                //RGB-Wert von weiß
                int rgb = new Color(255,255,255).getRGB();
                //Anzahl gleichfarbiger Pixel in einer Kette
                int k=0;
                //Wenn der folgende Pixel die gleiche Farbe hat, k inkrementieren und den nächsten Pixel anschauen
                while(j+k+1<img.getWidth() && img.getRGB(j+k,i) == img.getRGB(j+k+1,i))
                {
                    k++;
                }
                //Wenn es gleiche Pixel gibt, den ersten und anschließend alle weiteren Pixel weiß färben
                if(k>0)
                img.setRGB(j,i,rgb);
                for(int l=0; l<k; l++)
                {
                    img.setRGB(j+l,i,rgb);
                }
                //Den nächsten nicht gefärbten Pixel anschauen
                j+=k;
            }
        }
        return img;
    }
    
    /**
     * Gibt ein Bild zurück, bei dem Pixel weiß gefärbt wurden, wenn vertikal benachbarte Pixel die gleiche Farbe haben
     * @param img Originalbild
     * @return bearbeitetes Bild
     */
    private BufferedImage imgVertical(BufferedImage img)
    {
        for(int i=0; i<img.getWidth(); i++)
        {
            for(int j=0; j<img.getHeight()-1; j++)
            {
                int rgb = new Color(255,255,255).getRGB();
                int k=0;
                while(j+k+1<img.getHeight() && img.getRGB(i,j+k) == img.getRGB(i,j+k+1))
                {
                    k++;
                }
                if(k>0)
                img.setRGB(i,j,rgb);
                for(int l=0; l<k; l++)
                {
                    img.setRGB(i,j+l,rgb);
                }
                j+=k;
            }
        }
        return img;
    }
    
    public static void main(String[]args) throws java.io.IOException
    {
        for(int i=0; i<args.length; i++)
        {
            new Main(args[0]);
        }
    }
}
