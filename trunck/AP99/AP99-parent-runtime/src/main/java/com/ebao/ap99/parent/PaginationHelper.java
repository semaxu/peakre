/**
 * 
 */
package com.ebao.ap99.parent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;

/**
 * @author yujie.zhang
 *
 */
public class PaginationHelper<E> {

	/**
	 * 
	 * @param jt
	 * @param sql
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @param rowMapper
	 * @return
	 */
	public Page<E> fetchPage(final String sql, final Object args[], int pageIndex, int pageSize,
			BeanPropertyRowMapper<E> rowMapper) {

		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		List<Map<String, Object>> result = jt.queryForList(sql, args);

		// total count
		int rowCount = result.size();

		// page count
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}

		// create the page object
		Page<E> page = new Page<E>();
		page.setPageIndex(pageIndex);
		long pCount = (long) pageCount;
		page.setPageCount(pCount);

		// fetch a single page of results

		int startRow = (pageIndex - 1) * pageSize;
		page.setStart(startRow);
		page.setLimit(pageSize);
		page.setCountPerPage(pageSize);
		jt.query(sql, args, new ResultSetExtractor<Object>() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				final List<E> pageItems = page.getRows();
				int currentRow = 0;
				while (rs.next() && currentRow < startRow + pageSize) {
					if (currentRow >= startRow) {
						pageItems.add(rowMapper.mapRow(rs, currentRow));
					}
					currentRow++;
				}
				return page;
			}
		});

		return page;

	}

	/**
	 * 
	 * @param jt
	 * @param sql
	 * @param args
	 * @param pageIndex
	 * @param pageSize
	 * @param rowMapper
	 * @return
	 */
	public Page<E> pageHelper(final String sql, final Object args[], int pageIndex, int pageSize,
			BeanPropertyRowMapper<E> rowMapper) {

		JdbcTemplate jt = SpringContextUtils.getJdbcTemplate();
		int rowCount = jt.queryForObject("select count(1) from (" + sql + ")", args, Integer.class);

		// total count
		//int rowCount = result.size();

		// page count
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}

		// create the page object
		Page<E> page = new Page<E>();
		page.setPageIndex(pageIndex);
		long pCount = (long) pageCount;
		page.setPageCount(pCount);

		// fetch a single page of results

		int startRow = (pageIndex - 1) * pageSize;
		page.setStart(startRow);
		page.setLimit(pageSize);
		page.setCountPerPage(pageSize);
		jt.query(sql, args, new ResultSetExtractor<Object>() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				final List<E> pageItems = page.getRows();
				int currentRow = 0;
				while (rs.next() && currentRow < startRow + pageSize) {
					if (currentRow >= startRow) {
						pageItems.add(rowMapper.mapRow(rs, currentRow));
					}
					currentRow++;
				}
				return page;
			}
		});

		return page;

	}
	
}
