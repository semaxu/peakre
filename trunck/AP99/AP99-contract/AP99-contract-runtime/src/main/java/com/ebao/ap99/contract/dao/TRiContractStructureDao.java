package com.ebao.ap99.contract.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.contract.entity.TRiContractInfo;
import com.ebao.ap99.contract.entity.TRiContractStructure;
import com.ebao.ap99.contract.util.ContractCst;
import com.ebao.ap99.partner.service.PartnerAPI;
import com.ebao.unicorn.platform.foundation.dao.BaseDao;

public class TRiContractStructureDao extends BaseDao<TRiContractStructure> {
	// @PersistenceContext
	// private EntityManager em;

	@Autowired
	private PartnerAPI partnerService;

	@Override
	public Class<TRiContractStructure> getEntityClass() {
		final Query query = getEntityManager().createNamedQuery("TRiContractStructure.findByParentId",
				TRiContractStructure.class);
		return TRiContractStructure.class;
	}

	public List<Long> getChildrenIdList(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContractStructure.findChildrenIdByParentId",
				Long.class);
		query.setParameter("contCompId", contCompId);
		List<Long> list = query.getResultList();
		return list;
	}

	public int getChildrenCount(Long contCompId) {
		final Query query = getEntityManager().createNamedQuery("TRiContractStructure.getChildrenCount", Long.class);
		query.setParameter("contCompId", contCompId);
		return ((Long) query.getSingleResult()).intValue();
	}

	public boolean checkIsLeafComponent(Long contCompId) {
		int count = this.getChildrenCount(contCompId);
		return count > 0;
	}

	public List<TRiContractStructure> getChildList(Long parentId) {
		final Query query = getEntityManager().createNamedQuery("TRiContractStructure.findByParentId",
				TRiContractStructure.class);
		query.setParameter("parentId", parentId);
		List<TRiContractStructure> list = query.getResultList();
		return list;
	}

	public TRiContractStructure save(TRiContractStructure infoVO) {
		if (infoVO.getContCompId() != null) {
			TRiContractStructure existingOne = this.load(infoVO.getContCompId());
			infoVO.syncDataTo(existingOne, false);
			return existingOne;
		} else {
			this.getEntityManager().persist(infoVO);
			return infoVO;
		}
	}

	public TRiContractStructure generateStructureDao(Object obj) {
		TRiContractStructure structure = new TRiContractStructure();
		String classStr = obj.getClass().getSimpleName();
		if (classStr.contains("TRiContractInfo")) {
			TRiContractInfo info = (TRiContractInfo) obj;
			structure.setContCompId(info.getContCompId());
			structure.setName(info.getContractCode());
			structure.setType(ContractCst.CONTRACT_LEVEL);
			structure.setFullName(info.getContractCode() + ">" + info.getUwYear() + ">"
					+ partnerService.loadPartnerNameByPartnerCode(info.getCedent()) + ">"
					+ partnerService.loadPartnerNameByPartnerCode(info.getBroker()));
		} else {

		}
		return structure;
	}
}
