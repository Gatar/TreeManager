package com.gatar.DataTransferObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.Objects;

public class NodeDTO implements Comparable<NodeDTO>{

    private Text text;
    private LinkedList<NodeDTO> children = new LinkedList<>();

    @JsonIgnore
    private int nodeId;

    public NodeDTO() {
    }

    public NodeDTO(int nodeId, Integer value) {
        this.text = new Text(value);
        this.nodeId = nodeId;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public LinkedList<NodeDTO> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<NodeDTO> children) {
        this.children = children;
    }

    private class Text{
        private Integer value;

        public Text() {
        }

        public Text(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
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
