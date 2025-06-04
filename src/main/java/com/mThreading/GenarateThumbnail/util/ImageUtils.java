package com.mThreading.GenarateThumbnail.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;


public class ImageUtils 
{
    public static void createThumbnail(File inputFile, File outputFile, int width, int height) throws IOException 
    {
        BufferedImage originalImage = ImageIO.read(inputFile);
        Image resized = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage thumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = thumbnail.createGraphics();
        g.drawImage(resized, 0, 0, null);
        g.dispose();

        ImageIO.write(thumbnail, "jpg", outputFile);
    }
}
