package com.company;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class DisplayImage
{
         public DisplayImage(Controller controller, String imagePath) throws IOException
        {
            this.controller = controller;
            BufferedImage img= ImageIO.read(new File(imagePath));
            // resizing the image
            int w = img.getWidth();
            int h = img.getHeight();
            int finalh = 600;
            int finalw =  finalh *  w / h;
            BufferedImage dimg = new BufferedImage(finalw, finalh, img.getType());
            Graphics2D g = dimg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(img, 0, 0, finalw, finalh, null);

            ImageIcon icon=new ImageIcon(dimg);
            JFrame frame=new JFrame();
            frame.setLayout(new FlowLayout());
            JLabel lbl=new JLabel();
            lbl.setIcon(icon);
            lbl.setSize (finalw, finalh);
            frame.add(lbl);
            frame.pack ();
            frame.setVisible(true);
        }
    private Controller controller;
}