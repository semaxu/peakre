/**
 * 
 */
package com.ebao.ap99.accounting.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.accounting.entity.TRiAcctSegment;
import com.ebao.ap99.accounting.model.IBNRrecord;
import com.ebao.ap99.accounting.model.TRiAcctSegmentVO;
import com.ebao.ap99.accounting.service.impl.SegmentDSImpl;
import com.ebao.ap99.accounting.util.AccountingCst;
import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.constant.NumberingType;
import com.ebao.unicorn.platform.foundation.numbering.NumberingService;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author xiaoyu.yang
 */
@Module(com.ebao.ap99.parent.constant.Module.ACCOUNTING)
@Path("/ibnr")
public class SegmentRestfulService extends ServerResource {

    private static Logger logger = LoggerFactory.getLogger();

    @Autowired
    private NumberingService numService;

    @Autowired
    private SegmentDSImpl segmentDSImpl;

    /**
     * Search Segment Page Info
     * 
     * @param segmentDTO
     * @return Segment Page
     */
    @POST
    @Path("/page")
    public Page<TRiAcctSegment> querySegments(TRiAcctSegmentVO segmentDTO) {
        Page<TRiAcctSegment> page = new Page<TRiAcctSegment>();
        page.setPageIndex(segmentDTO.getPageIndex());
        page.setCountPerPage(segmentDTO.getCountPerPage());
        TRiAcctSegment segment = new TRiAcctSegment();
        BeanUtils.copyProperties(segmentDTO, segment);
        page.setCondition(segment);
        page = segmentDSImpl.findCurrentPage(page);
        return page;
    }

    /**
     * Get IBNR Info by SegmentId
     * 
     * @param segmentId
     * @return IBNR Info
     */
    @GET
    @Path("/loadIbnr/{segmentId}")
    public List<IBNRrecord> findIbnrBySegmentId(@PathParam("segmentId") Long segmentId) {
        return segmentDSImpl.findIBNRbySegmentId(segmentId);
    }

    /**
     * Get Segment Info by SegmentId
     * 
     * @param segmentId
     * @return TRiAccSegment
     */
    @GET
    @Path("/load/{segmentId}")
    public TRiAcctSegment loadSegmentBySegmentId(@PathParam("segmentId") Long segmentId) {
        return segmentDSImpl.getSegment(segmentId);
    }

    /**
     * Generate SegmentCode
     * 
     * @return
     */
    @GET
    @Path("/generateCode")
    public Map<String, String> generateSegmentCode() {
        Map<String, String> map = new HashMap<String, String>();
        try {
            map.put("segmentCode",
                    numService.generateNumber(NumberingType.RI_ACC_SEGMENT_CODE, new HashMap<String, String>()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return map;
    }

    /**
     * Create Segment
     * 
     * @param segment
     * @return success
     */
    @POST
    @Path("/create")
    public Map<String, String> createSegment(TRiAcctSegment segment) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_FAIL);
        if (validate(segment)) {
            Long segmentId = segmentDSImpl.saveOrUpdateSegment(segment);
            if (segmentId != 0L) {
                map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_SUCCESS);
            }
        }
        return map;
    }

    /**
     * Update Status of Segment
     * 
     * @param segment
     * @return
     */
    @POST
    @Path("/update")
    public Map<String, String> updateSegment(TRiAcctSegment segment) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_FAIL);
        TRiAcctSegment accSegment = segmentDSImpl.getSegment(segment.getSegmentId());
        TRiAcctSegment temp = new TRiAcctSegment();
        BeanUtils.copyProperties(accSegment, temp);
        if (segment.getStatus() != null) {
            temp.setStatus(segment.getStatus());
        }
        Long segmentId = segmentDSImpl.saveOrUpdateSegment(temp);
        if (segmentId != 0L) {
            map.put(AccountingCst.RESTFUL_RESULT, AccountingCst.RESTFUL_RESULT_SUCCESS);
        }
        return map;
    }

    /**
     * Check for Required Fields
     * 
     * @param segment
     * @return true
     */
    private boolean validate(TRiAcctSegment segment) {
        return StringUtils.isNotBlank(segment.getSegmentCode()) && StringUtils.isNotBlank(segment.getSegmentName())
                && segment.getStatus() != null && StringUtils.isNotBlank(segment.getCob())
                && StringUtils.isNotBlank(segment.getCedentCountry())
                && StringUtils.isNotBlank(segment.getContractNature());
    }

}
