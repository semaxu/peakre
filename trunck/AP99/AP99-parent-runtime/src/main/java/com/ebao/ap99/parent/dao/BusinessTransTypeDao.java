package com.ebao.ap99.parent.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.ebao.ap99.parent.entity.BusinessTransType;
import com.ebao.ap99.parent.model.CountryModel;
import com.ebao.ap99.parent.model.TreeModel;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class BusinessTransTypeDao extends BaseDao<BusinessTransType> {

	@Override
	public Class<BusinessTransType> getEntityClass() {
		return BusinessTransType.class;
	}

	public List<TreeModel> getAllUser(String moduleType) {

		StringBuilder sql = new StringBuilder();
		sql.append("  select user_id as id , user_name as text from t_pub_user    ");

		List<TreeModel> userModel = this.getJdbcTemplate().query(sql.toString(), new Object[] {},
				new BeanPropertyRowMapper<TreeModel>(TreeModel.class));

		return userModel;
	}

	public List<TreeModel> getCountry() {

		StringBuilder sql = new StringBuilder();
		sql.append("  select a.country_id as countryid,a.country_name as countryname,");
		sql.append(" a.country_level as countrylevel,a.parent_id as parentId,a.if_use as ifuse ");
		sql.append(" from t_ri_country a  where a.if_use = '1'    ");

		List<CountryModel> countryModel = this.getJdbcTemplate().query(sql.toString(), new Object[] {},
				new BeanPropertyRowMapper<CountryModel>(CountryModel.class));
		List<TreeModel> countryList = new ArrayList<>();
		
		for(CountryModel c:countryModel){
			if(c.getCountryLevel().equals("1")){
				TreeModel tree= new TreeModel();
				tree.setId(c.getCountryId());
				tree.setText(c.getCountryName());
				List<TreeModel> secondLevel = new ArrayList<>();
				for (CountryModel se:countryModel){
					if(se.getCountryLevel().equals("2")&&c.getCountryId().equals(se.getParentId()) ){
						TreeModel secondTree= new TreeModel();
						secondTree.setId(se.getCountryId());
						secondTree.setText(se.getCountryName());
						List<TreeModel> thirdLevel = new ArrayList<>();
						for (CountryModel th:countryModel){
							if(th.getCountryLevel().equals("3")&&se.getCountryId().equals(th.getParentId()) ){
								TreeModel thTree= new TreeModel();
								thTree.setId(th.getCountryId());
								thTree.setText(th.getCountryName());
								thirdLevel.add(thTree);
							}
						}
						secondTree.setChildren(thirdLevel);
						secondLevel.add(secondTree);
					}
				}
				tree.setChildren(secondLevel);
				countryList.add(tree);
			}
		}
		
		return countryList;
	}
}
