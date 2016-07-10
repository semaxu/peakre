/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.accounting.convertor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.accounting.entity.TRiAcctQuarterClosing;
import com.ebao.ap99.accounting.model.TRiAcctQuarterClosingVO;
import com.ebao.ap99.parent.Page;

/**
 * Date: Jan 18, 2016 9:37:26 AM
 * 
 * @author xiaoyu.yang
 */
public class QuarterClosingConvertor {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static Page<TRiAcctQuarterClosingVO> convertToClosingDTOPage(Page<TRiAcctQuarterClosing> page) {
        Page<TRiAcctQuarterClosingVO> retPage = new Page<TRiAcctQuarterClosingVO>();
        retPage.setCountPerPage(page.getCountPerPage());
        retPage.setPageCount(page.getPageCount());
        retPage.setPageIndex(page.getPageIndex());
        List<TRiAcctQuarterClosing> list = page.getRows();
        List<TRiAcctQuarterClosingVO> retList = new ArrayList<TRiAcctQuarterClosingVO>();
        for (TRiAcctQuarterClosing tRiAcctQuarterClosing : list) {
            retList.add(convertToClosingDTO(tRiAcctQuarterClosing));
        }
        retPage.setRows(retList);
        return retPage;
    }

    public static TRiAcctQuarterClosingVO convertToClosingDTO(TRiAcctQuarterClosing tRiAcctQuarterClosing) {
        TRiAcctQuarterClosingVO temp = new TRiAcctQuarterClosingVO();
        //        BeanUtils.copyProperties(tRiAcctQuarterClosing, temp);
        temp.setQuarterId(tRiAcctQuarterClosing.getQuarterId());
        temp.setQuarter(tRiAcctQuarterClosing.getFinancialYear() + "Q" + tRiAcctQuarterClosing.getFinancialQuarter());
        temp.setClosingStatus(tRiAcctQuarterClosing.getClosingStatus());
        if (tRiAcctQuarterClosing.getClosingDate() != null) {
            temp.setClosingDate(sdf.format(tRiAcctQuarterClosing.getClosingDate()));
        }
        if (tRiAcctQuarterClosing.getStartDate() != null) {
            temp.setStartDate(sdf.format(tRiAcctQuarterClosing.getStartDate()));
        }
        if (tRiAcctQuarterClosing.getClosedDate() != null) {
            temp.setClosedDate(sdf.format(tRiAcctQuarterClosing.getClosedDate()));
        }
        return temp;
    }

}
