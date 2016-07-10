/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.parent.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: Jan 25, 2016 10:35:49 AM
 * 
 * @author xiaoyu.yang
 */
public class TreeNode implements Serializable {

    private static final long serialVersionUID = 5815976123102645165L;

    private Long           id;
    private String         text;
    private Long           pid;
    private List<TreeNode> nodes = new ArrayList<TreeNode>();

    public TreeNode() {
    }

    public TreeNode(Long id, String text) {
        super();
        this.id = id;
        this.text = text;
    }

    public TreeNode(Long id, String text, Long pid) {
        super();
        this.id = id;
        this.text = text;
        this.pid = pid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

}
