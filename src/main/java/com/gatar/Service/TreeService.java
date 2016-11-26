package com.gatar.Service;

public interface TreeService {
    /**
     * Get all tree as String containing data in JSON form, which are readable for client to read and show.
     * @return String containing JSON with tree data
     */
    String getTree();

    /**
     * Proceed switch one node with all connected to it branch to other node.
     * @param nodeId id of node which will be move
     * @param newBranchNodeId id of target node, which will be new parent for moved node
     */
    void switchBranch(int nodeId, int newBranchNodeId);

    /**
     * Adding a new leaf to existing node. Default node value will be calculated as values of all above nodes up to root.
     * @param nodeId id of node which will be new parent
     */
    void addNewLeaf(int nodeId);

    /**
     * Remove only selected node. Children of removing node will be pinned to parent.
     * If selected node is root, method doesn't remove it!
     * @param nodeId id of node which will be removed
     */
    void removeNodeWithoutChildren(int nodeId);

    /**
     * Remove node with all its children from the tree.
     * If selected node is root, method doesn't remove it!
     * @param nodeId id of node which will be removed.
     */
    void removeNodeWithChildren(int nodeId);

    /**
     * Set new value for node by its id.
     * @param nodeId id of node for set new value
     * @param value value to set
     */
    void changeNodeValue(int nodeId, int value);
}
