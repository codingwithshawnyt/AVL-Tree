//Shawn Ray
//Period 4
//AVLTree.java
/*The purpose of this file is to expand upon the SearchTree class
 * and establish the necessary functions to create a balanced height
 * Binary Search Tree for better algorithmic efficiency.
 */
public class AVLTree <E extends Comparable<E>> extends SearchTree<E>{
	private boolean addReturn;

    public boolean add(E data)
    {
        return insert(data);
    }

    public boolean insert(E data)
    {
        // This method should use a recursive helper.  It returns
    	// the tree that results from inserting data 
    	// into t. It has a similar structure to add from BST.  It
    	// starts by asking if tree is empty, and if so it sets the
    	// a new leaf node as the root. If tree is not empty you can compare
    	// data to the current node's data and take action based on this comparison.
    	// When you create a new leaf node you should 
		// increment the tree's size variable. When you return from a 
    	// recursive call that updates one of t's children
    	// (e.g. curr.leftChild = insertHelper(data, curr.left))
		// you should check the AVL property and possibly 
    	// call updateHeight(), and then recompute curr's height.
        overallRoot = insert((AVLNode<E>) overallRoot, data);
        return addReturn;
    }
    
    private AVLNode<E> insert(AVLNode<E> node, E data) {
        if (node == null) {
            addReturn = true;
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(node.data) < 0) {
            node.left = insert((AVLNode<E>) node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insert((AVLNode<E>) node.right, data);
        } else {
            addReturn = false;
            return node;
        }
        return balance(node);
    }

    private AVLNode<E> balance(AVLNode<E> node) {
        if (node.bf() > 1) {
            if (((AVLNode<E>) node.right).bf() < 0) {
                node.right = rotateRight((AVLNode<E>) node.right);
            }
            node = rotateLeft(node);
        } else if (node.bf() < -1) {
            if (((AVLNode<E>) node.left).bf() > 0) {
                node.left = rotateLeft((AVLNode<E>) node.left);
            }
            node = rotateRight(node);
        }
        node.updateHeight();
        return node;
    }


    // TODO: IMPLEMENT THE FOLLOWING METHODS BASED ON THE JAVADOC COMMENTS
  
    /**
     * Perform a single rotation to the right of a tree rooted at the current node.
     * Consider the following illustrations (called on the node A):
     *
     *        A       =>     B
     *       / \      =>    / \
     *      B   T3    =>  T1   A
     *     / \        =>      / \
     *   T1   T2      =>    T2   T3
     *
     * Note that A's original parent (if it exists) will need to become B's new
     * parent. 
     *
     * @return The new root of this subtree (node B).
     */
    public AVLNode<E> rotateRight(AVLNode<E> oldRoot) {
        // TODO: Implement this method.  Return the new root B.
        // Do not forget to change A's parent (if it exists) to be
        // aware of B as the new root by returning the new root and setting the
        // parent's pointer when we call rotateRight(node).
    	AVLNode<E> newRoot = (AVLNode<E>) oldRoot.left;
        oldRoot.left = newRoot.right;
        newRoot.right = oldRoot;
        oldRoot.updateHeight();
        newRoot.updateHeight();
        return newRoot;

    }

    /**
     * Perform a single rotation to the left of a tree rooted at the current node.
     * Consider the following illustrations (called on the node A):
     *
     *      A         =>       B
     *     / \        =>      / \
     *   T1   B       =>     A   T3
     *       / \      =>    / \
     *     T2   T3    =>  T1   T2
     *
     * Note that A's original parent (if it exists) will need to become B's new
     * parent. 
     *
     * @return The new root of this subtree (node B).
     */
    public AVLNode<E> rotateLeft(AVLNode<E> OldRoot) {
        // TODO: Implement this method. Return the new root B. 
        // Do not forget to change A's parent (if it exists) to be
        // aware of B as the new root by returning the new root and setting the
        // parent's pointer when we call rotateRight(node).
    	AVLNode<E> newRoot = (AVLNode<E>) OldRoot.right;
        OldRoot.right = newRoot.left;
        newRoot.left = OldRoot;
        OldRoot.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }

    public boolean remove(E data)
    {
    	if(overallRoot == null || !contains(data) )
    	{
    		return false ;
    	}
        overallRoot = removing((AVLNode<E>) overallRoot, data);
        super.size -= 1; 
        return true; 
    }
    
    private AVLNode<E> removing(AVLNode<E> root, E data)
    {
    	if(root == null)//this is where we need to add the node 
    	{
    		return null; 
    	}
    	else if(data.compareTo(root.data)> 0 )//we need to go right 
    	{	
    		root.right = removing((AVLNode<E>) root.right, data);
    		
    	}
    	else if(data.compareTo(root.data)< 0 )//we need to go left
    	{
    		root.left = removing((AVLNode<E>) root.left, data);
    	}
    	else if(data == root.data) {
    		if(root.left == null)//this means there thers either only a rigt child or no child. Both shoudl retur the right tree
    		{

    			root = (AVLNode<E>) root.right; 
    		}
    		else if(root.right == null) // this means we onyl have left child so retrn in 
    		{
    			root =  (AVLNode<E>) root.left; 
    		}
    		else
    		{

    			AVLNode<E> temp = (AVLNode<E>) minNode(root.right);//bring up the smallest node on the right. THen connect it to nodes subtrees but since tah would lead to it beign in the tree wice, we now remove that 
    			root.right = removing((AVLNode<E>) root.right, temp.data); 
    			temp.right = root.right; 
    			temp.left = root.left; 
    			root = temp; 
    		}
    	}
    	if(root == null) return null; 
    	
    	if(root.bf() > 1)//right heavy
    	{
    		if(((AVLNode<E>) root.right).bf() < 0 )//imbalanged below, so we need to fix the below 
    		{
    			root.right = rotateRight((AVLNode<E>)root.right); 
    		}
    		root = rotateLeft(root); //do the rotation like we need to 
    	}
    	else if(root.bf() < -1)//left heavy
    	{
    		if(((AVLNode<E>) root.left).bf() > 0 )//imbalanced so we need to fix the left tree 
    		{
    			root.left = rotateLeft((AVLNode<E>) root.left); 
    		}
    		root = rotateRight(root); //rotate like  we need to 
    	}
    	
        root.updateHeight();
    	return root; 
    }

    // This will help you with debugging. It prints the keys
   // on each level of the tree.
   public void treePrinter() {
    for (int level = 0; level < ((AVLNode<E>)overallRoot).height(); level++ ) {
        System.out.printf("Level %d: ", level);
        printLevel(level, (AVLNode<E>)overallRoot);
        System.out.println();
    }
}

    public void printLevel(int level, AVLNode<E> t) {
        if (t != null) {
            if (level == 0)
                System.out.printf(  "%s ", t.data);
            else {
                printLevel(level-1, (AVLNode<E>)t.left);
                printLevel(level-1, (AVLNode<E>)t.right);
            }
        }
    }
}