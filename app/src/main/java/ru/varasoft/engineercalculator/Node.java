package ru.varasoft.engineercalculator;

public class Node<T> {

    private final T value;

    private Node<T> leftChild;
    private Node<T> rightChild;

    public int getDepth() {
        return depth;
    }

    private int depth;

    public Node(T value) {
        this.value = value;
        this.depth = 0;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
        leftChild.depth = depth + 1;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
        rightChild.depth = depth + 1;
    }

    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    public boolean hasOnlyOneChild() {
        return leftChild == null ^ rightChild == null;
    }
}
