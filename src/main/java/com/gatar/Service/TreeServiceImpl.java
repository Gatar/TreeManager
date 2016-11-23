package com.gatar.Service;

import com.gatar.Model.Node;
import com.gatar.Model.RootSingleton;
import org.springframework.stereotype.Service;

@Service
public class TreeServiceImpl implements TreeService{

    @Override
    public String getTree() {
        Node root = RootSingleton.getRoot();
        return null;
    }

    @Override
    public void switchBranch(int nodeId, int newBranchNodeId) {

    }

    @Override
    public void addLeaf(int nodeId) {

    }

    @Override
    public void removeNode(int nodeId) {

    }

    @Override
    public void changeNodeValue(int nodeId, int value) {

    }
}
