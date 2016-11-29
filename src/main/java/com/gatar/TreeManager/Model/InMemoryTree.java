package com.gatar.TreeManager.Model;

import com.gatar.TreeManager.Domain.Node;
import com.gatar.TreeManager.Domain.NodeEntity;
import com.gatar.TreeManager.Domain.NodeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Class providing in-memory tree as singleton.
 * Tree structure is root Node object with children references,
 * who have references to their children and so on.
 * All operations on tree are making by references to this root,
 * but tree can be saved in internal H2 database as Adjacency List.
 */
@Repository
public class InMemoryTree {

    @Autowired
    H2DatabaseRepository h2DatabaseRepository;

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

    /**
     * Load nodes from internal database and recreate references between them.
     */
    public void loadTreeFromH2Database(){
        List<NodeEntity> nodeEntityList = h2DatabaseRepository.findAll();
        if(nodeEntityList.isEmpty()) return;
        HashMap<Integer,Node> nodeMap = new HashMap<>();
        int highestNodeId = 0;

        nodeEntityList.forEach(n -> nodeMap.put(n.getId(),n.toNode()));

        for(NodeEntity nodeEntity : nodeEntityList){
            //Search for last node Id
            int nodeH2Id = nodeEntity.getId();
            if(highestNodeId < nodeH2Id) highestNodeId = nodeH2Id;

            //Create nodes id Set
            existingNodeIds.add(nodeH2Id);

            //Create tree references
            Node node = nodeMap.get(nodeH2Id);
            if(!node.isRoot()) {
                Node nodeParent = nodeMap.get(nodeEntity.getParentId());
                node.setParent(nodeParent);
                nodeParent.addChild(node);
            }
        }

        root = nodeMap.get(1);
        nodeIdCounter = highestNodeId;
    }

    /**
     * Save tree in internal database with use {@link NodeEntity} as entity
     * @param node
     */
    public void saveTreeInH2Database(Node node){
       if(node.isRoot()) h2DatabaseRepository.deleteAll();

        h2DatabaseRepository.save(node.toNodeH2());

        for(Node nodeChild : node.getChildren()){
            saveTreeInH2Database(nodeChild);
        }
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
