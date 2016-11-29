package com.gatar.TreeManager.Domain;

import com.gatar.TreeManager.Model.InMemoryTree;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Single node class.
 */
public class NodeImpl implements Node {

    private Integer id;
    private Integer value;
    private Node parent;
    private LinkedList<Node> children;

    /**
     * Constructor for normal creating Nodes.
     * @param id for set id of new node, should be provided from {@link InMemoryTree}
     */
    public NodeImpl(int id) {
        this.id = id;
        value = 1;
        parent = null;
        children = new LinkedList<>();
    }

    /**
     * Constructor used only for create test tree.
     * @param value for set new node value
     * @param id for set id of new node
     */
    public NodeImpl(int id, int value) {
        this.id = id;
        this.value = value;
        parent = null;
        children = new LinkedList<>();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
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
    public boolean isRoot() {
        return id.equals(1);
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
        return new NodeDTO(getId(),getValue());
    }

    @Override
    public NodeEntity toNodeH2(){
        Integer parentId = (parent==null) ? null : parent.getId();
        return new NodeEntity(id,value,parentId);
    }

}
