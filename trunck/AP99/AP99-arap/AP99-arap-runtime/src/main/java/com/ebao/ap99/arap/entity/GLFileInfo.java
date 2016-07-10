package com.ebao.ap99.arap.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ebao.unicorn.platform.data.domain.BaseEntityImpl;


/**
 * The persistent class for the T_RI_GL_FILE database table.
 * 
 */
@Entity
@Table(name="T_RI_GL_FILE")
@NamedQuery(name="GLFileInfo.findAll", query="SELECT g FROM GLFileInfo g")
@NamedQueries({
	@NamedQuery(name = "GLFileInfo.findByPostDate", query = " FROM GLFileInfo c WHERE c.fileDate = :fileDate order by c.fileNum desc")
})
public class GLFileInfo extends BaseEntityImpl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "S_UID")
	@Column(name="FILE_ID")
	private Long fileId;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="FILE_NUM")
	private Integer fileNum;

	@Temporal(TemporalType.DATE)
	@Column(name="FILE_DATE")
	private Date fileDate;
	
	@Column(name="FILE_RETURN_NAME")
	private String fileReturnName;
	
	@Column(name="FILE_PATH")
	private String filePath;

	@Column(name="FILE_RETURN_STATUS")
	private Integer fileReturnStatus;
	
	@Transient
	private List<GLGeneralLedger> fileDataList;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileNum() {
		return fileNum;
	}

	public void setFileNum(Integer fileNum) {
		this.fileNum = fileNum;
	}

	public Date getFileDate() {
		return fileDate;
	}

	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

	public String getFileReturnName() {
		return fileReturnName;
	}

	public void setFileReturnName(String fileReturnName) {
		this.fileReturnName = fileReturnName;
	}

	public Integer getFileReturnStatus() {
		return fileReturnStatus;
	}

	public void setFileReturnStatus(Integer fileReturnStatus) {
		this.fileReturnStatus = fileReturnStatus;
	}

	@Override
	public Long getPrimaryKey() {
		return this.getFileId();
	}

	@Override
	public void setPrimaryKey(Long key) {
		this.setFileId(key);
	}

	public List<GLGeneralLedger> getFileDataList() {
		return fileDataList;
	}

	public void setFileDataList(List<GLGeneralLedger> fileDataList) {
		this.fileDataList = fileDataList;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getSummaryInfo(){
		StringBuffer info = new StringBuffer();
		info.append("[FileName : ").append(this.getFileName());
		info.append("] [FilePath : ").append(this.getFilePath());
		info.append("] [FileDate : ").append(this.getFileDate());
		info.append("] [FileNumber : ").append(this.getFileNum());
		info.append("] [FileDataCount : ").append(this.getFileDataList() == null? 0 : getFileDataList().size());
		info.append("]");
		return info.toString();
	}
	
	public void rollBack(){
		setFileName(null);
		setFilePath(null);
		setFileDataList(null);
	}
}