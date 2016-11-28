package com.gatar.TreeManager.Service;

import com.gatar.TreeManager.Model.InMemoryTree;
import com.gatar.TreeManager.Domain.Node;
import com.gatar.TreeManager.Domain.NodeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


import static org.junit.Assert.*;

/**
 * Test tree Seep-first-search method.
 */
public class TreeDFSTest {

    @InjectMocks
    TreeDFS treeDFS;

    @Spy
    InMemoryTree inMemoryTree;

    //Create Nodes
    Node root;
    Node node2;
    Node node3;
    Node node4;
    Node node5;
    Node node6;
    Node node7;
    Node node8;
    Node node9;
    Node node10;
    Node node11;
    Node node12;

    @Before
    public void setupMock(){
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setUp() throws Exception {
        root  = inMemoryTree.getRoot();
        node2 = new NodeImpl(inMemoryTree.generateNextNodeId(), 3);
        node3 = new NodeImpl(inMemoryTree.generateNextNodeId(), 4);
        node4 = new NodeImpl(inMemoryTree.generateNextNodeId(), 4);
        node5 = new NodeImpl(inMemoryTree.generateNextNodeId(), -5);
        node6 = new NodeImpl(inMemoryTree.generateNextNodeId(), 0);
        node7 = new NodeImpl(inMemoryTree.generateNextNodeId(), 0);
        node8 = new NodeImpl(inMemoryTree.generateNextNodeId(), -15);
        node9 = new NodeImpl(inMemoryTree.generateNextNodeId(), 4);
        node10 = new NodeImpl(inMemoryTree.generateNextNodeId(), 2);
        node11 = new NodeImpl(inMemoryTree.generateNextNodeId(), 2);
        node12 = new NodeImpl(inMemoryTree.generateNextNodeId(), -11);

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
        inMemoryTree.clearTree(false);
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