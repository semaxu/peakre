/**
 * 
 */
package com.ebao.ap99.claim.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.entity.TRiClaimInfo;
import com.ebao.ap99.claim.entity.TRiClaimReserve;
import com.ebao.unicorn.platform.test.AbstractTest;

/**
 * @author gang.wang
 *
 */
public class RiClaimInfoDaoTest extends AbstractTest {

	@Autowired
	private RiClaimInfoDao claimInfoDao;

	// @PersistenceContext
	// private EntityManager entityManager;

	@Test
	// @Transactional(propagation = Propagation.REQUIRED)
	public void testInsert() {
		// EntityTransaction trans = entityManager.getTransaction();

		// try {
		// trans.begin();
		TRiClaimInfo entity = new TRiClaimInfo();
		entity.setCauseOfLoss("fire");
		entity.setDateOfLossFrom(new Date());
		entity.setDateOfLossTo(entity.getDateOfLossFrom());
		entity.setDateOfReport(new Date());
		entity.setRemark("AAAAAAAAA");
		TRiClaimReserve reserve1 = new TRiClaimReserve();
		//reserve1.setSectionId(BigDecimal.valueOf(1));
		reserve1.setReserveType("1");
		reserve1.setAmountOc(BigDecimal.valueOf(1000));
		reserve1.setAmountUsd(BigDecimal.valueOf(150));
		reserve1.setExchangeRate(BigDecimal.valueOf(1000 / 1500));
		reserve1.setPostingFlag("1");
		entity.addTRiClaimReserve(reserve1);
		TRiClaimReserve reserve2 = new TRiClaimReserve();
		//reserve2.setSectionId(BigDecimal.valueOf(2));
		reserve2.setReserveType("1");
		reserve2.setAmountOc(BigDecimal.valueOf(1000));
		reserve2.setAmountUsd(BigDecimal.valueOf(150));
		reserve2.setExchangeRate(BigDecimal.valueOf(1000 / 1500));
		reserve2.setPostingFlag("1");
		entity.addTRiClaimReserve(reserve2);

		claimInfoDao.insert(entity);
		// trans.commit();
		Assert.assertNotNull(entity.getClaimId());
		// } catch (Exception e) {
		// trans.rollback();
		// }

	}

}
