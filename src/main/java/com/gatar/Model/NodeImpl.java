package com.gatar.Model;

import java.util.LinkedList;

/**
 * Class containing single node values and every operations on it.
 * By standard constructor created node has fields with root values.
 */
public class NodeImpl implements Node {

    private int id;
    private int value;
    private Node parent;
    private LinkedList<Node> children;

    /**
     * Set all fields as root node.
     */
    public NodeImpl() {
        id = 1;
        value = 1;
        parent = null;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public LinkedList<Node> getChildren() {
        return children;
    }

    @Override
    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public void addLeaf() {

    }

    @Override
    public void removeChild(int nodeId) {

    }

    @Override
    public String toJSON() {
        return null;
    }
}
