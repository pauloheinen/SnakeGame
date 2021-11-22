import javax.swing.*;


public class windowInit extends JFrame {

    public windowInit(){

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void teste(){

        map game = new map();
        add(game);
        addKeyListener(game);
        pack();

    }

}
