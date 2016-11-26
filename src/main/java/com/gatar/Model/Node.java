package com.gatar.Model;

import com.gatar.DataTransferObject.NodeDTO;

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
     * Search in children list for child with specified id and remove it.
     * In case when child with this id not exist, do nothing.
     * @param nodeId node id of child for remove
     */
    void removeChild(int nodeId);

    /**
     * Get single child by it's number in children list.
     * @param childNumber number of children do get in range including 0.
     * @return selected child node or null if child not exist
     */
    Node getChild(int childNumber);

    /**
     * Get list with references for all children
     * @return list of all children
     */
    LinkedList<Node> getChildren();

    /**
     * Parse child to needed JSON String form.
     * @return JSON value of node as String.
     */
    NodeDTO toNodeDTO();

    /**
     * Check is node a leaf.
     * @return true - node is leaf, false - node is not leaf
     */
    boolean isLeaf();
}
