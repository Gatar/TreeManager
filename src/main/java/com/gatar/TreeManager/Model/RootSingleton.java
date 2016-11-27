package com.gatar.Model;


public class RootSingleton {

    private static RootSingleton rootSingleton = new RootSingleton();
    private static Integer createdNodeCounter;
    private Node root = new NodeImpl(true);

    private RootSingleton() {
        createdNodeCounter = 1;
            }

    public static Node getRoot(){
        return rootSingleton.root;
    }

    public static Integer getNodeCounter(){
        return createdNodeCounter;
    }

    public static void nodeCounterIncrement(){
        createdNodeCounter++;
    }

    public static void clearTree(){
        rootSingleton = new RootSingleton();
    }

    private void loadPrebuildTree(){
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
    }
}
