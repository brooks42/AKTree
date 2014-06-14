/*
 * This class is free to use by who(m)ever, for forever, whatever.
 *
 * If you find it useful though, email me to let me know!
 */
package aktree;

import java.util.ArrayList;

/**
 *
 * @author Chris
 * @param <T>
 */
public class AKTree<T> {

    private AKTreeNode<T> root;

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
    private ArrayList<AKTreeNode<T>> children;

    /**
     * Creates a default AKTreeNode with a null parent and an empty list of
     * children.
     */
    public AKTreeNode() {
        children = new ArrayList();
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
     * @param children the children list for this node
     */
    public AKTreeNode(T data, AKTreeNode parent, ArrayList<AKTreeNode<T>> children) {
        this(data, parent);
        this.children = children;
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
     *
     *
     * @param parent the parent to set
     */
    public void setParent(AKTreeNode<T> parent) {
        this.parent = parent;
    }

    /**
     * @return the children
     */
    public ArrayList<AKTreeNode<T>> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(ArrayList<AKTreeNode<T>> children) {
        this.children = children;
    }
}
