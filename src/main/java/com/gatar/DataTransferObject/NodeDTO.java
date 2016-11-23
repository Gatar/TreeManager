package com.gatar.DataTransferObject;

import com.gatar.Model.RootSingleton;

public class NodeDTO {


    public NodeDTO() {
    }

    private Text text;

    private Integer parentId;

    private class Text{
        private Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    private class Link{
        //Here will be links for manage each node

    }
}
