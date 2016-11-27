package com.gatar.TreeManager.Model;

import java.util.HashSet;

/**
 * Provide in memory single references to:
 *  <ul>
 *  <li> Node root reference
 *  <li> Integer counter of created nodes (all created nodes, counted with deleted too) for add always unique id
 *  <li> HashSet<Integer> set of all existing nodeId's (for efficient O(1) search does node exist)
 *  </ul>
 */
public class RootSingleton {

    private static RootSingleton rootSingleton = new RootSingleton();
    private static Integer createdNodeCounter;
    private static HashSet<Integer> nodeIdSet;
    private Node root = new NodeImpl(true);

    private RootSingleton() {
        createdNodeCounter = 1;
        nodeIdSet = new HashSet<>();
        nodeIdSet.add(1);
    }

    public static Node getRoot(){
        return rootSingleton.root;
    }

    public static Integer getNodeCounter(){
        return createdNodeCounter;
    }

    public static void nodeCounterIncrement(){
        createdNodeCounter++;
        nodeIdSet.add(createdNodeCounter.intValue());
    }

    public static HashSet<Integer> getNodeIdSet(){
        return nodeIdSet;
    }

    public static void clearTree(boolean loadDefaultTree){
        rootSingleton = new RootSingleton();
        if(loadDefaultTree) rootSingleton.loadDefaultTree();
    }




    private void loadDefaultTree(){
        Node node2 = new NodeImpl(3);
        Node node3 = new NodeImpl(4);
        Node node4 = new NodeImpl(4);
        Node node5 = new NodeImpl(-5);
        Node node6 = new NodeImpl(0);
        Node node7 = new NodeImpl(0);
        Node node8 = new NodeImpl(-15);
        Node node9 = new NodeImpl(4);
        Node node10 = new NodeImpl(2);
        Node node11 = new NodeImpl(2);
        Node node12 = new NodeImpl(-11);

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
        for (int i = 0; i < 13; i++) nodeIdSet.add(i);
        createdNodeCounter = 12;
    }
}
