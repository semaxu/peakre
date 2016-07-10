/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.parent.service;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.model.CodeResource;
import com.ebao.ap99.parent.model.QueryDTO;

/**
 * Date: Jan 15, 2016 2:41:18 PM
 * 
 * @author xiaoyu.yang
 */
public interface CodeTableDS {

    CodeResource searchByCode(String code, Long codeTableId);

    Page<CodeResource> findCurrentPage(QueryDTO query);

    String getCodeTableStr();
    
    public String getCodeTableDesc(Long codeTableId, String code, String langId) throws Exception;

}
