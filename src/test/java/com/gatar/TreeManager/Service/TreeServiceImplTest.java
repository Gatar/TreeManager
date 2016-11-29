package com.gatar.TreeManager.Service;

import com.gatar.TreeManager.Domain.NodeDTO;
import com.gatar.TreeManager.Model.InMemoryTree;
import com.gatar.TreeManager.Domain.Node;
import com.gatar.TreeManager.Domain.NodeImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;

/**
 * Test TreeService methods with connected to them in private methods logic.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration
public class TreeServiceImplTest {

    @InjectMocks
    TreeServiceImpl treeService;

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

        root = inMemoryTree.getRoot();
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

    @Test
    public void search() throws Exception {

        //Search for internal nodes
        assertEquals(node2,treeService.searchNodeByDFS(2));
        assertEquals(node10,treeService.searchNodeByDFS(10));

        //Search for leafs
        assertEquals(node4,treeService.searchNodeByDFS(4));
        assertEquals(node7,treeService.searchNodeByDFS(7));
        assertEquals(node11,treeService.searchNodeByDFS(11));

        //Search for root
        assertEquals(root,treeService.searchNodeByDFS(1));

        //Search for non-existing nodes
        assertEquals(null,treeService.searchNodeByDFS(13));
        assertEquals(null,treeService.searchNodeByDFS(-15));

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
    public void addNewLeaf() throws Exception {

        //Add new leaf to leaf
        treeService.addNewLeaf(11);
        Assert.assertFalse(node11.isLeaf());
        Assert.assertEquals(13, (int)node11.getChild(0).getId());
        Assert.assertEquals(4, (int) node11.getChild(0).getValue());
        Assert.assertEquals(node11, node11.getChild(0).getParent());

        //Add new leaf to node with leafs
        treeService.addNewLeaf(2);
        Assert.assertEquals(14, (int) node2.getChild(3).getId());
        Assert.assertEquals(4, (int) node2.getChild(0).getValue());
        Assert.assertEquals(node2, node2.getChild(3).getParent());

        //Add new leaf to root
        treeService.addNewLeaf(1);
        Assert.assertEquals(15, (int) root.getChild(2).getId());
        Assert.assertEquals(1, (int) root.getChild(2).getValue());
        Assert.assertEquals(root, root.getChild(2).getParent());
        Assert.assertNull( root.getParent());

        //Try use wrong nodeId
        Assert.assertFalse(treeService.addNewLeaf(125));
    }

    @Test
    public void switchBranch() throws Exception {

        //Switch leaf to leaf
        treeService.switchBranch(11, 12);
        //-----Check tree integrity
        Assert.assertEquals(node11, node12.getChild(0));
        Assert.assertNull(node10.getChild(0));
        Assert.assertEquals(node12, node11.getParent());
        //-----Check leaf values correctness
        Assert.assertEquals(-22, (int) node11.getValue());
        Assert.assertEquals(0, (int) node10.getValue());

        //Switch node with leafs to leaf
        treeService.switchBranch(6, 9);
        //-----Check tree integrity
        Assert.assertEquals(node6, node9.getChild(0));
        Assert.assertEquals(node7, node6.getChild(0));
        Assert.assertTrue(node5.getChildren().isEmpty());
        Assert.assertTrue(node7.getChildren().isEmpty());
        Assert.assertEquals(node10, node6.getChild(1));
        Assert.assertEquals(node9, node6.getParent());
        //-----Check leaf values correctness
        Assert.assertEquals(0, (int) node6.getValue());
        Assert.assertEquals(8, (int) node10.getValue());

        //Switch leaf to node with leaf
        treeService.switchBranch(7, 1);
        //-----Check tree integrity
        Assert.assertEquals(node7, root.getChild(2));
        Assert.assertNull(node7.getChild(0));
        Assert.assertNull(root.getParent());
        Assert.assertEquals(root, node7.getParent());
        //-----Check leaf values correctness
        Assert.assertEquals(1, (int) node7.getValue());
        Assert.assertEquals(4, (int) node4.getValue());


        //Switch node with leafs to node with leaf
        treeService.switchBranch(2, 3);
        //-----Check tree integrity
        Assert.assertEquals(node2, node3.getChild(1));
        Assert.assertEquals(node8, node2.getChild(1));
        Assert.assertEquals(node3, node2.getParent());
        //-----Check values correctness
        Assert.assertEquals(12, (int) node10.getValue());
        Assert.assertEquals(1, (int) node7.getValue());
        Assert.assertEquals(0, (int) node6.getValue());
        Assert.assertEquals(-15, (int) node8.getValue());
        Assert.assertEquals(-11, (int) node12.getValue());
        Assert.assertEquals(8, (int) node4.getValue());

        //Try create cyclic path
        treeService.switchBranch(3, 10);
        //-----Check tree integrity - shouldn't changed
        Assert.assertTrue(node10.isLeaf());
        Assert.assertEquals(root, node3.getParent());

        //Try use wrong nodeIds
        Assert.assertFalse(treeService.switchBranch(125,10));
        Assert.assertFalse(treeService.switchBranch(10,120));
        Assert.assertFalse(treeService.switchBranch(125,145));
    }
    @Test
    public void removeNodeWithoutChildren() throws Exception {

        //Remove leaf from leaf
        treeService.removeNodeWithoutChildren(11);
        Assert.assertTrue(node10.isLeaf());
        Assert.assertNull(treeService.searchNodeByDFS(11));
        Assert.assertEquals(0, (int) node10.getValue());

        //Remove node with single leaf between two nodes
        treeService.removeNodeWithoutChildren(5);
        Assert.assertNull(treeService.searchNodeByDFS(5));
        Assert.assertEquals(5, (int) node7.getValue());
        Assert.assertEquals(5, (int) node10.getValue());
        Assert.assertEquals(node3, node6.getParent());
        Assert.assertEquals(node6, node3.getChild(0));

        //Remove node with multiple leafs between two leafs
        treeService.removeNodeWithoutChildren(2);
        Assert.assertNull(treeService.searchNodeByDFS(2));
        Assert.assertEquals(1, (int) node4.getValue());
        Assert.assertEquals(-15, (int) node8.getValue());
        Assert.assertEquals(-14, (int) node12.getValue());
        Assert.assertEquals(root, node8.getParent());
        Assert.assertEquals(root, node4.getParent());
        Assert.assertEquals(node3, root.getChild(0));
        Assert.assertEquals(4, root.getChildren().size());

        //Try remove root
        treeService.removeNodeWithoutChildren(1);
        Assert.assertEquals(root, treeService.searchNodeByDFS(1));
        Assert.assertEquals(node3, treeService.searchNodeByDFS(3));

        //Try use wrong nodeId
        Assert.assertFalse(treeService.removeNodeWithoutChildren(47));
    }

    @Test
    public void removeNodeWithChildren() throws Exception {

        //Remove leaf
        treeService.removeNodeWithoutChildren(11);
        Assert.assertTrue(node10.isLeaf());
        Assert.assertNull(treeService.searchNodeByDFS(11));
        Assert.assertEquals(0, (int) node10.getValue());

        //Remove node with leafs
        treeService.removeNodeWithChildren(2);
        Assert.assertNull(treeService.searchNodeByDFS(2));
        Assert.assertNull(treeService.searchNodeByDFS(8));
        Assert.assertNull(treeService.searchNodeByDFS(12));
        Assert.assertEquals(node3, root.getChild(0));

        //Try use wrong nodeId
        Assert.assertFalse(treeService.removeNodeWithChildren(18));
    }

    @Test
    public void changeNodeValue() throws Exception {

        //Change leaf value
        treeService.changeNodeValue(11, 45);
        Assert.assertEquals(45, (int) node11.getValue());

        //Change node value
        treeService.changeNodeValue(2, 100);
        Assert.assertEquals(-15, (int) node8.getValue());
        Assert.assertEquals(86, (int) node12.getValue());
        Assert.assertEquals(101, (int) node4.getValue());
        Assert.assertEquals(100, (int) node2.getValue());

        //Try use wrong nodeId
        Assert.assertFalse(treeService.changeNodeValue(125,10));
    }
}