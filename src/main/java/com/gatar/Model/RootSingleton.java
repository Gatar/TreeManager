package com.gatar.Model;


public class RootSingleton {

    private static RootSingleton rootSingleton = new RootSingleton();
    private Node root = new NodeImpl();

    private RootSingleton() {
    }

    public static Node getRoot(){
        return rootSingleton.root;
    }

}
