package com.gatar.DataTransferObject;

import java.util.LinkedList;

public class NodeDTO implements Comparable<NodeDTO>{

    private Integer nodeId;
    private Integer value;
    private LinkedList<NodeDTO> children = new LinkedList<>();

    public NodeDTO() {
    }

    public NodeDTO(Integer nodeId, Integer value) {
        this.value = value;
        this.nodeId = nodeId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LinkedList<NodeDTO> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<NodeDTO> children) {
        this.children = children;
    }

    @Override
    public int compareTo(NodeDTO o) {
        int result=0;
        if(nodeId == o.nodeId){
            if(children.size() == o.children.size()){
                for(int counter = 0; counter < children.size(); counter++){
                    result = children.get(counter).compareTo(o.children.get(counter));
                }
            } else return -1;
        } else return -1;
        return result;
    }

}
