import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class snake {

    // image that represents the player's position on the board
    private BufferedImage image;
    // current position of the player on the board grid
    private Point pos;
    // last position that the snake's tile was
    private Point lastpos;
    // only useful for snake's head
    // direction that the snake's head is going to
    private String direction;
    // if the player already pressed any movement key
    private boolean movementSetted;
    // check if the snake has collide into a border
    private boolean collision;


    // the head's snake
    public snake() {

        // load the assets
        loadImage();
        // initialize the state
        pos = new Point(1, 0);
        lastpos = new Point(0, 0);
        direction = "right";

    }

    // the body's snake
    public snake(Point p) {

        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(p);
        lastpos = pos;

    }

    // methods from listeners and jpanel
    public void draw(Graphics g, ImageObserver observer) {

        // draw the player in TILE SIZE, so you can "walk" a tile each time
        g.drawImage(image, pos.x*map.TILE_SIZE, pos.y*map.TILE_SIZE, observer);

    }

    public void keyPressed(KeyEvent e) {

        // get the key pressed and compares to the key event button
        int key = e.getKeyCode();

        if (!movementSetted) {
            if (key == KeyEvent.VK_UP) {
                if (!direction.equals("down")) {
                    direction = "up";
                    setMovementSetted(true);
                }
            }
            else if (key == KeyEvent.VK_RIGHT) {
                if (!direction.equals("left")) {
                    direction = "right";
                    setMovementSetted(true);
                }
            }
            else if (key == KeyEvent.VK_DOWN) {
                if (!direction.equals("up")) {
                    direction = "down";
                    setMovementSetted(true);
                }
            }
            else if (key == KeyEvent.VK_LEFT) {
                if (!direction.equals("right")) {
                    direction = "left";
                    setMovementSetted(true);
                }
            }
        }

    }

    // methods created
    private void loadImage() {

        try {
            image = ImageIO.read(new File("Images/snake.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }

    }

    public boolean tick() {

        // this gets called once every tick, before the repainting process happens
        // prevent the player from moving off the edge of the board sideways
        if (pos.x < 0) {
            pos.x = 0;
            return true;
        }
        if (pos.x >= map.ROWS) {
            pos.x = map.ROWS - 1;
            return true;
        }
        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
            return true;
        }
        if (pos.y >= map.COLUMNS) {
            pos.y = map.COLUMNS - 1;
            return true;
        }
        return false;

    }

    // getters and setters
    public Point getPos() {
        return pos;
    }

    public Point getLastpos() {
        return lastpos;
    }

    public String getDirection() {
        return direction;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public void setLastpos(Point lastpos) {
        this.lastpos = lastpos;
    }

    public boolean isMovementSetted() {
        return movementSetted;
    }

    public void setMovementSetted(boolean movementSetted) {
        this.movementSetted = movementSetted;
    }

}
