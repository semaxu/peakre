/**
 * 
 */
package com.ebao.ap99.file.dao;

import org.apache.commons.lang.StringUtils;

import com.ebao.ap99.file.model.Document;

public class QuerySqlDocument {

    public String getQueryDocumentSql(Document document) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select t1.*, t2.process_type from t_ri_cm_document t1, t_ri_cm_document_rule t2 where status=1 AND t1.business_type = t2.business_type ");

        if (document.getBusinessId() > 0L) {
            sql.append(" and t1.business_id='").append(document.getBusinessId()).append("'");
        }
        if (StringUtils.isNotEmpty(document.getBusinessType())) {
            sql.append(" and t1.business_type='").append(document.getBusinessType()).append("'");
        }
        if (document.getDocumentId() > 0L) {
            sql.append(" and t1.document_id='").append(document.getDocumentId()).append("'");
        }

        sql.append(" order by t1.insert_time desc ");
        return sql.toString();

    }

    public String getDocumentRecordSql(long documentId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from t_ri_cm_document_record t1 where 1=1 ");
        if (documentId > 0L) {
            sql.append(" and t1.document_id=").append(documentId);
        }

        return sql.toString();

    }
    
    public String getDocumentFieldSql(String businessType) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from t_ri_cm_document_field t1 where 1=1 ");
        sql.append(" and t1.business_type='").append(businessType).append("' order by document_field_id");
        return sql.toString();

    }
}
