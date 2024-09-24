//Abu Sayiem
//CS-284E
//I pledge that I abide by the Stevens Honor System.

import java.util.Random;
import java.util.Stack;

class Node<E extends Comparable<E>> {
    public E data;
    public int priority;
    public Node<E> left;
    public Node<E> right;

    /**
     * Constructs a new node with the given data and priority.
     * @param data the data to be stored in the node
     * @param priority the priority of the node
     * @throws IllegalArgumentException if the provided data is null
     */

    public Node(E data, int priority) {
        if (data == null) throw new IllegalArgumentException("Data cannot be null");
        this.data = data;
        this.priority = priority;
        this.left = null;
        this.right = null;
    }
    /**
     * Rotates the node to the right, making the left child of this node the new root of the subtree.
     * @return the new root of the subtree after rotation
     */
    Node<E> rotateRight(){
        Node<E> newRoot = this.left;
        this.left = newRoot.right;
        newRoot.right = this;
        return newRoot;
    }

    /**
     * Rotates the node to the left, making the right child of this node the new root of the subtree.
     * @return the new root of the subtree after rotation
     */
    Node<E> rotateLeft(){
        Node<E> newRoot = this.right;
        this.right = newRoot.left;
        newRoot.left = this;
        return newRoot;
    }
}
/**
 * Implements a Treap, a randomized binary search tree structure that maintains a dynamic set of ordered keys
 * and allows binary search tree operations. It uses random priorities in addition to the key to maintain balance.
 * @param <E> the type of the elements in this Treap, which must be Comparable
 */
public class Treap<E extends Comparable<E>>{
    private Random priorityGenerator;
    private Node<E> root;
    private boolean deleteReturn;
    /**
     * Constructs an empty Treap with no root and a default random priority generator.
     */
    public Treap(){
        this.priorityGenerator = new Random();
    }
    /**
     * Constructs an empty Treap with a specific seed for the random priority generator.
     * @param seed the seed for generating random priorities
     */
    public Treap(long seed){
        this.priorityGenerator = new Random(seed);
    }
    /**
     * Adds an element to the Treap with a randomly assigned priority.
     * @param key the element to add
     * @return true if the element was added, false if it already exists
     */
    public boolean add(E key){
        int priority = priorityGenerator.nextInt(Integer.MAX_VALUE);
        return  add(key, priority);
    }
    /**
     * Adds an element with the specified priority to the Treap.
     * @param key the element to add
     * @param priority the priority of the element
     * @return true if the element was added, false if it already exists
     */
    public boolean add(E key, int priority) {
        if (root == null) {
            root = new Node<>(key, priority);
            return true;
        }
    
        Stack<Node<E>> pathStack = new Stack<>();
        Node<E> current = root;
        while (current != null) {
            int cmp = key.compareTo(current.data);
            if (cmp == 0) {
                // Node with this key already exists
                return false;
            }
            pathStack.push(current);
            if (cmp < 0) {
                if (current.left == null) {
                    current.left = new Node<>(key, priority);
                    reheap(pathStack, true);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node<>(key, priority);
                    reheap(pathStack, false);
                    return true;
                }
                current = current.right;
            }
        }
        // Unreachable
        return false;
    }
    
    private void reheap(Stack<Node<E>> pathStack, boolean isLeftChild) {
        Node<E> child = isLeftChild ? pathStack.peek().left : pathStack.peek().right;
        while (!pathStack.isEmpty() && pathStack.peek().priority < child.priority) {
            Node<E> parent = pathStack.pop();
            if (isLeftChild) {
                child = parent.rotateRight();
            } else {
                child = parent.rotateLeft();
            }
            if (!pathStack.isEmpty()) {
                if (pathStack.peek().left == parent) {
                    pathStack.peek().left = child;
                } else {
                    pathStack.peek().right = child;
                }
            } else {
                root = child;
            }
            isLeftChild = pathStack.isEmpty() || (pathStack.peek().left == child);
        }
    }
    /**
     * Attempts to delete an element from the Treap.
     * @param key the element to delete
     * @return true if the element was successfully deleted, false otherwise
     */
    public boolean delete(E key) {
        deleteReturn = false; // Assume deletion fails
        root = deleteHelp(root, key);
        return deleteReturn;
    }
    /**
     * Helper method for deleting a node from the Treap. This method is called recursively.
     * @param node the current node being inspected
     * @param key the key of the node to delete
     * @return the new root of the subtree after deletion adjustments
     */
    private Node<E> deleteHelp(Node<E> node, E key) {
        if (node == null) {
            // Base case: key not found
            return null;
        }
        int compare = key.compareTo(node.data);
        if (compare < 0) {
            // Key is smaller than node's key; go left
            node.left = deleteHelp(node.left, key);
        } else if (compare > 0) {
            // Key is larger than node's key; go right
            node.right = deleteHelp(node.right, key);
        } else {
            // Key found
            deleteReturn = true; // Mark successful deletion
            if (node.left == null && node.right == null) {
                // Node is a leaf
                return null;
            } else if (node.left == null) {
                // Node has only right child
                return node.right;
            } else if (node.right == null) {
                // Node has only left child
                return node.left;
            } else {
                // Node has two children, choose the one with higher priority to rotate up
                if (node.left.priority > node.right.priority) {
                    node = node.rotateRight();
                    node.right = deleteHelp(node.right, key); // Continue adjustment on the right subtree
                } else {
                    node = node.rotateLeft();
                    node.left = deleteHelp(node.left, key); // Continue adjustment on the left subtree
                }
            }
        }
        return node;
    }
    /**
     * Searches for an element in the Treap.
     * @param key the element to find
     * @return true if the element is found, false otherwise
     */
    private boolean find(Node<E> node, E key) {
        while (node != null) {
            int cmp = key.compareTo(node.data);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return true; // Key found
            }
        }
        return false; // Key not found
    }
    /**
     * Helper method to recursively search for an element starting from a given node.
     * @param node the current node in the search process
     * @param key the key of the element to search for
     * @return true if the element is found, false otherwise
     */   
    public boolean find(E key) {
        return find(root, key);
    }
    /**
     * Generates a string representation of the Treap. This representation includes each node's key and priority,
     * formatted as a tree structure.
     * @return a string representation of the Treap
     */
    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    toString(root, sb, "", "");
    return sb.toString();
}
    /**
     * Helper method to recursively build a string representation of the Treap.
     * @param node the current node being processed
     * @param sb the StringBuilder object used to build the string representation
     * @param padding padding characters used to indicate the level of the current node
     * @param pointer line characters used to indicate the parent-child relationships
     */
private void toString(Node<E> node, StringBuilder sb, String padding, String pointer) {
    if (node != null) {
        sb.append(padding);
        sb.append(pointer);
        sb.append("[key=" + node.data + ", priority=" + node.priority + "]");
        sb.append('\n');

        String paddingForBoth = padding + "│  ";
        String pointerForRight = "└──";
        String pointerForLeft = (node.right != null) ? "├──" : "└──";

        toString(node.right, sb, paddingForBoth, pointerForRight);
        toString(node.left, sb, paddingForBoth, pointerForLeft);
    }
}

    


}

