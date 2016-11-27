package com.gatar.Service;

import com.gatar.Model.Node;
import com.gatar.Model.RootSingleton;
import org.springframework.stereotype.Service;

import java.util.Stack;

/**
 * Class used for provide only search of one node, based on node id value.
 * Used search method - Depth-first search
 */
@Service
public class TreeDFS {

    private Node actualNode;
    private Stack<Integer> stack;


    /**
     * Start searching.
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
        actualNode = RootSingleton.getRoot();
    }

    private void incrementStackValue(){
        Integer last = stack.pop();
        last++;
        stack.push(last);
    }
}
