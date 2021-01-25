package bst;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.regex.*;

public class BSTPanel extends JPanel {
    private DrawingPanel drawPanel;
    private ProcedurePanel pp;
    private JPanel controlPanel;
    private JButton insert;
    private JButton delete;
    private JButton search;
    private JButton clear;
    private JTextField input;
    private JRadioButton fast;
    private JRadioButton slow;
    private JRadioButton no;
    private BinaryTree bst;
    private ButtonListener listener;

    private int speed;

    private  String command;

    public BSTPanel() {
        bst = new BinaryTree();
        command = "";

        speed = 800;

        setLayout(new BorderLayout());
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(150, 500));

        pp = new ProcedurePanel();

        listener = new ButtonListener();
        Jlistener JL = new Jlistener();

        fast = new JRadioButton("Fast Comparisons");
        fast.setSelected(true);
        slow = new JRadioButton("Slow Comparisons");
        no = new JRadioButton("No Comparisons");

        ButtonGroup group = new ButtonGroup();
        group.add(fast);
        group.add(slow);
        group.add(no);

        fast.addActionListener(JL);
        slow.addActionListener(JL);
        no.addActionListener(JL);

        insert = new JButton("Insert");
        delete = new JButton("Delete");
        input = new JTextField(4);
        clear = new JButton("Clear");
        search = new JButton("Search");
        controlPanel.add(input);
        controlPanel.add(insert);
        controlPanel.add(search);
        controlPanel.add(delete);
        controlPanel.add(clear);
        controlPanel.add(fast);
        controlPanel.add(slow);
        controlPanel.add(no);
        
        drawPanel = new DrawingPanel();
        
        insert.addActionListener(listener);
        search.addActionListener(listener);
	delete.addActionListener(listener);
	clear.addActionListener(listener);


        add(controlPanel, BorderLayout.LINE_START);
        add(drawPanel, BorderLayout.CENTER);
        add(pp, BorderLayout.PAGE_END);
    }

    
    public void paintPanel() {
        System.out.println("paint panel");
        repaint();
    }

    /**
     * Procedure Panel a JPanel where the procedure and instructions 
     * are drawn
     */
    class ProcedurePanel extends JPanel {

        public ProcedurePanel() {
            setPreferredSize(new Dimension(550, 100));
        }
	/**
	 * Draws the procedure String in the Procedure Panel
	 * @param g, the graphics object
	 */
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawString(bst.procedure, 20, 40);
        }

        public void draw() {
            paintImmediately(this.getVisibleRect());
        }
    }

    /**
     * DrawingPanel, a JPanel where the tree is displayed and animations 
     * shown.
     */
    class DrawingPanel extends JPanel{
        public DrawingPanel(){
            setPreferredSize(new Dimension(400, 500));
            setBackground(Color.white);
        }
	/**
	 * Draws the tree
	 * @param g, graphics object
	 */
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            bst.draw(g, drawPanel.getWidth(), drawPanel.getHeight());
        }
    }


    private class ButtonListener implements ActionListener {
	/**
	 * Links up the buttons Insert, delete, and clear with their Action events.
	 */
        public void actionPerformed (ActionEvent ae) {
            JButton button = (JButton) ae.getSource();
            //Insert button is clicked
            if(ae.getSource() == insert){
                //insert
		//regex checks the input
		if(Pattern.matches("^[0-9]+",input.getText())){
		    bst.insert(Integer.parseInt(input.getText()), drawPanel, pp,  speed);
		    command = "Inserting: " + input.getText();
		    input.setText("");
 
		}
		else{
		    command = "Invalid input";
		    input.setText("");
 
		}		

		
            }//Delete button is clicked
	    else if(ae.getSource() == delete){
                //delete
		//regex checks the input
		if(Pattern.matches("(^[0-9]+)",input.getText())){
		    command = "Deleting: " + input.getText();
		    bst.search(Integer.parseInt(input.getText()) , drawPanel, pp, speed);
		    bst.delete(Integer.parseInt(input.getText()), drawPanel, pp, speed);
		    input.setText("");
 
		}
		else{
		    command = "Invalid input";
		    input.setText("");
 
		}

	    }//Search button is clicked
	    else if (ae.getSource() == search) {
            if(Pattern.matches("(^[0-9]+)",input.getText())){
		    command = "Searching: " + input.getText();
		    bst.search(Integer.parseInt(input.getText()), drawPanel, pp, speed);
		    input.setText("");
            }
 
		}

	    //Clear button is clicked 
	    else if(ae.getSource() == clear){
		bst.clear();
		command = "Clear tree";
	    }
            repaint();
        }

    }

    
    private class Jlistener implements ActionListener {
	/**
	 * Sets the speed of the animation when the comparisson buttons are clicked.
	 * @param ae, action event 
	 */
        public void actionPerformed (ActionEvent ae) {
            JRadioButton button = (JRadioButton) ae.getSource();
            
            if (button == fast) {
                speed = 800;
            } else if (button == slow) {
                speed =  1600;
            } else {
                speed = 0;
            }

        }
    }
}
