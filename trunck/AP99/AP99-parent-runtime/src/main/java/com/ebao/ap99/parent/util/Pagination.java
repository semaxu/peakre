/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.parent.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.model.PageModel;
import com.ebao.ap99.parent.model.QueryVO;

/**
 * Date: Mar 31, 2016 5:17:24 PM
 * 
 * @author xiaoyu.yang
 */
public class Pagination<T> {

    public Page<T> pagination(List<T> valueList, QueryVO query) {
        Page<T> page = new Page<T>();
        if (CollectionUtils.isNotEmpty(valueList)) {
            PageModel<T> pm = new PageModel<T>(valueList, query.getCountPerPage(), query.getPageIndex());
            page.setPageIndex(pm.getPageIndex());
            page.setPageCount(pm.getTotalPages());
            page.setCountPerPage(pm.getPageSize());
            page.setResults(pm.getTotalRows());
            page.setRows(pm.getList());
        }
        return page;
    }
}
