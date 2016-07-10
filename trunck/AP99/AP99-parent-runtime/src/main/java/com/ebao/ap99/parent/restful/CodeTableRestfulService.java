/**
 * Copyright 2015 eBaoTech.com All right reserved. This software is the
 * confidential and proprietary information of eBaoTech.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with eBaoTech.com.
 */
package com.ebao.ap99.parent.restful;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.parent.Page;
import com.ebao.ap99.parent.model.CodeResource;
import com.ebao.ap99.parent.model.CodeTableVO;
import com.ebao.ap99.parent.model.QueryDTO;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.ap99.parent.service.CodeTableDS;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.data.code.CodeService;
import com.ebao.unicorn.platform.data.code.domain.BusinessCodeTable;
import com.ebao.unicorn.platform.data.code.domain.BusinessCodeTableValue;
import com.ebao.unicorn.platform.foundation.context.AppUser;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.Assert;
import com.ebao.ap99.parent.dao.BusinessTransTypeDao;

/**
 * Date: Jan 13, 2016 7:01:18 PM
 * 
 * @author xiaoyu.yang
 */
@Module(com.ebao.ap99.parent.constant.Module.COMMON)
@Path("/codeTable")
public class CodeTableRestfulService extends ServerResource {

    @Autowired
    private CodeTableDS codeTableDS;
    
    @Autowired
    private BusinessTransTypeDao businessTransDao;
    
    @Autowired
    private CodeService codeTableService;
   
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @POST
    @Path("search")
    public Object searchByCode2(QueryDTO query) {
        return codeTableDS.searchByCode(query.getCode(), query.getCodeTableId());
    }

    @POST
    @Path("/page")
    public Object getPageForSearch2(QueryDTO query) {
        Page<CodeResource> page = new Page<CodeResource>();
        page = codeTableDS.findCurrentPage(query);
        return page;
    }

    @GET
    @Path("/generateCodeTable")
    public void generateCodeTable() {
        try {
            String str = codeTableDS.getCodeTableStr();
            File file = new File("D:/codeTable.js." + sdf.format(new Date()));
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fos, "utf-8");
            writer.write(str);
            writer.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	@GET
    @Path("/getCurrentUser")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Object getCurrentUser() {
    	
    	AppUser user = AppContext.getCurrentUser();
    	
        return user;
    }
    

    @GET
    @Path("/getAllUser/{moduleType}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Object getAllUser(@PathParam(value = "moduleType") String moduleType) {
    	List<TreeModel> userModel = businessTransDao.getAllUser(moduleType);
    	
        return userModel;
    }
    
    
    @GET
    @Path("/country")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Object getCountry() {
    	List<TreeModel> countryModel = businessTransDao.getCountry();
    	
        return countryModel;
    }
    
    @Autowired
    com.ebao.unicorn.platform.data.restful.CodeTableRestfulService unicornCodeTableRestfulService;
    /**
     * @see  $pt.ComponentConstants.CODETABLE_PARENT_VALUE_KEY = parentId
     * @param codeTableId
     * @param filterMap
     * @return
     * @throws Exception
     */
    @POST
    @Path("/generate/{codeTableId}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public List<CodeTableVO> getCodeTableById(@PathParam(value = "codeTableId") Long codeTableId, Map filterMap) throws Exception {
    	List<CodeTableVO> codeTableList = new ArrayList<CodeTableVO>();
    	String parentId = null;
    	if (filterMap != null && filterMap.get("parentId") != null)
    		parentId = (String) filterMap.get("parentId");
    	
    	List<BusinessCodeTableValue> valueList = unicornCodeTableRestfulService.getAllCodeTableValue(codeTableId, com.ebao.unicorn.platform.foundation.context.AppContext.getCurrentUser().getLangId());
    	for (BusinessCodeTableValue item : valueList) {
    		CodeTableVO t = new CodeTableVO();
    		t.setId(item.getId());
    		t.setText(item.getDescription());
    		if (CollectionUtils.isNotEmpty(item.getConditionFieldList()) && item.getConditionFieldList().size() == 1)
    			t.setParentId(item.getConditionFieldList().get(0).values().toArray()[0]);
    		
    		if (StringUtils.isBlank(parentId) || parentId.equals(String.valueOf(t.getParentId())))
    			codeTableList.add(t);
    	}
    	
        return codeTableList;
    }

    
    @POST
    @Path("/generateTree/{codeTableId}/{level}/{parentId}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public List<TreeModel> getSelectTreeFromDD(@PathParam(value = "codeTableId") Long codeTableId,
    		@PathParam(value="level") Integer level,
    		@PathParam(value="parentId") String parentId) throws Exception {
    	List<TreeModel> treeModel = new ArrayList<TreeModel>();
    	Map<String, Object> conditions = new HashMap<String,Object>();
    	codeTableService.findCodeTableById(codeTableId);
    	conditions.put("PARENT_ID", parentId);
    	List<BusinessCodeTableValue> valueList = codeTableService.findCodeTableValueListByConditionFields$CodeTableId(conditions, codeTableId, com.ebao.unicorn.platform.foundation.context.AppContext.getCurrentUser().getLangId());
    	if(null != valueList){
    		for (BusinessCodeTableValue item : valueList) {
        		TreeModel node = new TreeModel();
        		node.setId(item.getId());
        		node.setText(item.getDescription());
        		node.setLevel(level);
        		node.setChildren(getSelectTreeFromDD(codeTableId,level+1,item.getId().toString()));
        		treeModel.add(node);    		
        	}
    	}
   	
        return treeModel;
    }
    
	@POST
	@Path("/generateByCondition/{codeTableName}")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public List<CodeTableVO> getCodeTableValueByNameAndConditions(
			@PathParam("codeTableName") String codeTableName, @BeanParam HashMap conditionMap) throws Exception {
		Assert.isNotNull(codeTableName, "codeTableName is null.", new Object[0]);
		if (conditionMap == null) {
			conditionMap = new HashMap();
		}

		String langId = AppContext.getCurrentUser().getLangId();

		List<BusinessCodeTableValue> valueList =  this.codeTableService.findCodeTableValueListByConditionFields$CodeTableId(conditionMap,
				Long.valueOf(codeTableName), langId);
		
		return convertDDToCodeTable(valueList);
	}
	
	private List<CodeTableVO> convertDDToCodeTable(List<BusinessCodeTableValue> valueList){
		List<CodeTableVO> codeTableList = new ArrayList<CodeTableVO>();
		for (BusinessCodeTableValue item : valueList) {
    		CodeTableVO t = new CodeTableVO();
    		t.setId(item.getId());
    		t.setText(item.getDescription());
    		if (CollectionUtils.isNotEmpty(item.getConditionFieldList()) && item.getConditionFieldList().size() == 1)
    			t.setParentId(item.getConditionFieldList().get(0).values().toArray()[0]);
    			codeTableList.add(t);
    	}
		return codeTableList;
	}
}
