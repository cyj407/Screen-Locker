package qa_part;

import java.awt.AlphaComposite;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.RenderingHints;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.OutputStream;  
  
import javax.imageio.ImageIO;  
import javax.swing.ImageIcon;  
    
 
public class ImageMarkLogoByIcon {     
     
    public static void main(String[] args) {     
        String srcImgPath = "c:/1111.png";     
        String iconPath = "c:/0439.jpg";     
        String targerPath = "c:/c.png" ;   

        ImageMarkLogoByIcon.markImageByIcon(iconPath, srcImgPath, targerPath , -45);    
    }     
  
    public static void markImageByIcon(String iconPath, String srcImgPath,     
            String targerPath) {     
        markImageByIcon(iconPath, srcImgPath, targerPath, null) ;   
    }     

    public static void markImageByIcon(String iconPath, String srcImgPath,     
            String targerPath, Integer degree) {     
        OutputStream os = null;     
        try {     
            Image srcImg = ImageIO.read(new File(srcImgPath));   
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),     
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);   
            
            Graphics2D g = buffImg.createGraphics();     

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,     
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);     
    
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg     
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);     
    
            if (null != degree) {     
            	
                g.rotate(Math.toRadians(degree),     
                        (double) buffImg.getWidth() / 2, (double) buffImg     
                                .getHeight() / 2);     
            }     
            ImageIcon imgIcon = new ImageIcon(iconPath);     
            Image img = imgIcon.getImage();     
            float alpha = 0.2f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));     
            g.drawImage(img, 150, 300, null);     
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));     
            g.dispose();     
            os = new FileOutputStream(targerPath);     	 
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if (null != os)     
                    os.close();     
            } catch (Exception e) {     
                e.printStackTrace();     
            }     
        }     
    }     
}   