package com.gatar.Controller;

import com.gatar.DataTransferObject.ChangeNodeValueDTO;
import com.gatar.DataTransferObject.MoveBranchDTO;
import com.gatar.Service.TreeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TreeControllerImpl implements TreeController {

    @Autowired
    TreeServiceImpl treeService;

    @Override
    public ResponseEntity<String> getTree() {
        return null;
    }

    @Override
    public ResponseEntity<Void> switchBranch(@RequestBody MoveBranchDTO coordinates) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addLeaf(@PathVariable String nodeId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> removeNodeWithChildren(@PathVariable String nodeId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> removeNodeWithoutChildren(@PathVariable String nodeId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> changeValue(@RequestBody ChangeNodeValueDTO newValue) {
        return null;
    }
}
