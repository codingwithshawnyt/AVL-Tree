public class SearchTreeNode<E> {
    public E data;             // data stored at this node
    public SearchTreeNode<E> left;   // reference to left subtree
    public SearchTreeNode<E> right;  // reference to right subtree

    // Constructs a leaf node with the given data
    public SearchTreeNode(E data) {
        this(data, null, null);
    }

    // Constructs a branch node with the given data and links
    public SearchTreeNode(E data, SearchTreeNode<E> left, SearchTreeNode<E> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}