import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class food {

    private BufferedImage image;
    private Point pos;



    public food(Point p) {
        loadImage();
        pos = new Point(p);

    }

    private void loadImage() {

        try {
            image = ImageIO.read(new File("Images/food.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }

    }

    public void draw(Graphics g, ImageObserver observer) {

        // draw the player in TILE SIZE
        g.drawImage(image, pos.x*map.TILE_SIZE, pos.y*map.TILE_SIZE, observer);

    }


    public Point getPos() {
        return pos;
    }
}
