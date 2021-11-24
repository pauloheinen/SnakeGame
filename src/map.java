import org.w3c.dom.ranges.Range;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

// https://www.youtube.com/watch?v=PJLLDpaLjds&ab_channel=LearnCodeByGaming
// https://github.com/learncodebygaming/java_2d_game/blob/master/Board.java

public class map extends JPanel implements KeyListener, ActionListener {

    private Timer timer;
    private Random random = new Random();

    private ArrayList<snake> player = new ArrayList<snake>();
    private food apple;
    private int points = 0;

    // map's configs
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static final int DELAY = 380;
    public static final int TILE_SIZE = 15;
    public static final int COLUMNS = WIDTH/TILE_SIZE;
    public static final int ROWS = HEIGHT/TILE_SIZE;



    public map(){

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        // initialize the game state
        player.add(new snake());
        spawnFood();
        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.

        move();
        player.get(0).setMovementSetted(false);
        // prevent the player from disappearing off the board
        if (player.get(0).tick())
            timer.stop();
        checkCollisionItself();
        checkCollisionFood();
        System.out.println(timer.getDelay());

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
        for (snake node : player){
            node.draw(g, this);

        }
        apple.draw(g, this);

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

        /*
        // for debug purpose
        System.out.println("-------------[" + player.size() + "]-------------");
        for (int i = 0; i < player.size(); i++) {
            System.out.println("[" + i + "]");
            System.out.println(player.get(i).getPos());
            System.out.println(player.get(i).getLastpos());
            System.out.println();
        }
        System.out.println("----------------------------");
         */

    }

    private void checkCollisionFood() {

        if (player.get(0).getPos().equals(apple.getPos())) {
            Point p = player.get(player.size()-1).getLastpos();
            player.add(new snake(p));
            spawnFood();
        }

    }

    private void checkCollisionItself(){

        for (int i = 0; i < player.size()-1; i++) {
            for (int j = i+1; j < player.size()-1; j++) {

                if (player.get(j).getPos().equals(player.get(i).getPos()))
                    timer.stop();

            }
        }

    }

    private void spawnFood(){

        Point p = new Point(random.nextInt(ROWS), random.nextInt(COLUMNS));

        apple = new food(p);
        points++;

        // for every 5 points increases the game speed
        if (points % 5 == 0)
            // +10% game speed
            timer.setDelay(timer.getDelay()-(timer.getDelay()/10));

    }

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


}
