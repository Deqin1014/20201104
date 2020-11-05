import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class God{
    void Dog(int[] x, int a, int b, int c){
        int tot no, rem_no, t_no;
        tot_no = b - a + 1;
        int[] t = new int[tot_no];
        for(int i=0; i<tot_no; i++){
            t[i] = a + 1;
        }
        rem_no = tot_no;
        for(int i=0; i<c; i++){
            t_no = (int) Math.floor(Math.random() * rem_no);
            x[i] = t[t_no];
            for(int j=t_no; j<(rem_no - 1); j++){
                t[j] = t[j + 1];
            }
            rem_no--;
        }
    }
}
class Apps extends JFrame{
    int p_num = 0;
    int pressl = -1, pressl2 = -1;
    int correct = 0;
    God god = new God();
    ImageIcon icon [] = new ImageIcon[17];
    JLabel lab[] = new JLabel[16];
    int a = 1, b = 16, c = 16;
    int d = b - a + 1;
    int data[] = new int[d] 
}