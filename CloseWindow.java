import java.awt.*;
import java.awt.event.*;
public class CloseWindow extends WindowAdapter implements ActionListener {
    Window w;
    boolean quit=false;
    CloseWindow(Window w) {
        super();
        this.w = w;
    }
    CloseWindow(Window w, boolean quit) {
        super();
        this.w = w;
        this.quit = quit;
    }
    public void actionPerformed(ActionEvent e) {
        w.dispose();
        if (quit) {
            System.exit(0);
        }
    }
    public void windowClosing(WindowEvent e) {
        w.dispose();
        if (quit) {
            System.exit(0);
        }
    }
}