package com.gatar.Service;

import com.gatar.Model.Node;
import com.gatar.Model.NodeImpl;
import com.gatar.Model.RootSingleton;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class TreeDFSTest {

    TreeDFS treeDFS = new TreeDFS();

    //Create Nodes
    Node root  = RootSingleton.getRoot();
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

    @Before
    public void setUp() throws Exception {

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

    @After
    public void tearDown() throws Exception {
        RootSingleton.clearTree();
    }

    @Test
    public void search() throws Exception {

        //Search for internal nodes
        assertEquals(node2,treeDFS.search(2));
        assertEquals(node10,treeDFS.search(10));

        //Search for leafs
        assertEquals(node4,treeDFS.search(4));
        assertEquals(node7,treeDFS.search(7));
        assertEquals(node11,treeDFS.search(11));

        //Search for root
        assertEquals(root,treeDFS.search(1));

        //Search for non-existing nodes
        assertEquals(null,treeDFS.search(13));
        assertEquals(null,treeDFS.search(-15));

    }

}