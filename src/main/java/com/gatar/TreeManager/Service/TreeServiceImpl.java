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

    public TreeDFS treeDFS = new TreeDFS();


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
            Node transferNode = treeDFS.search(nodeId);
            Node targetNode = treeDFS.search(newBranchNodeId);
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
            Node parent = treeDFS.search(nodeId);
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
            Node node = treeDFS.search(nodeId);
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
            Node node = treeDFS.search(nodeId);
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
            Node node = treeDFS.search(nodeId);
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

    private void recalculateLeafsValues(Node node){
        if(node == null) return;
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

    private class TreeDFS {
        private Node actualNode;
        private Stack<Integer> stack;


        /**
         * Start searching with return node reference.
         * @param targetId id of target node.
         * @return node reference if node exist, if node not exist return null
         */
        public Node search(int targetId) {
            clearParameters();
            if (targetId < 1) return null;

            while (true) {
                if (stack.empty()) return null;
                if (actualNode.getId() == targetId) return actualNode;

                if (actualNode.getChildren().size() > stack.peek()) {
                    actualNode = actualNode.getChild(stack.peek());
                    incrementStackValue();
                    stack.push(0);
                } else {
                    stack.pop();
                    actualNode = actualNode.getParent();
                }
            }
        }

        private void clearParameters(){
            stack = new Stack<>();
            stack.push(0);
            actualNode = inMemoryTree.getRoot();
        }

        private void incrementStackValue(){
            Integer last = stack.pop();
            last++;
            stack.push(last);
        }

    }
}
