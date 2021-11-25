import javax.swing.*;

public class main {

    public static void main (String[] args){

        windowInit windowInit = new windowInit();
        SwingUtilities.invokeLater(windowInit::mySnakes);

    }
}
