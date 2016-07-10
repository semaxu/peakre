/**
 * 
 */
package com.ebao.ap99.accounting.UI.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebao.ap99.accounting.model.SoaModel;

/**
 * @author Sema.Xu
 */
public class SoaViewPage<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8338235914409958290L;

    /**
     * 分页查询时，每页的最大记录数
     */
    public static final int MAX_LIMIT = 1000;

    private int pageIndex = 1;

    private long pageCount = 1;
    
    
    private String ContractCurrecy;
    
    private String SectionCount;
     
    private Boolean reviewedFlag;
    
    private String reviewedBy;
    
    private String taskcreatorName;
    
    private String taskreleaserName;
    
    private String takswithdrawName;
    
    private SoaModel soaM;
    
    private String statementType;
    
    

    /**
     * 最大查询数
     */
    private int countPerPage = 10;

    /**
     * 起始记录
     */
    private int start = 0;

    /**
     * 总记录数
     */
    private long results = 0;

    /**
     * 记录列表
     */
    private List<T> rows = new ArrayList<T>();

    /**
     * 查询条件
     */
    private T condition;

    /**
     * 查询返回记录数
     */
    private int size = 0;

    public SoaViewPage() {
        super();
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public int getCountPerPage() {
        return countPerPage;
    }

    public void setCountPerPage(int countPerPage) {
        if (countPerPage > SoaViewPage.MAX_LIMIT) {
            throw new RuntimeException("超出最大记录数");
        }
        this.countPerPage = countPerPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getResults() {
        return results;
    }

    public void setResults(long results) {
        this.results = results;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.size = rows != null ? rows.size() : 0;
        this.rows = rows;
    }

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    

    /**
     * 封装EasyUI datagrid表格分页
     * 
     * @return
     */
    public Map<String, Object> getPageJsonMap(SoaViewPage<T> dataList) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (null != dataList) {
            jsonMap.put("total", dataList.getResults() >= 0 ? dataList.getResults() : 0);// total键
                                                                                         // 存放总记录数，必须的
            if (dataList.getRows() != null) {
                jsonMap.put("rows", dataList.getRows().size() > 0 ? dataList.getRows() : "");// rows键
            }
        }
        return jsonMap;
    }

    /**
     * 封装EasyUI datagrid中起始页数，记录列表
     * 
     * @return
     */
    public void setPageRow(int page, int rows) {
        page = page == 0 ? 1 : page;
        rows = rows == 0 ? 10 : rows;
        int start = rows * (page - 1);
        this.setStart(start);
        this.setCountPerPage(rows);
    }

	public String getContractCurrecy() {
		return ContractCurrecy;
	}

	public void setContractCurrecy(String contractCurrecy) {
		ContractCurrecy = contractCurrecy;
	}

	public String getSectionCount() {
		return SectionCount;
	}

	public void setSectionCount(String sectionCount) {
		SectionCount = sectionCount;
	}

	public SoaModel getSoaM() {
		return soaM;
	}

	public void setSoaM(SoaModel soaM) {
		this.soaM = soaM;
	}

	public Boolean getReviewedFlag() {
		return reviewedFlag;
	}

	public void setReviewedFlag(Boolean reviewedFlag) {
		this.reviewedFlag = reviewedFlag;
	}

	public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public String getTaskcreatorName() {
		return taskcreatorName;
	}

	public void setTaskcreatorName(String taskcreatorName) {
		this.taskcreatorName = taskcreatorName;
	}

	public String getTaskreleaserName() {
		return taskreleaserName;
	}

	public void setTaskreleaserName(String taskreleaserName) {
		this.taskreleaserName = taskreleaserName;
	}

	public String getTakswithdrawName() {
		return takswithdrawName;
	}

	public void setTakswithdrawName(String takswithdrawName) {
		this.takswithdrawName = takswithdrawName;
	}

	public String getStatementType() {
		return statementType;
	}

	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	
}
