import javax.swing.*;
import java.awt.*;


public class windowInit extends JFrame {

    public windowInit(){

        Image icon = Toolkit.getDefaultToolkit().getImage("./Images/icon.png");
        setIconImage(icon);
        setTitle("Snake :)");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void mySnakes(){

        map game = new map();
        add(game);
        addKeyListener(game);
        pack();

    }

}
