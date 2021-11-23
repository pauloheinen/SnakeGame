import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class snake {

    // image that represents the player's position on the board
    private BufferedImage image;
    // current position of the player on the board grid
    private Point pos;
    private Point lastpos;
    // direction that the snake's head is going
    private String direction;
    // check if the snake has collide into a border
    private boolean collision = false;
    private int length = 0;



    public snake() {

        // load the assets
        loadImage();
        length++;
        // initialize the state
        pos = new Point(1, 0);
        lastpos = new Point(0, 0);
        direction = "right";

    }

    public snake(Point p) {

        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(p);
        lastpos = pos;
        length++;

    }

    private void loadImage() {

        try {
            image = ImageIO.read(new File("Images/snake.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }

    }

    public void draw(Graphics g, ImageObserver observer) {

        // draw the player in TILE SIZE, so you can "walk" a tile each time
        g.drawImage(image, pos.x*map.TILE_SIZE, pos.y*map.TILE_SIZE, observer);

    }

    public void keyPressed(KeyEvent e) {

        // get the key pressed and compares to the key event button
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            if (!direction.equals("down")){
                direction = "up";
            }
        }
        if (key == KeyEvent.VK_RIGHT) {
            if (!direction.equals("left")){
                direction = "right";
            }
        }
        if (key == KeyEvent.VK_DOWN) {
            if (!direction.equals("up")){
                direction = "down";

            }
        }
        if (key == KeyEvent.VK_LEFT) {
            if (!direction.equals("right")){
                direction = "left";

            }
        }

    }

    public void tick() {

        // this gets called once every tick, before the repainting process happens
        // prevent the player from moving off the edge of the board sideways
        if (pos.x < 0) {
            pos.x = 0;
            collision = true;
        }
        if (pos.x >= map.ROWS) {
            pos.x = map.ROWS - 1;
            collision = true;
        }
        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
            collision = true;
        }
        if (pos.y >= map.COLUMNS) {
            pos.y = map.COLUMNS - 1;
            collision = true;
        }
        System.out.println("collision: " + collision);

    }






    public Point getPos() {
        return pos;
    }

    public Point getLastpos() {
        return lastpos;
    }

    public int getLength() {
        return length;
    }

    public String getDirection() {
        return direction;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public void setLastpos(Point lastpos) {
        this.lastpos = lastpos;
    }

}
