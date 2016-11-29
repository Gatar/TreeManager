package com.gatar.TreeManager.Controller;

import com.gatar.TreeManager.Domain.MoveBranchDTO;
import com.gatar.TreeManager.Domain.NodeDTO;
import com.gatar.TreeManager.Domain.ChangeNodeValueDTO;
import com.gatar.TreeManager.Service.TreeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TreeControllerImpl implements TreeController {

    @Autowired
    TreeServiceImpl treeService;

    @Override
    @RequestMapping(value = "/treemanager/getTree", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<NodeDTO> getTree() {
        return new ResponseEntity<NodeDTO>(treeService.getTree(),HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/treemanager/switchBranch", method = RequestMethod.POST)
    public ResponseEntity<Void> switchBranch(@RequestBody MoveBranchDTO coordinates) {
        boolean success = treeService.switchBranch(coordinates.getNodeId(),coordinates.getTargetNodeId());
        return new ResponseEntity<Void>((success)? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    @RequestMapping(value = "/treemanager/addLeaf/{nodeId}")
    public ResponseEntity<Void> addLeaf(@PathVariable String nodeId) {
        boolean success = treeService.addNewLeaf(Integer.parseInt(nodeId));
        return new ResponseEntity<Void>((success)? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    @RequestMapping(value = "/treemanager/removeWithChildren/{nodeId}")
    public ResponseEntity<Void> removeNodeWithChildren(@PathVariable String nodeId) {
        boolean success = treeService.removeNodeWithChildren(Integer.parseInt(nodeId));
        return new ResponseEntity<Void>((success)? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    @RequestMapping(value = "/treemanager/removeWithoutChildren/{nodeId}")
    public ResponseEntity<Void> removeNodeWithoutChildren(@PathVariable String nodeId) {
        boolean success = treeService.removeNodeWithoutChildren(Integer.parseInt(nodeId));
        return new ResponseEntity<Void>((success)? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    @RequestMapping(value = "/treemanager/changeValue", method = RequestMethod.POST)
    public ResponseEntity<Void> changeValue(@RequestBody ChangeNodeValueDTO newValue) {
        boolean success = treeService.changeNodeValue(newValue.getNodeId(),newValue.getNewValue());
        return new ResponseEntity<Void>((success)? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    @RequestMapping(value = "/treemanager/prepareForIntegrationTest")
    public ResponseEntity<Void> prepareForIntegrationTest() {
        treeService.prepareTreeForIntegrationTest();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/treemanager/saveToInternalDatabase")
    public ResponseEntity<Void> saveTreeInInternalDatabase() {
        treeService.saveTreeInInternalDatabase();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/treemanager/loadFromInternalDatabase")
    public ResponseEntity<Void> loadTreeFromInternalDatabase() {
        treeService.loadTreeFromInternalDatabase();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
