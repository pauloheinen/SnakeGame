import org.w3c.dom.ranges.Range;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

// https://www.youtube.com/watch?v=PJLLDpaLjds&ab_channel=LearnCodeByGaming
// https://github.com/learncodebygaming/java_2d_game/blob/master/Board.java

public class map extends JPanel implements KeyListener, ActionListener {

    private Timer timer;

    private snake player;
    private food apple;

    private int count = 0;
    private Random random = new Random();

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static final int DELAY = 300;
    public static final int TILE_SIZE = 15;
    public static final int COLUMNS = WIDTH/TILE_SIZE;
    public static final int ROWS = HEIGHT/TILE_SIZE;



    public map(){

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        // initialize the game state
        player = new snake();
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

        // prevent the player from disappearing off the board
        move();
        player.tick();
        checkCollisionFood();
        checkCollisionBorder();

        System.out.println(player.getPos());
        System.out.println(apple.getPos());
        System.out.println(player.getLength());

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
        player.draw(g, this);
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
        player.keyPressed(e);
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

        if (player.getDirection().equals("up")){
            player.getPos().translate(0, -1);
        }
        if (player.getDirection().equals("right")){
            player.getPos().translate(1, 0);
        }
        if (player.getDirection().equals("down")){
            player.getPos().translate(0, 1);
        }
        if (player.getDirection().equals("left")){
            player.getPos().translate(-1, 0);
        }

    }

    private void checkCollisionBorder() {
        if (player.isCollision())
            timer.stop();
    }

    private void checkCollisionFood() {

        if (player.getPos().equals(apple.getPos())) {
            spawnFood();
            player.setLength(player.getLength() + 1);
            newSnake();

        }

    }

    private void spawnFood(){

        int x = random.nextInt(ROWS);
        int y = random.nextInt(COLUMNS);

        apple = new food(x, y);

    }

    private void newSnake(){
        snake player1 = new snake(player.getPos().x-1, player.getPos().y-1);
    }
}
