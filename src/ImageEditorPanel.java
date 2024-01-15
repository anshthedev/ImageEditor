import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;

public class ImageEditorPanel extends JPanel implements KeyListener{

    Color[][] pixels;
    boolean quit = false;
    
    public ImageEditorPanel() {
        BufferedImage imageIn = null;
        try {
            //adding image to edit
            imageIn = ImageIO.read(new File("CarImage.png"));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        pixels = makeColorArray(imageIn);
        setPreferredSize(new Dimension(pixels[0].length, pixels.length));
        setBackground(Color.BLACK);

        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                g.setColor(pixels[row][col]);
                g.fillRect(col, row, 1, 1);
            }
        }
    }

    public void run() {
        
        while(!quit){
            repaint();
        }

        //Closes window
        System.exit(0);
    }

    public void saveImage() {
        BufferedImage imageOut = new BufferedImage(pixels[0].length, pixels.length, BufferedImage.TYPE_INT_RGB);
        
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                imageOut.setRGB(col, row, pixels[row][col].getRGB());
            }
        }

        try {
            //Saved file name
            File outputfile = new File("outputImage.png");
            ImageIO.write(imageOut, "png", outputfile);
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            //Error handling
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

    public Color[][] blur(Color[][] origArr){
        Color[][] newArr = new Color[origArr.length][origArr[0].length];

        for (int r = 0; r < newArr.length; r++) {
            for (int c = 0; c < newArr[r].length; c++) {
                int redSum = 0;
                int greenSum = 0;
                int blueSum = 0;
                int count = 0;

                for(int i = r-1; i<r + 1; i++){
                    for (int j = c-1; j < c+1; j++) {
                        if(i>=0 && j>=0 && j<newArr[r].length && i<newArr.length){
                            redSum += origArr[i][j].getRed();
                            blueSum += origArr[i][j].getBlue();
                            greenSum += origArr[i][j].getGreen();

                            count++;
                        }
                    }
                }

                newArr[r][c] = new Color(redSum/count, greenSum/count, blueSum/count);
            }
        }

        return newArr;
    }

    public Color[][] flipHoriz(Color[][] origArr){
        Color[][] newArr = new Color[origArr.length][origArr[0].length];

        for (int r = 0; r < newArr.length; r++) {
            for (int c = 0; c<newArr[r].length; c++){
                newArr[r][newArr[0].length-c-1] = origArr[r][c];
            }
        }

        return newArr;
    }

    public Color[][] flipVert(Color[][] origArr){
        Color[][] newArr = new Color[origArr.length][origArr[0].length];

        for (int c = 0; c < newArr[0].length; c++) {
            for (int r = 0; r<newArr.length; r++){
                newArr[newArr.length-r-1][c] = origArr[r][c];
            }
        }

        return newArr;
    }

    public Color[][] grayscale(Color[][] origArr){
        Color[][] newArr = new Color[origArr.length][origArr[0].length];

        for (int r = 0; r < newArr.length; r++) {
            for (int c = 0; c < newArr[0].length; c++) {

                int gray = (origArr[r][c].getRed() + origArr[r][c].getBlue() + origArr[r][c].getGreen()) / 3;

                newArr[r][c] = new Color(gray, gray, gray);
            }
        }

        return newArr;
    }

    public Color[][] makeColorArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color[][] result = new Color[height][width];
        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = new Color(image.getRGB(col, row), true);
                result[row][col] = c;
            }
        }

        return result;
    }


    //Handles key inputs for saving and image edits
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'b') {
            pixels = blur(pixels);
        }else if (e.getKeyChar() == 's') {
            saveImage();
        }else if (e.getKeyChar() == 'g') {
            pixels = grayscale(pixels);
        }else if(e.getKeyChar() == 'h'){
            pixels = flipHoriz(pixels);
        }else if(e.getKeyChar() == 'v'){
            pixels = flipVert(pixels);
        }else if (e.getKeyChar() == 'q'){
            quit = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // unused
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // unused
    }
}
