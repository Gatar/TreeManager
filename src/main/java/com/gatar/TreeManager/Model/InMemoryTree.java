package com.gatar.TreeManager.Model;

import com.gatar.TreeManager.Domain.Node;
import com.gatar.TreeManager.Domain.NodeImpl;
import org.springframework.stereotype.Repository;
import java.util.HashSet;

/**
 * Class providing in-memory tree as singleton.
 */
@Repository
public class InMemoryTree {

    /**
     * Last created node id. Used for provide next, unique id value.
     */
    private Integer nodeIdCounter;

    /**
     * Set of all existing in tree nodes (at the moment).
     * Used for provide checking with O(1) instead of O(n) in DFS searching case, because this checking is used with high frequency.
     */
    private HashSet<Integer> existingNodeIds;

    /**
     * Reference to root of the tree.
     */
    private Node root;

    public InMemoryTree() {
        initializeEmptyTree();
    }

    /**
     * Get root Node reference.
     * @return root node reference.
     */
    public Node getRoot(){
        return root;
    }

    /**
     * Generate next id number. Use only during creation of new nodes.
     * @return next nodeId value.
     */
    public Integer generateNextNodeId(){
        nodeIdCounter++;
        existingNodeIds.add(nodeIdCounter.intValue());
        return nodeIdCounter;
    }

    /**
     * Check does node exist in tree, by check given node id in Set of existing nodes.
     * @param nodeId node id for check
     * @return true - node exist in actual tree, false - node does not exist in actual tree
     */
    public boolean doesNodeExist(Integer nodeId){
        return existingNodeIds.contains(nodeId);
    }

    /**
     * Remove node id from Set. When node is deleted this method must be used.
     * @param nodeId node id for remove
     */
    public void removeNodeId(Integer nodeId){
        existingNodeIds.remove(nodeId);
    }

    /**
     * Clear all tree and leave only root with value 1. Depends on parameter could load example tree for tests.
     * @param loadExampleTree true - load example tree, false - only clean tree
     */
    public void clearTree(boolean loadExampleTree){
        initializeEmptyTree();
        if(loadExampleTree) loadExampleTree();
    }

    private void initializeEmptyTree(){
        root = new NodeImpl(1);
        nodeIdCounter = 1;
        existingNodeIds = new HashSet<>();
        existingNodeIds.add(1);
    }

    private void loadExampleTree(){
        Node node2 = new NodeImpl(generateNextNodeId(), 3);
        Node node3 = new NodeImpl(generateNextNodeId(), 4);
        Node node4 = new NodeImpl(generateNextNodeId(), 4);
        Node node5 = new NodeImpl(generateNextNodeId(), -5);
        Node node6 = new NodeImpl(generateNextNodeId(), 0);
        Node node7 = new NodeImpl(generateNextNodeId(), 0);
        Node node8 = new NodeImpl(generateNextNodeId(), -15);
        Node node9 = new NodeImpl(generateNextNodeId(), 4);
        Node node10 = new NodeImpl(generateNextNodeId(), 2);
        Node node11 = new NodeImpl(generateNextNodeId(), 2);
        Node node12 = new NodeImpl(generateNextNodeId(), -11);

        //Left
        root.addChild(node2);
        node2.addChild(node4);
        node2.addChild(node8);
        node2.addChild(node9);
        node8.addChild(node12);

        //Right
        root.addChild(node3);
        node3.addChild(node5);
        node5.addChild(node6);
        node6.addChild(node7);
        node6.addChild(node10);
        node10.addChild(node11);
        for (int i = 0; i < 13; i++) existingNodeIds.add(i);
        nodeIdCounter = 12;
    }
}
