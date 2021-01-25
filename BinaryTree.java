package bst;

import java.awt.*;
import javax.swing.*;
import bst.BSTPanel.DrawingPanel;
import bst.BSTPanel.ProcedurePanel;


public class BinaryTree{
    private int ROW_HEIGHT = 50;
    private int NODE_SIZE= 30;


    private int treeDepth;
    public  String procedure = "";
    class Node{
	int key;
	Node left;
	Node right;
        Node parent;
        int index; //index in a row -leftmost child is 1, rightmost is n 
        int depth; //root = 1, root's children = 2, etc
        int offset;
        int xPos;
        int yPos;
        boolean isDrawn;
        Color color;
    }

    private Node root;
    
    /**
     * Constructor for BinaryTree, sets the initial root and treeDepth.
     */
    public BinaryTree(){
        treeDepth = 0;

	root = null;
    }
    
    /**
     * Creates a new Node and initialises its variables. 
     * @param key, the 
     * @return node, the newly created node. 
     */ 
    public Node newNode(int key){

	Node node = new Node();
	node.key = key;
        node.parent = null;
	node.right = null;
	node.left = null;

        node.isDrawn = false;
        node.index = 1;
        node.depth = 1;
        node.offset = 0;
        node.xPos = 0;
        node.yPos = 0;
        node.color = Color.BLACK;

	return node;

    }
    /**
     * Searches the tree for the key. Changes the colour of the Nodes as it searches through the tree.
     * Set the current node to red if the node != key. Set the Node to green if its equal to the key. 
     * @param tree,
     * @param key, the key to be searched for
     * @param panel, the DrawingPanel
     * @param pp, the ProcedurePanel
     * @param sleepTime, 
     * @return 1 if the key is found else 0     
     */ 
    private int bst_search(Node tree, int key, DrawingPanel panel, ProcedurePanel pp, int sleepTime){
        
	if(tree == null){
            procedure = "Node " + key + " not in tree.";
            pp.draw();
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
	    return 0;
	}

        //Key is found
	if(tree.key == key){
            //change node color to indicate status, redraw, change back and
            //redraw again
            procedure = "Found node: " + key;
            tree.color = Color.GREEN;
            pp.draw();
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
            try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
            tree.color = Color.BLACK;
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
	    return 1;
	}   

	//Key is smaller than current node -> search in the left subtree
	else if(tree.key > key){
            //change node color to indicate status, redraw, change back and
            //redraw again
            procedure = "Node " + tree.key + " greater than " + key + ". Search left subtree.";
            tree.color = Color.RED;
            pp.draw();
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
            try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
            tree.color = Color.BLACK;
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());

	    return bst_search(tree.left, key, panel, pp, sleepTime);
	}

	//Key is greater than the current node -> search in the right subtree
	else if(tree.key < key){
            //change node color to indicate status, redraw, change back and
            //redraw again
            procedure = "Node " + tree.key + " less than " + key + ". Search right subtree.";
            tree.color = Color.RED;
            pp.draw();
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
            try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
            tree.color = Color.BLACK;
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());

	    return bst_search(tree.right, key, panel, pp, sleepTime);
	}
	
	return 0;
    }

    /**
     * Calls bst_search on the root of the tree. 
     * @param key, the key to be searched
     * @param panel, the DrawingPanel
     * @param pp, the ProcedurePanel
     * @param sleepTime, the wait time between draw calls
     * @return found, 1 if the key is found else 0
     */
    public int search(int key, DrawingPanel panel, ProcedurePanel pp, int sleepTime){
	int found = bst_search(root, key, panel, pp, sleepTime);
	return found;
    }
    
    /**
     * Calls bst_insert on the root of the tree.
     * @param key, the key to be inserted
     * @param panel, the DrawingPanel
     * @param pp, the ProcedurePanel
     * @param sleepTime, the wait time between draw calls
     */
    public void insert(int key, DrawingPanel panel, ProcedurePanel pp, int sleepTime){
	root = bst_insert(root, key, panel, pp, sleepTime);
    }

    /**
     * Inserts the given key into the tree. 
     * @param tree, the Node we're trying to insert into.
     * @param key, the key to be inserted
     * @param panel, the DrawingPanel
     * @param pp, the ProcedurePanel
     * @param sleepTime, the wait time between draw calls
     * @return a new Node with the given key
     */ 
    private Node bst_insert(Node tree, int key, DrawingPanel panel, ProcedurePanel pp, int sleepTime){

	if(tree == null){ //create new tree
            procedure = "Inserted " + key + ".";
            pp.draw();
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
	    return newNode(key);
	}
	//key is smaller than current node -> insert in the left subtree
	else if(tree.key > key){
            procedure = "Node " + tree.key + " greater than " + key + ". Insert in left subtree.";
            pp.draw();
            tree.color = Color.RED;
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
            try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
            tree.color = Color.BLACK;
            
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
	    tree.left = bst_insert(tree.left, key, panel, pp, sleepTime);

            tree.left.parent = tree;
            tree.left.index = tree.index * 2 - 1;

	}
	//key is larger than the current node -> insert in the right subtree
	else if(tree.key < key){
            procedure = "Node " + tree.key + " less than " + key + ". Insert in right subtree.";
            pp.draw();
            tree.color = Color.RED;
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
            try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
            tree.color = Color.BLACK;
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
	    tree.right = bst_insert(tree.right, key, panel, pp, sleepTime);

            tree.right.parent = tree;
            tree.right.index = tree.index * 2;
            tree.right.depth = tree.depth + 1;

	}

        else { //we found the key in the tree
            procedure = key + " already in tree!";
            pp.draw();
            tree.color = Color.GREEN;
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
            try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
            tree.color = Color.BLACK;
            draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
        }

        treeDepth++;
	return tree;

    }

    /**A draw method that can be called outside of the bst.
     * Lets other classes tell the bst to redraw itself.
     * @param g the graphics object to draw to
     * @param width the width of the panel to draw to
     * @param height the height of the panel to draw to
     */
    public void draw(Graphics g, int width, int height){
        if(root != null){
            //calls setxPos to make sure each node has the right x position
            setxPos(width, height, root);
            //calls draw node to draw the nodes
            drawNode(g, width, height, root);
        }
    }

    /**
     * Recursively sets the x position of nodes in the tree.
     * Checks if the node has been drawn before - if not, calculates its
     * initial position and adjust its predecessors as required. Otherwise
     * calculates its position based on its parents to allow resizing of the
     * window.
     * @param width the width of the drawing surface
     * @param height the height of the drawing surface
     * @param node the node to set the position of
     */
    private void setxPos(int width, int height, Node node){
        if (!node.isDrawn) {//this is a new node
            if (node.parent != null) { //not the root
                if(node == node.parent.left){ //left child
                    node.isDrawn = true;
                    fixLeftParent(node, width, height); //move parent if need be
                    node.xPos = node.parent.xPos - NODE_SIZE; //set position to left of parent
                }else{ //right child
                    node.isDrawn = true;
                    fixRightParent(node, width, height); //move parent if need be
                    node.xPos = node.parent.xPos + NODE_SIZE; //set position to right of parent
                }
                    
            }else{ //is the root
                node.xPos = width/2 - NODE_SIZE/2; //set root to the middle of the drawing panel
            }
            node.isDrawn = true;
        } else {
	    if (node.parent == null) { //root
                node.xPos = width/2 - NODE_SIZE/2; //set to center of panel
	    }else if (node == node.parent.left) { //left child
		node.xPos = node.parent.xPos - NODE_SIZE + node.offset; //set to left of parent 
	    }else { //right child
		node.xPos = node.parent.xPos + NODE_SIZE + node.offset; //set to right of parent
	    }
        }
        if (node.left != null) {
            setxPos(width, height, node.left); //recurse left
        }
        if (node.right != null) {
            setxPos(width, height, node.right); //recurse right
        }
    }

    /**
     * Draws each node in the tree.
     * recursively draws each node in the tree, along with the connecting
     * lines.
     * @param g Graphics object to draw to.
     * @param width the width of the panel
     * @param height the height of the panel
     * @param node the Node to draw
     */
    private void drawNode(Graphics g, int width, int height, Node node){
        if(node.parent != null){ 
            node.depth = node.parent.depth + 1; //sets the node depth to one deeper than its parent
        }

        node.yPos = node.depth * ROW_HEIGHT; //y position is the nodes depth times the row height

        g.setColor(node.color); //set the color to the nodes color
        g.drawOval(node.xPos, node.yPos, NODE_SIZE, NODE_SIZE); //draw node circle
        g.setColor(Color.BLACK); //set color to black

        FontMetrics fm = g.getFontMetrics();
        String keyString = Integer.toString(node.key);
        g.drawString(keyString, node.xPos + NODE_SIZE/2 - fm.stringWidth(keyString)/2, node.yPos + (NODE_SIZE + fm.getAscent())/2); //draw the nodes key

        if(node.left != null){ 
            drawNode(g, width, height, node.left); //recurse left
            g.drawLine(node.xPos + NODE_SIZE/2, node.yPos + NODE_SIZE, node.left.xPos + NODE_SIZE/2, node.left.yPos); //draw connecting line
        }
        if(node.right != null){
            drawNode(g, width, height, node.right); //recurse right
            g.drawLine(node.xPos + NODE_SIZE/2, node.yPos + NODE_SIZE, node.right.xPos + NODE_SIZE/2, node.right.yPos); //draw connecting line
        }
    }

   
    /**
     * Moves the parent of a left child to make space for it.
     * Works up the tree moving nodes as needed to accomodate a new node.
     * @param node,
     * @param width,  
     * @param height, 
     */
    private void fixLeftParent(Node node, int width, int height){
        Node start = node; //the node we started out on
	while (node.parent != null){ //while not the root
	    if (node == node.parent.right) { //have we reached a right child 
		Node top = node; //the top of the current branch
		while (node.parent != null) { //find the root's child 
		    if (node.parent.parent == null) { //child of root
			if (node == node.parent.right) { //we're on the right side of the tree, so our top node must be an outer child 
			    top.offset += NODE_SIZE; //move the top node of the working branch out
			    setxPos(width, height, top); //set children psoitions
			    if (top.parent.parent != null) { //if the top of the branch wasn't a child of the root 
				setxPos(width, height, fixOuter(top)); //fixOuter on top, setx on result to fix children
			    }
			    break;
			}else{ //our start node is an outer child
			    setxPos(width, height, fixOuter(start)); //fixOuter on start, setx to fix children
			    break;
			}   
		    }
		    node = node.parent; //go up tree
		}
		break;
	    }else{
		node = node.parent; //go up tree
	    }
	}
    }
    
    
    /**Moves the parent of a right child to make space for it.
     * Works up the tree moving nodes as needed to accomodate a new node.
     * @param node the node to work up from
     * @param width the width of the panel, to pass to setxpos
     * @param height the height of the panel, to pass to setxpos
     */
    private void fixRightParent(Node node, int width, int height) {
        Node start = node; //our starting node
        while (node.parent != null) { //while not on the root
            if (node == node.parent.left) {//we have reached a left child
		Node top = node; //the top of the current branch
		while (node.parent != null) { //find the root's child 
		    if (node.parent.parent == null) { //child of root
			if (node == node.parent.left) { //we're on the left side of the tree, so our top node must be an outer child
			    top.offset -= NODE_SIZE; //move top out
			    setxPos(width, height, top); //fix children
			    if (top.parent.parent != null) { //if top is not a child of root
				setxPos(width, height, fixOuter(top)); //fixouter on top and setx on result to fix children
			    }
			    break;
			}else{
			    setxPos(width, height, fixOuter(start)); //fixouter on starting node and setx on result to fix children
			    break;
			}   
		    }
		    node = node.parent; //go up tree
		}
		break;

            }else {
		node = node.parent; //go up tree
            }
        }
    }

    /**Called on outer children to move nodes above them out.
     * Moves nodes out to make space for a new node.
     * @param node the Node to start working up from.
     */
    private Node fixOuter(Node node){
        int branch; //0 = left, 1 = right
        if (node == node.parent.right ){
            branch = 1;
        }else{
            branch = 0;
        }
        node = node.parent;
        while(node.parent != null){
            if (branch == 1) { //we started on a right child
                if (node.parent.left == node) { //we have found top of branch
                    node.offset -= NODE_SIZE; //move in
                    node = node.parent; //move up tree
                    while(node.parent != null) { //find the top of the next branch up
                        if(node.parent.right == node) { //top
                            node.offset += NODE_SIZE; //move out
                            break;
                        }
                        node = node.parent; //go up tree
                    }

                    break;
                }
            }else{ //we started on a left child
                if (node.parent.right == node) { //we have found the top of branch
                    node.offset += NODE_SIZE; //move in
                    node = node.parent; //move up tree
                    while(node.parent != null) { //find the top of the next branch up
                        if(node.parent.left == node) { //top
                            node.offset -= NODE_SIZE; //move out
                            break;
                        }
                        node = node.parent; //move up tree
                    }

                    break;
                }
            }
            node = node.parent; //move up tree
        }
        if (node.parent != null && node.parent.parent != null) { //if we did not reach child of root
	    fixOuter(node); //we still have more branches to fix
        }
        return node; //return the node we end up on
    }

    

    
    
    /**
     * Calls the bst_delete method on root.
     * @param key, the key to delete
     */
    public void delete(int key, DrawingPanel panel, ProcedurePanel pp, int sleepTime){
	bst_delete(root, key, panel, pp, sleepTime);
        if(root != null){
            bst_clear_drawing(root);
        }
    }
    
    /**
     * Clears the drawing
     * @param node, the node to be cleared
     */ 
    private void bst_clear_drawing(Node node){
        node.isDrawn = false;
        node.depth = 1;
        node.offset = 0;
        node.xPos = 0;
        node.yPos = 0;

        if(node.left != null){
            bst_clear_drawing(node.left); //clear left subtree
        }
        if(node.right != null){
            bst_clear_drawing(node.right); //clear right subtree
        }

    }

    
    /** 
     * Finds the successor (leftmost value in the right subtree)
     * @param tree, the current node we're checking
     * @param panel, the DrawingPanel
     * @param pp, the ProcedurePanel
     * @param sleepTime, the wait time between draw calls
     * @return tree, the left most node in the tree.
     */
    public Node findLeftMost(Node tree, DrawingPanel panel, ProcedurePanel pp, int sleepTime){
        pp.draw();
	try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
	if(tree.left == null){
	    tree.color = Color.BLUE;
	    procedure = "Successor found - Node " + tree.key;
	    pp.draw();
	    draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
	    try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
	    tree.color = Color.BLACK;
	    return tree;
	}
	tree.color = Color.RED;
	procedure = "Search for successor - find leftmost node";	
	pp.draw();
	draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
	try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
	tree.color = Color.BLACK;
	return findLeftMost(tree.left, panel, pp, sleepTime);
    }

    /**
     *
     * Deletes the selected key from the tree
     * @param tree, the tree containing the node
     * @param key, the value to be deleted
     * @param panel, the drawing panel
     * @param pp, the procedure panel
     * @param sleepTime, 
     * @return t, the updated node
     */
    public Node bst_delete(Node t, int key,  DrawingPanel panel, ProcedurePanel pp, int sleepTime){

        Node temp = t;

        if(bst_search(t, key, panel, pp, sleepTime) == 0){
            return null;
        }

        if (t.key == key) {
            //Node is a leaf (has no children)
            if (t.left == null && t.right == null) {
                procedure = "Node " + t.key + " is a leaf. Deleting node " + t.key;
                pp.draw();
		try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
                t = null;
                return t;
            }
            //Node has 2 children
            else if (t.left != null && t.right != null) {
                //set t has the nodes successor
                procedure = "Searching for successor in right subtree";
                pp.draw();
                t = findLeftMost(t.right, panel, pp, sleepTime);
                //checks that t is not equal to the node being deleted's right
                if (t != temp.right) {
                    //Updates t's right if it has a right
                    if (t.right != null) {
                        t.parent.left = t.right;
                        t.right.parent = t.parent;
                    } else {
                        t.parent.left = null;
                    }
                    t.right = temp.right;
                }

                t.left = temp.left;
                t.parent = temp.parent;
                
                //if t is the root
                if (t.parent == null) {
                    root = t;
                } else if (t.parent.left == temp) {
                    t.parent.left = t;
                } else {
                    t.parent.right = t;
                }
                //set the parent for t's right if it has a right child
                if (t.right != null) {
                    t.right.parent = t;
                }
                //set the parent for t's left if it has a left child
                if (t.left != null) {
                    t.left.parent = t;
                }
                return t;
            }
            //Node only has a left child
            else if (t.left != null && t.right == null) {
                procedure = "Node " + t.key + " only has a left child. Replacing " + key + " with left child.";
                pp.draw();
                t.left.color = Color.BLUE;
                draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
                try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
                t.left.color = Color.BLACK;
                t = t.left;
                t.parent = temp.parent;

                //if t is the root
                if (t.parent == null) {
                    root = t;
                } else if (t.parent.left == temp) {
                    t.parent.left = t;
                } else {
                    t.parent.right = t;
                }
                if (t.left != null) {
                    t.left.parent = t;
                }
                return t;
            } else { //Node only has a right child
                procedure = "Node " + t.key + " only has a right child. Replacing " + key + " with right child " + t.right.key;
                pp.draw();
                t.right.color = Color.BLUE;
                draw(panel.getGraphics(), panel.getWidth(), panel.getHeight());
                try { Thread.sleep(sleepTime); } catch  (InterruptedException e) { }
                t.right.color = Color.BLACK;
                t = t.right;
                t.parent = temp.parent;
                //if t is the root
                if (t.parent == null) {
                    root = t;
                } else if ( t.parent.left == temp) {
                    t.parent.left = t;
                } else {
                    t.parent.right = t;
                }
                if (t.right != null) {
                    t.right.parent = t;
                }
                return t;
            }
            //If key is smaller than the current Node then delete from the left subtree 
        } else if (t.key > key) {
            t.left = bst_delete(t.left, key, panel, pp, sleepTime);
            pp.draw();
            return t;
            //Else if the key is bigger than the current Node then delete from the right subtree
        } else {
            t.right = bst_delete(t.right, key, panel, pp, sleepTime);
            pp.draw();
            return t;
        }
    }




    /** 
     * Clears the tree
     */
    public void clear(){
	root = null;
    }
}
