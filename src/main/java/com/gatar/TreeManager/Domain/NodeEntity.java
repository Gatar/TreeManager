package com.gatar.TreeManager.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Node entity class for save tree in H2 relational database as Adjacency List.
 */
@Entity
public class NodeEntity {

    @Id
    private Integer id;

    @Column
    private Integer value;

    @Column
    private Integer parentId;

    public NodeEntity() {
    }

    public NodeEntity(Integer id, Integer value, Integer parentId) {
        this.id = id;
        this.value = value;
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public NodeImpl toNode(){
        return new NodeImpl(id,value);
    }
}
