package com.gatar.TreeManager.Service;

import com.gatar.TreeManager.Model.InMemoryTree;
import com.gatar.TreeManager.Domain.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Stack;

/**
 * Class used for provide only search of one node, based on node id value.
 * Used search method - Depth-first search
 */
@Component
public class TreeDFS {

    @Autowired
    InMemoryTree inMemoryTree;

    private Node actualNode;
    private Stack<Integer> stack;


    /**
     * Start searching with return node reference.
     * @param targetId id of target node.
     * @return node reference if node exist, if node not exist return null
     */
    public Node search(int targetId) {
        clearParameters();
        if (targetId < 1) return null;

        while (true) {
            if (stack.empty()) return null;
            if (actualNode.getId() == targetId) return actualNode;

            if (actualNode.getChildren().size() > stack.peek()) {
                actualNode = actualNode.getChild(stack.peek());
                incrementStackValue();
                stack.push(0);
            } else {
                stack.pop();
                actualNode = actualNode.getParent();
            }
        }
    }

    private void clearParameters(){
        stack = new Stack<>();
        stack.push(0);
        actualNode = inMemoryTree.getRoot();
    }

    private void incrementStackValue(){
        Integer last = stack.pop();
        last++;
        stack.push(last);
    }

}
