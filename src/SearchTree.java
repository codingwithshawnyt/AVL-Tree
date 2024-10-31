import java.util.Collection;

// The SearchTree class implements a binary search tree whose nodes store objects of generic type E.
// E must extend Comparable interface, so objects of type E can be compared for order.
public class SearchTree<E extends Comparable<E>> {

    // Reference to the overall root of the tree
    SearchTreeNode<E> overallRoot;
    
    // Keeps track of the number of elements in the tree
    int size = 0;

    // Default constructor for SearchTree.
    // Initializes an empty tree by setting root to null.
    public SearchTree() {
        overallRoot = null; // initialize to an empty tree
    }

    // Constructor for SearchTree that takes an object of type E as a parameter.
    // Initializes a tree with one node containing the parameter value.
    public SearchTree(E value) {
        overallRoot = new SearchTreeNode<>(value);  // create just one node with value
        size++;  // increment size as we have added a single node
    }

    // Constructor for SearchTree that takes a collection of objects of type E.
    // Initializes a tree and adds each object in the collection into the tree.
    public SearchTree(Collection<E> list) {
        this.overallRoot = null; // start with an empty tree
        for (E value : list) { // iterate over each element in the list
            this.add(value); // add the current element to the tree
        }
        // After this loop, all elements of list have been added to tree
    }
    
 // Returns the size of the tree (i.e., the number of elements).
    public int getSize() {
        return size;
    }

    // Clears all elements from the tree.
    public void clear() {
        overallRoot = null;  // remove all nodes by setting root to null
        size = 0;  // tree has no elements, so size is set to 0
    }

    // Returns the smallest (minimum) value stored in the tree.
    public E smallest() {
        if (isEmpty())
            throw new IllegalStateException("Empty tree");  // can't find smallest of empty tree

        // Start from the root node.
        SearchTreeNode<E> temp = overallRoot;

        // Move to the left-most node in the tree, which is the smallest value.
        while (temp.left != null) {
            temp = temp.left;
        }

        // Return the smallest value.
        return temp.data;
    }

    // Returns the largest (maximum) value stored in the tree.
    public E largest() {
        if (isEmpty())
            throw new IllegalStateException("Empty tree");  // can't find largest of empty tree

        // Start from the root node.
        SearchTreeNode<E> temp = overallRoot;

        // Move to the right-most node of the tree, which is the largest value.
        while (temp.right != null) {
            temp = temp.right;
        }

        // Return the largest value.
        return temp.data;
    }
    
 // Returns the number of leaf nodes in the tree.
    public int countLeaves() {
        // Invokes private helper method to perform recursive leaf count starting from overallRoot.
        return countTheLeaves(overallRoot);
    }

    // Check whether the tree is empty or not.
    public boolean isEmpty() {
        // If overallRoot is null, it indicates tree is empty so it returns true. Otherwise, false.
        return overallRoot == null;
    }

    // Adds a new value to the tree preserving binary search tree property.
    public boolean add(E value) {
        // First we check whether the value already exists inside the tree by using helper ifExists method.
        if (ifExists(value, overallRoot)) {
            return false;  // If it already exists, we don't add it again (No duplicates allowed). Return false.
        }
        // If it doesn't exist, we add it using the private helper method addNode.
        overallRoot = addNode(overallRoot, value); 
        size++;  // Increment the size of tree as we add a node.
        return true; // Return true as we successfully added the new value.
    }

    // Helper method for add operation which is recursive.
    private SearchTreeNode<E> addNode(SearchTreeNode<E> root, E value) {
        // If we reach null node, we found appropriate place according to binary search tree properties. Create a new node with value.
        if (root == null) {
            return new SearchTreeNode<E>(value);
        } 
        else if (value.compareTo(root.data) < 0) {  // Check whether the new value is less than data of current node.
            // If it's less, new value should be added to the left subtree.  
            root.left = addNode(root.left, value);  
        } 
        else {
            // Otherwise, new value should be added to the right subtree.
            root.right = addNode(root.right, value);
        }
        // After recursion, return the root node of the (potentially new) subtree.
        return root;
    }

 // Returns true if the tree contains the given value, false otherwise.
    public boolean contains(E value) {
        // Calls the helper method ifExists to determine the presence of value in the tree
        return ifExists(value, overallRoot);
    }

    // Returns the node that stores the smallest value in the tree (used in the remove method).
    protected static <T extends Comparable<T>> SearchTreeNode<T> minNode(SearchTreeNode<T> root) {
        SearchTreeNode<T> temp = root;
        // The smallest value is stored in the leftmost node.
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    // Removes a node from the tree that stores a given value.
    // Returns true if successful, false if there is no such node.
    public boolean remove(E num) {
        // Checks whether the node to be deleted exists in the tree.
        if (!ifExists(num, overallRoot)) {
            return false;
        }
        // Calls the helper method that removes the node.
        overallRoot = removeNode(overallRoot, num);
        size--;  // Decreases the size of the tree as a node has been removed.
        return true;  // Return true as removal was successful.
    }

    // Helper method for removing a node from the tree. It takes the root of a (sub)tree and the value to be removed.
    private SearchTreeNode<E> removeNode(SearchTreeNode<E> root, E num) {
        // If the root is null then return null - used for reaching the end of a branch in recursion.
        if (root == null) {
            return null;
        }

        // If the value to be removed is less/greater than the root's value then it will be in the left/right subtree.
        if (num.compareTo(root.data) < 0) {
            root.left = removeNode(root.left, num);
        } else if (num.compareTo(root.data) > 0) {
            root.right = removeNode(root.right, num);
        } else {
            // If the values are equal then this is the node to be removed.
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            root.data = minNode(root.right).data;
            root.right = removeNode(root.right, root.data);
        }
        // Return the root of the potentially updated (sub)tree.
        return root;
    }

    // Returns the string representation of a tree, where the values are sorted in ascending order.
    public String toString() {
        // It calls the private helper method printTree.
        return printTree(overallRoot);
    }

    // Private helper method for the toString. It generates the in-order traversal of the tree.
    private String printTree(SearchTreeNode<E> root) {
        if (root == null) {
            return "";
        }
        // First, visit the left subtree, then the node itself, then the right subtree.
        String inOrderTree = printTree(root.left);
        inOrderTree += root.data + " ";
        inOrderTree += printTree(root.right);
        return inOrderTree;
    }

    // Helper function to check if a value exists in the tree.
    private boolean ifExists(E value, SearchTreeNode<E> node) {
        if (node == null) {
            return false;
        }
        if (node.data.equals(value)) {
            return true;
        }
        // If the value is less/greater than the node's value, it will be in the left/right subtree.
        return value.compareTo(node.data) < 0 
            ? ifExists(value, node.left) 
            : ifExists(value, node.right);
    }

 // Recursive function to count the leaves in the tree.
    private int countTheLeaves(SearchTreeNode<E> node) {
        // Base case: if node is null, then return 0, because null nodes are not leave nodes.
        if (node == null)
            return 0;
        
        // If both left child and right child are null for a node, then it is a leaf node. We return 1.
        if (node.left == null && node.right == null)
            return 1;
        else
            // Recursive case: For a non-leaf node, we check its left and right subtree and those results are added together.
            // If the node is not a leaf node, recursively count the leaves in its left and right subtrees and return their total.
            return countTheLeaves(node.left) + countTheLeaves(node.right);
    }
}