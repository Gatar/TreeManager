package com.gatar.TreeManager.Model;

import com.gatar.TreeManager.DataTransferObject.NodeDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing single node values and every operations on it.
 * By standard constructor created node has fields with root values.
 */
public class NodeImpl implements Node {

    private int id;
    private int value;
    private Node parent;
    private LinkedList<Node> children;


    /**
     * Constructor for only single use in RootSingleton for create root node
     */
    public NodeImpl(Boolean createRoot) {
        if(createRoot) id = 1;
            else id = getNewNodeId();

        value = 1;
        parent = null;
        children = new LinkedList<>();
    }

    /**
     * Constructor used only for create test tree.
     * @param value for new node
     */
    public NodeImpl(int value) {
        id = getNewNodeId();
        this.value = value;
        parent = null;
        children = new LinkedList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public Node getChild(int childListNumber) {
        if(children.size() > childListNumber){
            return  children.get(childListNumber);
        }
        return null;
    }

    @Override
    public LinkedList<Node> getChildren() {
        return children;
    }

    @Override
    public void addChild(Node child) {
        child.setParent(this);
        children.add(child);
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public void removeChild(int nodeId) {
        List<Node> child =
                children.stream()
                        .filter(n -> n.getId() == nodeId)
                        .collect(Collectors.toList());

        if(!child.isEmpty()) children.remove(child.get(0));
    }

    @Override
    public NodeDTO toNodeDTO() {
        NodeDTO nodeDTO = new NodeDTO(getId(),getValue());
        return nodeDTO;
    }


    private int getNewNodeId(){
        RootSingleton.nodeCounterIncrement();
        return RootSingleton.getNodeCounter();
    }
}
