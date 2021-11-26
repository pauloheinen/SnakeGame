import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;


public class map extends JPanel implements KeyListener, ActionListener {

    private final Timer timer;
    private final Random random = new Random();

    private final ArrayList<snake> player = new ArrayList<>();
    private final ArrayList<food> apple = new ArrayList<>();
    private int points = 0;

    // map's configs
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int DELAY = 420;
    public static final int TILE_SIZE = 25;
    public static final int COLUMNS = WIDTH/TILE_SIZE;
    public static final int ROWS = HEIGHT/TILE_SIZE;


    public map(){

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        // initialize the game state
        player.add(new snake());
        player.add(new snake(new Point(player.get(player.size()-1).getLastpos())));
        for (int i = 0; i < 6; i++)
            spawnFood();

        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // this method is called by the timer every DELAY ms.
        move();
        player.get(0).setMovementSetted(false);
        // prevent the player from disappearing off the board
        if (player.get(0).tick())
            timer.stop();

        checkCollisionItself();
        if (checkCollisionFood())
            spawnFood();

        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        repaint();

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver
        // because Component implements the ImageObserver interface, and JPanel
        // extends from Component. So "this" Board instance, as a Component, can
        // react to imageUpdate() events triggered by g.drawImage()

        // draw our graphics.
        drawBackground(g);
        for (snake node : player)
            node.draw(g, this);

        for (food apple: apple) {
            apple.draw(g, this);
        }

        drawScore(g);

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // react to key down events
        player.get(0).keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }

    private void drawBackground(Graphics g) {

        // draw a checkered background
        // draw rectangles to watch better the X, Y thing while in development
        g.setColor(Color.darkGray);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {

                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position
                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE
                    );

                }
            }
        }

    }

    // sets the snake's position for future drawing
    private void move(){

        /// sets the head's last position
        Point p = player.get(0).getPos().getLocation();
        player.get(0).setLastpos(p);

        // sets the rest of the body's last position
        for (int i = 1; i < player.size(); i++) {
            player.get(i).setLastpos(player.get(i).getPos());
            player.get(i).setPos(player.get(i-1).getLastpos());
        }

        setDirection();

    }

    // check if the snake collides into an apple and increases game speed
    private boolean checkCollisionFood() {

        for (food node: apple) {
            if (player.get(0).getPos().equals(node.getPos())) {
                apple.remove(node);
                points++;
                // for every 6 points increases the game speed
                if (points % 6 == 0)
                    // +3% game speed
                    timer.setDelay(timer.getDelay()-(timer.getDelay()/3));

                Point p = player.get(player.size()-1).getLastpos();
                player.add(new snake(p));
                return true;
            }
        }
        return false;

    }

    // check if the snake hit himself
    private void checkCollisionItself(){

        for (int i = 1; i < player.size()-1; i++)
            if (player.get(0).getPos().equals(player.get(i).getPos()))
                timer.stop();

    }

    // verifies and spawn a food in a grid that snake doesn't occupy
    private void spawnFood() {

        Point p = new Point(random.nextInt(ROWS), random.nextInt(COLUMNS));

        // verify if the new Point p is occupied by a snake node
        for (int j = 0; j < player.size() - 1; j++) {

            if (player.get(j).getPos().equals(p)) {
                p.setLocation(random.nextInt(ROWS), random.nextInt(COLUMNS));
                j = 0;
            }

        }

        // check if is possible to add a new apple
        if (player.size() + apple.size() < COLUMNS * ROWS)
            apple.add(new food(p));

    }

    // method that moves the head from the actual direction it is setted
    private void setDirection(){

        if (player.get(0).getDirection().equals("up"))
            player.get(0).getPos().move(player.get(0).getPos().x, player.get(0).getPos().y - 1);

        if (player.get(0).getDirection().equals("right"))
            player.get(0).getPos().move(player.get(0).getPos().x + 1, player.get(0).getPos().y);

        if (player.get(0).getDirection().equals("down"))
            player.get(0).getPos().move(player.get(0).getPos().x, player.get(0).getPos().y + 1);

        if (player.get(0).getDirection().equals("left"))
            player.get(0).getPos().move(player.get(0).getPos().x - 1, player.get(0).getPos().y);

    }

    private void drawScore(Graphics g){
        // setting fonts configs
        String s = Integer.toString(points);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 18));
        g.setColor(Color.white);
        g.drawString(s, WIDTH-20, HEIGHT-5);

    }
}
