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
            int finalh = 700;
            int finalw = 1200;
            // resizing the image
            int w = img.getWidth();
            int h = img.getHeight();
            BufferedImage dimg = new BufferedImage(finalw, finalh, img.getType());
            Graphics2D g = dimg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(img, 0, 0, finalw, finalh, 0, 0, w, h, null);
            g.dispose();


            ImageIcon icon=new ImageIcon(dimg);
            JFrame frame=new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setSize(finalw,finalh);
            JLabel lbl=new JLabel();
            lbl.setIcon(icon);
            frame.add(lbl);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        private Controller controller;
}
