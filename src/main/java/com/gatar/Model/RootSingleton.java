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
}
