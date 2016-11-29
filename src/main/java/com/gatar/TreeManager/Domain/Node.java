package com.gatar.TreeManager.Domain;

import java.util.LinkedList;

public interface Node {
    /**
     * Get id of node
     * @return int value of id
     */
    Integer getId();

    /**
     * Set id value of node.
     */
    void setId(Integer value);

    /**
     * Get integer value saved in node.
     * @return int value
     */
    Integer getValue();

    /**
     * Set integer value saved in node.
     */
    void setValue(Integer value);

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
     * @return selected child node, null if child not exist
     */
    Node getChild(int childNumber);

    /**
     * Get list with references for all children
     * @return list of all children
     */
    LinkedList<Node> getChildren();

    /**
     * Create {@link NodeDTO} object used for transfer data
     * @return object with DTO data
     */
    NodeDTO toNodeDTO();

    /**
     * Create {@link NodeEntity} object used for save data in in-memory H2 database
     * @return object with DTO data
     */
    NodeEntity toNodeH2();

    /**
     * Check is node a leaf.
     * @return true - node is leaf, false - node is not leaf
     */
    boolean isLeaf();

    /**
     * Check is node a root.
     * @return true - node is root, false - node isn't a root
     */
    boolean isRoot();
}
