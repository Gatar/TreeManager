package com.gatar.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gatar.DataTransferObject.NodeDTO;
import com.gatar.Model.Node;
import com.gatar.Model.NodeImpl;
import com.gatar.Model.RootSingleton;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;

public class TreeServiceImplTest {

    @InjectMocks
    TreeServiceImpl treeService;

    @Spy
    TreeDFS treeDFS;

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
    public void setupMock(){
        treeService = new TreeServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

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
    public void getTree() throws Exception {
        //Prepare NodeDTO tree
        NodeDTO rootDTO = root.toNodeDTO();
        //Left
        NodeDTO node2DTO = node2.toNodeDTO();
        rootDTO.getChildren().add(node2DTO);
        NodeDTO node3DTO = node3.toNodeDTO();
        rootDTO.getChildren().add(node3DTO);
        node2DTO.getChildren().add(node4.toNodeDTO());
        node2DTO.getChildren().add(node9.toNodeDTO());
        NodeDTO node8DTO = node8.toNodeDTO();
        node2DTO.getChildren().add(node8DTO);
        node8DTO.getChildren().add(node12.toNodeDTO());
        //Right
        NodeDTO node5DTO = node5.toNodeDTO();
        node3DTO.getChildren().add(node5DTO);
        NodeDTO node6DTO = node6.toNodeDTO();
        node5DTO.getChildren().add(node6DTO);
        node6DTO.getChildren().add(node7.toNodeDTO());
        NodeDTO node10DTO = node10.toNodeDTO();
        node6DTO.getChildren().add(node10DTO);
        node10DTO.getChildren().add(node11.toNodeDTO());


        //Check if created tree DTO valid
        NodeDTO treeDTO = treeService.getTree();
        Assert.assertEquals(0,treeDTO.compareTo(rootDTO));
    }

    @Test
    public void switchBranch() throws Exception {

        //Switch leaf to leaf
        treeService.switchBranch(11, 12);
        //Check tree integrity
        Assert.assertEquals(node11, node12.getChild(0));
        Assert.assertNull(node10.getChild(0));
        Assert.assertEquals(node12, node11.getParent());
        //Check leaf values correctness
        Assert.assertEquals(-22, node11.getValue());
        Assert.assertEquals(0, node10.getValue());

        //Switch node with leafs to leaf
        treeService.switchBranch(6, 9);
        //Check tree integrity
        Assert.assertEquals(node6, node9.getChild(0));
        Assert.assertEquals(node7, node6.getChild(0));
        Assert.assertTrue(node5.getChildren().isEmpty());
        Assert.assertTrue(node7.getChildren().isEmpty());
        Assert.assertEquals(node10, node6.getChild(1));
        Assert.assertEquals(node9, node6.getParent());
        //Check leaf values correctness
        Assert.assertEquals(0, node6.getValue());
        Assert.assertEquals(8, node10.getValue());

        //Switch leaf to node with leaf
        treeService.switchBranch(7, 1);
        //Check tree integrity
        Assert.assertEquals(node7, root.getChild(2));
        Assert.assertNull(node7.getChild(0));
        Assert.assertNull(root.getParent());
        Assert.assertEquals(root, node7.getParent());
        //Check leaf values correctness
        Assert.assertEquals(1, node7.getValue());
        Assert.assertEquals(4, node4.getValue());


        //Switch node with leafs to node with leaf
        treeService.switchBranch(2, 3);
        //Check tree integrity
        Assert.assertEquals(node2, node3.getChild(1));
        Assert.assertEquals(node8, node2.getChild(1));
        Assert.assertEquals(node3, node2.getParent());
        //Check values correctness
        Assert.assertEquals(12, node10.getValue());
        Assert.assertEquals(1, node7.getValue());
        Assert.assertEquals(0, node6.getValue());
        Assert.assertEquals(-15, node8.getValue());
        Assert.assertEquals(-11, node12.getValue());
        Assert.assertEquals(8, node4.getValue());

        //Try create cyclic path
        treeService.switchBranch(3, 10);
        //Check tree integrity - shouldn't changed
        Assert.assertTrue(node10.isLeaf());
        Assert.assertEquals(root, node3.getParent());
    }

    @Test
    public void addNewLeaf() throws Exception {

        //Add new leaf to leaf
        treeService.addNewLeaf(11);
        Assert.assertFalse(node11.isLeaf());
        Assert.assertEquals(13, node11.getChild(0).getId());
        Assert.assertEquals(4, node11.getChild(0).getValue());
        Assert.assertEquals(node11, node11.getChild(0).getParent());

        //Add new leaf to node with leafs
        treeService.addNewLeaf(2);
        Assert.assertEquals(14, node2.getChild(3).getId());
        Assert.assertEquals(4, node2.getChild(0).getValue());
        Assert.assertEquals(node2, node2.getChild(3).getParent());

        //Add new leaf to root
        treeService.addNewLeaf(1);
        Assert.assertEquals(15, root.getChild(2).getId());
        Assert.assertEquals(1, root.getChild(2).getValue());
        Assert.assertEquals(root, root.getChild(2).getParent());
        Assert.assertNull( root.getParent());
    }
    @Test
    public void removeNodeWithoutChildren() throws Exception {

        //Remove leaf from leaf
        treeService.removeNodeWithoutChildren(11);
        Assert.assertTrue(node10.isLeaf());
        Assert.assertNull(treeDFS.search(11));
        Assert.assertEquals(0, node10.getValue());

        //Remove node with single leaf between two nodes
        treeService.removeNodeWithoutChildren(5);
        Assert.assertNull(treeDFS.search(5));
        Assert.assertEquals(5, node7.getValue());
        Assert.assertEquals(5, node10.getValue());
        Assert.assertEquals(node3, node6.getParent());
        Assert.assertEquals(node6, node3.getChild(0));

        //Remove node with multiple leafs between two leafs
        treeService.removeNodeWithoutChildren(2);
        Assert.assertNull(treeDFS.search(2));
        Assert.assertEquals(1, node4.getValue());
        Assert.assertEquals(-15, node8.getValue());
        Assert.assertEquals(-14, node12.getValue());
        Assert.assertEquals(root, node8.getParent());
        Assert.assertEquals(root, node4.getParent());
        Assert.assertEquals(node3, root.getChild(0));
        Assert.assertEquals(4, root.getChildren().size());

        //Try remove root
        treeService.removeNodeWithoutChildren(1);
        Assert.assertEquals(root, treeDFS.search(1));
        Assert.assertEquals(node3, treeDFS.search(3));
    }

    @Test
    public void removeNodeWithChildren() throws Exception {

        //Remove leaf
        treeService.removeNodeWithoutChildren(11);
        Assert.assertTrue(node10.isLeaf());
        Assert.assertNull(treeDFS.search(11));
        Assert.assertEquals(0, node10.getValue());

        //Remove node with leafs
        treeService.removeNodeWithChildren(2);
        Assert.assertNull(treeDFS.search(2));
        Assert.assertNull(treeDFS.search(8));
        Assert.assertNull(treeDFS.search(12));
        Assert.assertEquals(node3, root.getChild(0));
    }

    @Test
    public void changeNodeValue() throws Exception {

        //Change leaf value
        treeService.changeNodeValue(11, 45);
        Assert.assertEquals(45, node11.getValue());

        //Change node value
        treeService.changeNodeValue(2, 100);
        Assert.assertEquals(-15, node8.getValue());
        Assert.assertEquals(86, node12.getValue());
        Assert.assertEquals(101, node4.getValue());
        Assert.assertEquals(100, node2.getValue());
    }

}