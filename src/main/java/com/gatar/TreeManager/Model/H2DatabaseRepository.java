package com.gatar.TreeManager.Model;

import com.gatar.TreeManager.Domain.NodeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface H2DatabaseRepository extends CrudRepository <NodeEntity,Integer> {
    List<NodeEntity> findAll();
    void deleteAll();
}
