package com.gatar.Service;

import com.gatar.Model.Node;
import com.gatar.Model.NodeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TreeServiceImpl implements TreeService{

    @Autowired
    TreeDFS treeDFS;

    @Override
    public String getTree() {
        return null;
    }

    @Override
    public void switchBranch(int nodeId, int newBranchNodeId) {
        Node transferNode = treeDFS.search(nodeId);
        Node targetNode = treeDFS.search(newBranchNodeId);
        Node transferNodeParent = transferNode.getParent();

        if(isCreateAcyclic(transferNode,targetNode)){
            targetNode.addChild(transferNode);
            transferNodeParent.removeChild(nodeId);

            recalculateLeafsValues(targetNode);
            recalculateLeafsValues(transferNodeParent);
        } else System.out.println("Error: Try to create cyclic structure, not tree!");
    }

    @Override
    public void addNewLeaf(int nodeId) {
        Node parent = treeDFS.search(nodeId);
        Node leaf = new NodeImpl(false);
        leaf.setParent(parent);
        leaf.setValue(countValuesToRoot(leaf));
        parent.addChild(leaf);
    }

    @Override
    public void removeNodeWithoutChildren(int nodeId) {
        if  (!isRoot(nodeId)) {
            Node node = treeDFS.search(nodeId);
            Node parentNode = node.getParent();

            node.getChildren().forEach(n -> parentNode.addChild(n));
            parentNode.removeChild(nodeId);

            recalculateLeafsValues(parentNode);
        }
    }

    @Override
    public void removeNodeWithChildren(int nodeId) {
        if  (!isRoot(nodeId)) {
            Node node = treeDFS.search(nodeId);
            Node parentNode = node.getParent();
            parentNode.removeChild(nodeId);

            recalculateLeafsValues(parentNode);
        }
    }

    @Override
    public void changeNodeValue(int nodeId, int value) {
        Node node = treeDFS.search(nodeId);
        node.setValue(value);

        if(!node.isLeaf()) recalculateLeafsValues(node);
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

}
