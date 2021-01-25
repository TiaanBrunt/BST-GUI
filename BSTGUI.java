package bst;

import javax.swing.JFrame;
import java.awt.Dimension;

/** A gui to show a BST, and demonstrate its operations.
 * @author Oliver Robson-Alexander + Tiaan Stevenson-Brunt
 */
public class BSTGUI {
    public static void main (String[] args){
        JFrame frame = new JFrame("BSTGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().add(new BSTPanel());
        frame.setMinimumSize(new Dimension(700, 700));
    
        frame.pack();
        frame.setVisible(true);
    }
}
