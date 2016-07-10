/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.parent.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.model.CodeResource;
import com.ebao.ap99.parent.model.PageModel;
import com.ebao.ap99.parent.model.QueryDTO;
import com.ebao.ap99.parent.service.CodeTableDS;
import com.ebao.unicorn.platform.data.code.CodeService;
import com.ebao.unicorn.platform.data.code.domain.BusinessCodeTable;
import com.ebao.unicorn.platform.data.code.domain.BusinessCodeTableValue;

/**
 * Date: Jan 15, 2016 2:41:29 PM
 * 
 * @author xiaoyu.yang
 */
public class CodeTableDSImpl implements CodeTableDS {

    @Autowired
    private CodeService codeTableService;

    @Override
    public CodeResource searchByCode(String code, Long codeTableId) {
        BusinessCodeTableValue value = this.codeTableService.findCodeTableValueById$CodeTableId(code, codeTableId,
                null);
        if (value != null) {
            return new CodeResource(value.getCode(), value.getDescription());
        } else {
            return new CodeResource("", "");
        }

    }

    /**
     * Get Current Page for Search Component by codeTableId
     */
    @Override
    public Page<CodeResource> findCurrentPage(QueryDTO query) {
        String keyword = "";
        if (StringUtils.isNoneBlank(query.getName())) {
            keyword = query.getName();
        }
        List<BusinessCodeTableValue> valueList = this.codeTableService
                .findCodeTableValueListByKeyword$CodeTableId(keyword, query.getCodeTableId(), null);
        //        Map<String, Object> condition = new HashMap<String, Object>();
        //        condition.put("BUSINESS_PARTNER_CATEGORY", "1");
        //        condition.put("STATUS", "2");
        //        List<BusinessCodeTableValue> valueList = this.codeTableService
        //                .findCodeTableValueListByConditionFields$CodeTableId(condition, query.getCodeTableId(), null);
        return pagination(valueList, query);
    }

    private Page<CodeResource> pagination(List<BusinessCodeTableValue> valueList, QueryDTO query) {
        Page<CodeResource> page = new Page<CodeResource>();
        if (CollectionUtils.isNotEmpty(valueList)) {
            PageModel<BusinessCodeTableValue> pm = new PageModel<BusinessCodeTableValue>(valueList,
                    query.getCountPerPage(), query.getPageIndex());
            page.setPageIndex(pm.getPageIndex());
            page.setPageCount(pm.getTotalPages());
            page.setCountPerPage(pm.getPageSize());
            page.setResults(pm.getTotalRows());
            List<CodeResource> list = new ArrayList<CodeResource>();
            for (BusinessCodeTableValue businessCodeTableValue : pm.getList()) {
                list.add(new CodeResource(businessCodeTableValue.getCode(), businessCodeTableValue.getDescription()));
            }
            page.setRows(list);
        }
        return page;
    }

    /**
     * Generate String of CodeTable
     */
    @Override
    public String getCodeTableStr() {
        StringBuilder sb = new StringBuilder("(function (context) {\n");
        sb.append("var $page = $pt.getService(context, '$page');\n");
        sb.append("var codeTable = $pt.getService($page, 'codeTable');\n");

        List<BusinessCodeTable> codeTableList = codeTableService.findCodeTableListByContextId(100L);
        for (BusinessCodeTable businessCodeTable : codeTableList) {
            sb.append("codeTable." + businessCodeTable.getName() + " = $pt.createCodeTable([");
            //            if (StringUtils.isNoneBlank(businessCodeTable.get)) {
            //                TreeNode treeNode = treeNodeDao.recursiveTree(1L, codeTable);
            //                generateTreeNodeStr(treeNode, sb);
            //            } else {
            //                List<TreeNode> treeNodes = treeNodeDao.getTreeNodeList(codeTable);
            //                generateTreeNodeStr(treeNodes, sb);
            //            }
            List<BusinessCodeTableValue> codeTableValueList = codeTableService
                    .findCodeTableValueListByKeyword$CodeTableId("", businessCodeTable.getCodeTableId(), null);
            generateTreeNodeStr(codeTableValueList, sb);
            sb.append("]);\n");
        }
        sb.append("}(typeof window !== \"undefined\" ? window : this));");
        return sb.toString();
    }

    /**
     * Generate CodeTable by TreeModel
     * 
     * @param treeNode
     * @param sb
     */
    private void generateTreeNodeStr(List<BusinessCodeTableValue> codeTableValueList, StringBuilder sb) {
        for (BusinessCodeTableValue businessCodeTableValue : codeTableValueList) {
            sb.append("{id:" + businessCodeTableValue.getId() + ",text:'" + businessCodeTableValue.getDescription()
                    + "'},");
        }
        sb.deleteCharAt(sb.length() - 1);
    }

	@Override
	public String getCodeTableDesc(Long codeTableId, String code, String langId) throws Exception {		
		return codeTableService.findCodeTableValueByCode$CodeTableId(code, codeTableId, langId).getDescription();
	}

    //    /**
    //     * Generate CodeTable by TreeModel
    //     * 
    //     * @param treeNode
    //     * @param sb
    //     */
    //    private void generateTreeNodeStr(TreeNode treeNode, StringBuilder sb) {
    //        if (0L != treeNode.getPid()) {
    //            sb.append("{id:" + treeNode.getId() + ",text:'" + treeNode.getText() + "'");
    //        }
    //        if (0L != treeNode.getPid() && !treeNode.getNodes().isEmpty()) {
    //            sb.append(",children:[");
    //        }
    //        if (!treeNode.getNodes().isEmpty()) {
    //            for (TreeNode temp : treeNode.getNodes()) {
    //                generateTreeNodeStr(temp, sb);
    //                sb.append(",");
    //            }
    //            sb.deleteCharAt(sb.length() - 1);
    //        }
    //        if (0L != treeNode.getPid() && !treeNode.getNodes().isEmpty()) {
    //            sb.append("]");
    //        }
    //        if (0L != treeNode.getPid()) {
    //            sb.append("}");
    //        }
    //    }

    //    /**
    //     * Generate CodeTable by List
    //     * 
    //     * @param treeNodes
    //     * @param sb
    //     */
    //    private void generateTreeNodeStr(List<TreeNode> treeNodes, StringBuilder sb) {
    //        for (TreeNode treeNode : treeNodes) {
    //            sb.append("{id:" + treeNode.getId() + ",text:'" + treeNode.getText() + "'},");
    //        }
    //        sb.deleteCharAt(sb.length() - 1);
    //    }

}
