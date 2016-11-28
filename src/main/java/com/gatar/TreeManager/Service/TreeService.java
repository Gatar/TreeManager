package com.gatar.TreeManager.Service;

import com.gatar.TreeManager.Domain.NodeDTO;

public interface TreeService {
    /**
     * Get all tree as String containing data in JSON form, which are readable for client to read and show.
     * @return String containing JSON with tree data
     */
    NodeDTO getTree();

    /**
     * Proceed switch one node with all connected to it branch to other node.
     * @param nodeId id of node which will be move
     * @param newBranchNodeId id of target node, which will be new parent for moved node
     * @return true - process done, false - one of nodes not exist
     */
    boolean switchBranch(int nodeId, int newBranchNodeId);

    /**
     * Adding a new leaf to existing node. Default node value will be calculated as values of all above nodes up to root.
     * @param nodeId id of node which will be new parent
     * @return true - process done, false - node not exist
     */
    boolean addNewLeaf(int nodeId);

    /**
     * Remove only selected node. Children of removing node will be pinned to parent.
     * If selected node is root, method doesn't remove it!
     * @param nodeId id of node which will be removed
     * @return true - process done, false - node not exist or is root
     */
    boolean removeNodeWithoutChildren(int nodeId);

    /**
     * Remove node with all its children from the tree.
     * If selected node is root, method doesn't remove it!
     * @param nodeId id of node which will be removed.
     * @return true - process done, false - node not exist or is root
     */
    boolean removeNodeWithChildren(int nodeId);

    /**
     * Set new value for node by its id.
     * @param nodeId id of node for set new value
     * @param value value to set
     * @return true - process done, false - node not exist
     */
    boolean changeNodeValue(int nodeId, int value);

    /**
     * Clear in-memory tree and load example tree data. Use for integrations tests with client api.
     */
    void prepareTreeForIntegrationTest();
}
