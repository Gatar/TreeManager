package com.gatar.TreeManager.Controller;

import com.gatar.TreeManager.DataTransferObject.MoveBranchDTO;
import com.gatar.TreeManager.DataTransferObject.ChangeNodeValueDTO;
import com.gatar.TreeManager.DataTransferObject.NodeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface TreeController {

    /**
     * Get all tree structure as JSON value.
     * @return HttpEntity with String containing tree in body.
     */
    ResponseEntity<NodeDTO> getTree();

    /**
     *  Move branch to other place.
     * @param coordinates {@link MoveBranchDTO} object containing id of moved and target node
     * @return Entity with empty body and automatic set HttpStatus.
     */
    ResponseEntity<Void> switchBranch(@RequestBody MoveBranchDTO coordinates);

    /**
     * Add new leaf to selected node.
     * @param nodeId new parent node id
     * @return Entity with empty body and automatic set HttpStatus.
     */
    ResponseEntity<Void> addLeaf(@PathVariable String nodeId);

    /**
     * Delete selected node with all its children.
     * @param nodeId node for delete id
     * @return Entity with empty body and automatic set HttpStatus.
     */
    ResponseEntity<Void> removeNodeWithChildren(@PathVariable String nodeId);

    /**
     * Delete only selected node, their children are set as child of parent.
     * @param nodeId node for delete id
     * @return Entity with empty body and automatic set HttpStatus.
     */
    ResponseEntity<Void> removeNodeWithoutChildren(@PathVariable String nodeId);

    /**
     * Change value of node by its id.
     * @param newValue {@link ChangeNodeValueDTO} object containing id and new value for node
     * @return Entity with empty body and automatic set HttpStatus.
     */
    ResponseEntity<Void> changeValue(@RequestBody ChangeNodeValueDTO newValue);

    /**
     * Clear tree and load test tree for test with client.
     * @return Entity with empty body and automatic set HttpStatus.
     */
    ResponseEntity<Void> prepareForIntegrationTest();
}
