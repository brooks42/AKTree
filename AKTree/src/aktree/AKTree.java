/*
 * This class is free to use by who(m)ever, for forever, whatever.
 *
 * If you find it useful though, email me to let me know!
 */
package aktree;

import java.util.Objects;
import java.util.Scanner;

/**
 * The AKTree object is essentially a binary tree; that is, appending a leaf to
 * to the tree will cause it to be added as far down as it needs to be and
 * basically following a binary tree algorithm. The tree itself, however,
 * supports any object that implements the <code>Comparable</code> interface.
 *
 * This tree "allows" duplicates in that if you add a duplicate, the tree won't
 * be mad but also won't overwrite the value or add it elsewhere in the tree.
 * For example, adding 3 to the following tree
 *
 * 3
 *  / \
 * 1 4
 *
 * will result in the exact same tree (even the 3 value isn't overwritten).
 *
 * @author Chris
 * @param <T>
 */
public class AKTree<T extends Comparable> {

    // the tree's root node
    private AKTreeNode<T> root;

    // keeps track of the size of the tree, as long as you use add() 
    // and remove() methods. 
    private int size = 0;

    /**
     * creates an empty AKTree
     */
    public AKTree() {
        root = null;
    }

    /**
     * Returns the size of the tree, guaranteed accurate as long as you use the
     * tree's add() and remove() methods. Use tree.confirmSize() if you're not
     * sure this is the correct size.
     *
     * @return the size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * Recalculates the size of this tree by traversing it and summing its
     * leaves.
     */
    public void confirmSize() {
        SizeTraversal<T> traveler = new SizeTraversal();
        depthFirstTraversal(traveler);
        this.size = traveler.size;
    }

    /**
     * Adds the passed object to the tree in the appropriate place, ignoring
     * duplicate values (see the class-level doc).
     *
     * @param object the object to add to this tree.
     */
    public void add(T object) {
        if (root == null) {
            root = new AKTreeNode<>(object);
            size = 1;
        } else {
            add(object, root);
        }
    }

    // recursively adds the passed object to the tree in the proper place, 
    // ignoring duplicate values
    private void add(T object, AKTreeNode<T> parent) {
        if (object.compareTo(parent.getData()) == 0) {
            // ignore duplicates
        } else if (object.compareTo(parent.getData()) < 0) {
            // object is less than the parent, set or recurse the left child
            if (parent.getLeftChild() == null) {
                parent.setLeftChild(new AKTreeNode<>(object));
                size++;
            } else {
                add(object, parent.getLeftChild());
            }
        } else {
            // object is greater than the parent, set or recurse the right child
            if (parent.getRightChild() == null) {
                parent.setRightChild(new AKTreeNode<>(object));
                size++;
            } else {
                add(object, parent.getRightChild());
            }
        }
    }

    /**
     * Deletes the passed object from this AKTree and tries to rebuild the tree
     * using the following rules:
     *
     * - If the node has no children, whatever - If the node has one child, that
     * child is used as the node's replacement. - If the node has 2 children,
     * the left child will become the new node and this logic will be called
     * recursively on that node's children until the tree reaches a state where
     * there are no holes in the tree.
     *
     * @param object the object to remove
     */
    public void removeAndAttemptToRebuild(T object) {
        // TODO: actually do this
    }

    /**
     * Deletes the passed object and all of its children. Note that, as in life,
     * destroying the root will destroy the entire tree.
     *
     * @param object the object to remove from the tree
     */
    public boolean removeAndPrune(T object) {
        if (root == null) {
            return false;
        }

        AKTreeNode<T> node = findNodeForObject(object, root);

        if (node != null) {
            if (node.getParent() != null) {
                AKTreeNode parent = node.getParent();
                if (parent.getLeftChild() != null) {
                    if (parent.getLeftChild().equals(node)) {
                        parent.setLeftChild(null);
                    }
                }
                if (parent.getRightChild() != null) {
                    if (parent.getRightChild().equals(node)) {
                        parent.setRightChild(null);
                    }
                }
            } else {
                // this is the root, destroy it
                root = null;
            }
            return true;
        }
        return false;
    }

    // this either returns null, which means the object isn't in the tree, or it
    // returns a node that is the object's wrapper in this tree.
    private AKTreeNode findNodeForObject(T object, AKTreeNode node) {
        if (node == null) {
            return null;
        }
        // try the middle (this is a pre-order traversal, after all)
        if (node.getData().equals(object)) {
            return node;
        }
        // since it wasn't the root, try the left branch
        if (node.getLeftChild() != null) {
            AKTreeNode retNode = findNodeForObject(object, node.getLeftChild());
            if (retNode != null) {
                return retNode;
            }
        }
        // since it wasn't the root or left, try the right branch
        if (node.getRightChild() != null) {
            return findNodeForObject(object, node.getRightChild());
        }
        return null;
    }

    /**
     * Performs a pre-order depth-first traversal of this tree, using the passed
     * AKTreeTraverser. Performs the traversal's <code>traverse</code> method on
     * each object in this tree, in pre-order (root, then left node, then right
     * node).
     *
     * @param traversal an <code>AKTreeTraverser</code> object. Its
     * <code>traverse</code> method is called on each object in this tree.
     */
    public void depthFirstTraversal(AKTreeTraverser<T> traversal) {
        depthFirstTraversal(traversal, root, 1);
    }

    // performs a recursive depth-first traversal on the passed node.
    private void depthFirstTraversal(AKTreeTraverser<T> traversal, AKTreeNode<T> node, int level) {
        if (node != null) {
            traversal.traverse(node.getData(), level);
            if (node.getLeftChild() != null) {
                depthFirstTraversal(traversal, node.getLeftChild(), level + 1);
            }
            if (node.getRightChild() != null) {
                depthFirstTraversal(traversal, node.getRightChild(), level + 1);
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        ToStringTraversal<T> traveler = new ToStringTraversal();
        depthFirstTraversal(traveler);
        return traveler.toString();
    }

    /**
     * Example main, executes the tree.
     *
     * TODO: replace with unit tests
     *
     * @param args
     */
    public static void main(String[] args) {
        AKTree<String> tree = new AKTree<>();
        Scanner scanner = new Scanner("shame on you if you step through to the old dirty bastard brooklyn zoo");
        while (scanner.hasNext()) {
            String token = scanner.next();
            tree.add(token);
        }
        tree.confirmSize();
        System.out.println(tree.size());
        System.out.println(tree.toString());

        System.out.println("======");
        System.out.println("Found and pruned zoo: (should be true): " + tree.removeAndPrune("zoo"));
        tree.confirmSize();
        System.out.println("Should be 12: " + tree.size());
        System.out.println(tree.toString());

        System.out.println("Found and pruned shames: (should be false):" + tree.removeAndPrune("shames"));
        tree.confirmSize();
        System.out.println("Should be 12: " + tree.size());
        System.out.println(tree.toString());

        System.out.println("Found and pruned shame: (should be true): " + tree.removeAndPrune("shame"));
        tree.confirmSize();
        System.out.println("Should be 0: " + tree.size());
        System.out.println(tree.toString());

        System.out.println("======");
        System.out.println("Found and pruned through: (should be false because destroyed tree): " + tree.removeAndPrune("through"));
    }
}

/**
 * Helpful class to do the <code>toString()</code> method for our Tree and also
 * to act as an example of how to use an <code>AKTreeTraverser</code> instance.
 *
 * @author Chris
 * @param <T>
 */
class ToStringTraversal<T> extends AKTreeTraverser<T> {

    public StringBuilder string = new StringBuilder();

    @Override
    public void traverse(T object, int level) {
        if (level > 1) {
            string.append("|");
        }
        for (int i = 0; i < level; i++) {
            string.append("-");
        }
        string.append(object.toString()).append("\n");
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return string.toString();
    }
}

/**
 * Helpful class to do the <code>confirmSize()</code> method for our Tree and
 * also to act as an example of how to use an <code>AKTreeTraverser</code>
 * instance.
 *
 * @author Chris
 * @param <T>
 */
class SizeTraversal<T> extends AKTreeTraverser<T> {

    public int size = 0;

    // here as we traverse, we increment the public size variable
    @Override
    public void traverse(T object, int level) {
        size++;
    }
}

/**
 * Internal class for the AKTree to use to contain its root and children.
 *
 * @author Chris
 * @param <T>
 */
class AKTreeNode<T> {

    // the object contained by this node
    private T data;

    // the parent node to this one. If null, this is (in theory) the root node
    private AKTreeNode<T> parent;

    // a list of this node's children
    private AKTreeNode<T> leftChild, rightChild;

    /**
     * Creates a default AKTreeNode with a null parent and an empty list of
     * children.
     */
    public AKTreeNode() {
        leftChild = null;
        rightChild = null;
    }

    /**
     * Creates an AKTreeNode with the passed data, a null parent (so this is a
     * constructor for the root) and an empty list of children.
     *
     * @param data the object this node should contain
     */
    public AKTreeNode(T data) {
        this();
        this.data = data;
    }

    /**
     * Creates an AKTreeNode with the passed data, the passed parent node, and
     * an empty list of children.
     *
     * @param data the object this node should contain
     * @param parent the parent node for this node
     */
    public AKTreeNode(T data, AKTreeNode parent) {
        this(data);
        this.parent = parent;
    }

    /**
     * Creates an AKTreeNode with the passed data, the passed parent node, and
     * the passed list of children nodes.
     *
     * @param data the object this node should contain
     * @param parent the parent node for this node
     * @param leftChild the child less than this Node
     * @param rightChild the child greater than this Node
     */
    public AKTreeNode(T data, AKTreeNode parent, AKTreeNode<T> leftChild,
            AKTreeNode<T> rightChild) {
        this(data, parent);
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Returns the data object for this node.
     *
     * @return the object wrapped by this node
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data object wrapped by this node.
     *
     * @param data the data object for this node to wrap
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Returns the parent AKTreeNode of this node, or null if this is a root
     * node.
     *
     * @return the parent of this node, or null
     */
    public AKTreeNode<T> getParent() {
        return parent;
    }

    /**
     * Sets the parent of this node to the passed Node. Can be null, although
     * why would it be null? We're not running a forestry over here.
     *
     * @param parent the parent to set
     */
    public void setParent(AKTreeNode<T> parent) {
        this.parent = parent;
    }

    /**
     * @return the leftChild
     */
    public AKTreeNode<T> getLeftChild() {
        return leftChild;
    }

    /**
     * @param leftChild the leftChild to set. Can totally be null.
     */
    public void setLeftChild(AKTreeNode<T> leftChild) {
        if (leftChild != null) {
            leftChild.parent = this;
        }
        this.leftChild = leftChild;
    }

    /**
     * @return the rightChild
     */
    public AKTreeNode<T> getRightChild() {
        return rightChild;
    }

    /**
     * @param rightChild the rightChild to set. Can totally be null.
     */
    public void setRightChild(AKTreeNode<T> rightChild) {
        if (rightChild != null) {
            rightChild.parent = this;
        }
        this.rightChild = rightChild;
    }

    /**
     * Returns this node's Object's <code>equals</code> method's return value
     * for the passed object.
     *
     * @param object an object to test for equality with this node's object
     * @return the same value as data.equals(object)
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof AKTreeNode){
            return ((AKTreeNode)object).getData().equals(data);
        }
        return data.equals(object);
    }

    /**
     * Returns a hashCode of this Node.
     *
     * @return hashcode etc
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.data);
        return hash;
    }

    /**
     * Returns the (recursive) String value of this node.
     *
     * @return recursive strings
     */
    public String toString() {
        return data.toString()
                + (leftChild == null ? "" : "\n\t" + leftChild.toString())
                + (rightChild == null ? "" : "\n\t" + rightChild.toString());
    }
}
