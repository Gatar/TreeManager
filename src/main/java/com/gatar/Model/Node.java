package com.gatar.Model;

import java.util.LinkedList;

public interface Node {
    /**
     * Get id of node
     * @return int value of id
     */
    int getId();

    /**
     * Set id value of node.
     */
    void setId(int value);

    /**
     * Get integer value saved in node.
     * @return int value
     */
    int getValue();

    /**
     * Set integer value saved in node.
     */
    void setValue(int value);

    /**
     * Get parent node.
     * @return parent node reference, if node is root return null
     */
    Node getParent();

    /**
     * Set parent of node.
     * @param parent reference to the new parent
     */
    void setParent(Node parent);

    /**
     * Add new child to children list.
     * @param child reference to the new child
     */
    void addChild(Node child);

    /**
     * Create new Node, add as a child and calculating its value.
     */
    void addLeaf();

    /**
     * Search in children list for child with specified id and remove it.
     * @param nodeId node id of child for remove
     */
    void removeChild(int nodeId);

    /**
     * Get list with references for all children
     * @return list of all children
     */
    LinkedList<Node> getChildren();

    /**
     * Parse child to needed JSON String form.
     * @return JSON value of node as String.
     */
    String toJSON();
}
