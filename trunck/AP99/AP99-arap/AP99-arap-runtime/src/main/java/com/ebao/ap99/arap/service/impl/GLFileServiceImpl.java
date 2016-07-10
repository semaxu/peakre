package com.ebao.ap99.arap.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.constant.CurrencyConstants;
import com.ebao.ap99.arap.constant.PostStatus;
import com.ebao.ap99.arap.dao.GLExDetailDao;
import com.ebao.ap99.arap.dao.GLFileInfoDao;
import com.ebao.ap99.arap.dao.GLGeneralLedgerDao;
import com.ebao.ap99.arap.dao.GLLogDao;
import com.ebao.ap99.arap.entity.GLExDetail;
import com.ebao.ap99.arap.entity.GLFileInfo;
import com.ebao.ap99.arap.entity.GLGeneralLedger;
import com.ebao.ap99.arap.service.GLFileService;
import com.ebao.ap99.arap.service.GLService;
import com.ebao.ap99.file.service.FileService;

public class GLFileServiceImpl implements GLFileService {
	private Logger logger=LoggerFactory.getLogger(GLFileServiceImpl.class);
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private GLFileInfoDao glFileInfoDao;
	
	@Autowired
	private GLLogDao glLogDao;
	
	@Autowired
	private GLGeneralLedgerDao glGeneralLedgerDao;

	@Autowired
	private GLExDetailDao glExDetailDao;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
	
	@Override
	public List<GLFileInfo> generateGLFile() throws Exception{
		logger.info("==============generateGLFile===============");
		List<GLFileInfo> glFileList = new ArrayList<GLFileInfo>();
		List<Date> postPendingDateList = glFileInfoDao.getPostPendingDate();
		if(CollectionUtils.isNotEmpty(postPendingDateList)){
			GLFileInfo glFile;
			for(Date postPendingDate : postPendingDateList){
				glFile = generateGLFileByPostingDate(postPendingDate);
				if(CollectionUtils.isNotEmpty(glFile.getFileDataList())){
					glFileList.add(glFile);
				}
			}
		}
		return glFileList;
	}

	@Override
	public void hanleReturnedGLFile() throws Exception {
		// TODO Auto-generated method stub

	}

	
	private GLFileInfo generateGLFileByPostingDate(Date postDate) throws Exception{
		logger.info("==============generateGLFileByPostingDate==============="+postDate);
		GLFileInfo glFileInfo = new GLFileInfo();
		String message = "Generate GL File...";
		List<GLGeneralLedger> postDataList = glGeneralLedgerDao.getPostPendingData(postDate);
		fillGLExchangeData(postDataList);
		
		if(CollectionUtils.isNotEmpty(postDataList)){
			List<GLFileInfo> glFileInfoList = glFileInfoDao.getByFileDate(postDate);
			if(CollectionUtils.isNotEmpty(glFileInfoList)){
				GLFileInfo lastFileInfo = glFileInfoList.get(0);
				if(StringUtils.isBlank(lastFileInfo.getFileName())){
					//Last file generation is failed, continue to update existing file info
					glFileInfo = lastFileInfo;
				}else{
					//Last file generation is successful, need to generate new file for outstanding GL data with increasing file number
					glFileInfo.setFileNum(lastFileInfo.getFileNum());
				}
			}
			glFileInfo.setFileDate(postDate);
			glFileInfo.setFileDataList(postDataList);
			try{
				Integer fileNum = glFileInfo.getFileNum() == null ? 1 : glFileInfo.getFileNum() + 1;
				StringBuffer fileName = new StringBuffer();
				fileName.append(GL_FILE_PREFIX)
						.append("_").append(dateFormat.format(postDate)).append("_")
						.append(StringUtils.leftPad(fileNum.toString(), GL_FILE_NUM_LEN, "0"))
						.append(".TXT");
				glFileInfo.setFileName(fileName.toString());
				glFileInfo.setFileNum(fileNum);
				glFileInfo.setFilePath(GL_FILE_FOLDER);
				
				//Generate physical GL file
				File glFile = createGLFile(glFileInfo);
				
				if(glFile != null && glFile.exists()){
					glFileInfo.setFilePath(glFile.getAbsolutePath());
					for(GLGeneralLedger glData : postDataList){
						glData.setPostStatus(PostStatus.POSTED.getType());
						glGeneralLedgerDao.insertOrUpdate(glData);
					}
					message += "Successful!  " + glFileInfo.getSummaryInfo();
				}
			}catch(Exception e){
				glFileInfo.rollBack();
				message += "Fail!  Error Message :" + e.toString();
			}
			glFileInfoDao.insertOrUpdate(glFileInfo);
			glLogDao.log(message, glLogDao.LOG_TYPE_FILE, glFileInfo.getFileId());
		}
		return glFileInfo;
	}
	
	private void fillGLExchangeData(List<GLGeneralLedger> glList) throws Exception{
		if(CollectionUtils.isNotEmpty(glList)){
			List<GLExDetail> exList;
			for(GLGeneralLedger gl : glList){
				exList = glExDetailDao.findBySourceTypeAndSourceId(GLService.GL_EX_SOURCE_GENERAL_LEDGER, gl.getGeneralLedgerId());
				if(CollectionUtils.isNotEmpty(exList)){
					gl.setExDetailList(exList);
				}
			}
		}
	}
	
	private File createGLFile(GLFileInfo glFileInfo) throws Exception{
		File file = null;
		StringBuffer fileContext = new StringBuffer();
		List<GLGeneralLedger> postDataList = glFileInfo.getFileDataList();
		String rowData;
		for(GLGeneralLedger glData : postDataList){
			rowData = buildGLFileRow(glData);
			if(StringUtils.isNotBlank(rowData)){
				fileContext.append(rowData).append(FILE_NEW_LINE);
			}
		}
		if(fileContext.length() > 0){
			String relativeFilePath = glFileInfo.getFilePath() + File.separator + glFileInfo.getFileName();
			file = fileService.writePhysicalFile(new ByteArrayInputStream(fileContext.toString().getBytes()), relativeFilePath);
		}
		return file;
	}
	
	private String buildGLFileRow(GLGeneralLedger rowData) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("ddMMYYYY");
		List<GLExDetail> exList = rowData.getExDetailList();
		StringBuffer row = new StringBuffer();
		row.append(rowData.getDocDate() == null? "" : format.format(rowData.getDocDate()));
		row.append(GL_FILE_SPLIT).append(rowData.getReference() == null ? "" : rowData.getReference());
		row.append(GL_FILE_SPLIT).append(rowData.getGlAccountNo() == null ? "" : rowData.getGlAccountNo());
		row.append(GL_FILE_SPLIT).append(rowData.getGlAccountType() == null ? "" : rowData.getGlAccountType());
		String currencyCode;
		for(int idx = 0; idx < CurrencyConstants.GL_CURRENCY_CODE_ARRAY.length; idx++){
			currencyCode = CurrencyConstants.GL_CURRENCY_CODE_ARRAY[idx];
			row.append(GL_FILE_SPLIT).append(currencyCode);
			if(CollectionUtils.isNotEmpty(exList)){
				for(GLExDetail exDetail : exList){
					if(currencyCode.equalsIgnoreCase(exDetail.getCurrencyCode())){
						row.append(GL_FILE_SPLIT).append(exDetail.getAmount() == null ? "" : exDetail.getAmount());
						currencyCode = null;//reset currency value after match existing exchange currency amount
						break;
					}
				}
				if(currencyCode != null){//set default amount as blank in case of missing exchange currency amount
					row.append(GL_FILE_SPLIT).append("");
				}
			}
		}
		row.append(GL_FILE_SPLIT).append(rowData.getValueDate() == null ? "" : format.format(rowData.getValueDate()));
		row.append(GL_FILE_SPLIT).append(rowData.getPostDate() == null ? "" : format.format(rowData.getPostDate()));
		return row.toString();
	}
}
