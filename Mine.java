import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Mine extends Component implements MouseListener, ActionListener {
    public Dimension getPreferredSize() {
        return new Dimension(220,220);
    }
    void reset() {
        // initialize your data structure
        repaint();
    }
    public void paint(Graphics g) {
        // draw your content
    }
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Exit"))          System.exit(0);
        else if (command.equals("New Game")) reset();
        else                                 System.out.println("Unknown Command");
    }
    public Mine(int x, int y) {
        // set up your user interface
        reset();
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {
        int x = e.getX()/20 + 1;
        int y = e.getY()/20 + 1;
        // do your mouse operation
        repaint();
    }
    public static void main(String[] argv) {
        new Mine(10,10);
    }
}