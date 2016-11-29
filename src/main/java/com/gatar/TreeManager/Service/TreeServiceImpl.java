package com.gatar.TreeManager.Service;

import com.gatar.TreeManager.Domain.NodeDTO;
import com.gatar.TreeManager.Model.InMemoryTree;
import com.gatar.TreeManager.Domain.Node;
import com.gatar.TreeManager.Domain.NodeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Stack;


@Service
public class TreeServiceImpl implements TreeService{

    @Autowired
    InMemoryTree inMemoryTree;

    @Override
    public NodeDTO getTree() {
        Node root = inMemoryTree.getRoot();
        NodeDTO result = root.toNodeDTO();
        createTreeForTransfer(root,result);
        return result;
    }

    @Override
    public boolean switchBranch(int nodeId, int newBranchNodeId) {
        if(doesNodeExist(nodeId) && doesNodeExist(newBranchNodeId)) {
            Node transferNode = searchNodeByDFS(nodeId);
            Node targetNode = searchNodeByDFS(newBranchNodeId);
            Node transferNodeParent = transferNode.getParent();

            if (isCreateAcyclic(transferNode, targetNode)) {
                targetNode.addChild(transferNode);
                transferNodeParent.removeChild(nodeId);

                recalculateLeafsValues(targetNode);
                recalculateLeafsValues(transferNodeParent);
            } else System.out.println("Error: Try to create cyclic structure, not tree!");
            return true;
        }
        return false;
    }

    @Override
    public boolean addNewLeaf(int nodeId) {
        if(doesNodeExist(nodeId)) {
            Node parent = searchNodeByDFS(nodeId);
            Integer nextNodeId = inMemoryTree.generateNextNodeId();
            Node leaf = new NodeImpl(nextNodeId);
            leaf.setParent(parent);
            leaf.setValue(countValuesToRoot(leaf));
            parent.addChild(leaf);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeNodeWithoutChildren(int nodeId) {
        if  (!isRoot(nodeId) && doesNodeExist(nodeId)) {
            Node node = searchNodeByDFS(nodeId);
            Node parentNode = node.getParent();

            node.getChildren().forEach(n -> parentNode.addChild(n));
            parentNode.removeChild(nodeId);

            removeNodesFromSet(node,false);
            recalculateLeafsValues(parentNode);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeNodeWithChildren(int nodeId) {
        if  (!isRoot(nodeId) && doesNodeExist(nodeId)) {
            Node node = searchNodeByDFS(nodeId);
            Node parentNode = node.getParent();
            parentNode.removeChild(nodeId);

            removeNodesFromSet(node,true);
            recalculateLeafsValues(parentNode);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeNodeValue(int nodeId, int value) {
        if(doesNodeExist(nodeId)){
            Node node = searchNodeByDFS(nodeId);
            node.setValue(value);

            if (!node.isLeaf()) recalculateLeafsValues(node);
            return true;
        }
        return false;
    }

    @Override
    public void prepareTreeForIntegrationTest() {
        inMemoryTree.clearTree(true);
    }

    @Override
    public void saveTreeInInternalDatabase() {
        inMemoryTree.saveTreeInH2Database(inMemoryTree.getRoot());
    }

    @Override
    public void loadTreeFromInternalDatabase() {
        inMemoryTree.loadTreeFromH2Database();
    }

    private void recalculateLeafsValues(Node node){
        if(node == null) return;
        if(node.isRoot() && node.getChildren().isEmpty()) return;
        if(node.isLeaf()) node.setValue(countValuesToRoot(node));

        node.getChildren().stream()
                .filter(n -> n.isLeaf())
                .forEach(n -> n.setValue(countValuesToRoot(n)));

        for(int counter = 0; counter < node.getChildren().size(); counter++){
            recalculateLeafsValues(node.getChild(counter));
        }
    }

    private int countValuesToRoot(Node node){
        int value;
        Node parent = node.getParent();

        if(parent != null){
            value = parent.getValue();
            return value + countValuesToRoot(parent);
        }
        return 0;
    }

    private boolean isRoot(int nodeId){
        return nodeId == 1;
    }

    private boolean isCreateAcyclic(Node transferNode, Node targetNode){
        if(transferNode.getId()==targetNode.getId()) return false;
        if(targetNode.getParent() != null) return isCreateAcyclic(transferNode,targetNode.getParent());
        return true;
    }

    private void createTreeForTransfer(Node node, NodeDTO result){
        for(int counter = 0; counter < node.getChildren().size(); counter++){
            Node tempNode = node.getChild(counter);
            result.getChildren().add(tempNode.toNodeDTO());
            createTreeForTransfer(tempNode,result.getChildren().get(counter));
        }

    }

    private boolean doesNodeExist(Integer nodeId){
        return inMemoryTree.doesNodeExist(nodeId);
    }

    private void removeNodesFromSet(Node node, boolean removeChildren){
        inMemoryTree.removeNodeId(node.getId());

        if(removeChildren){
            node.getChildren().forEach(n -> removeNodesFromSet(n,true));
        }
    }
    

    public Node searchNodeByDFS(int targetId) {
        Node actualNode = inMemoryTree.getRoot();
        Stack<Integer> stack = new Stack<>();
        stack.push(0);        

        if (targetId < 1) return null;

        while (true) {
            if (stack.empty()) return null;
            if (actualNode.getId() == targetId) return actualNode;

            if (actualNode.getChildren().size() > stack.peek()) {
                actualNode = actualNode.getChild(stack.peek());
                incrementStackValue(stack);
                stack.push(0);
            } else {
                stack.pop();
                actualNode = actualNode.getParent();
            }
        }

    }

    private void incrementStackValue(Stack<Integer> stack){
        Integer last = stack.pop();
        last++;
        stack.push(last);
    }
}
