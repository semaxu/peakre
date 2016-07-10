/**
 * 
 */
package com.ebao.ap99.parent.restful;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.parent.convertor.LockConvertor;
import com.ebao.ap99.parent.dao.BusinessTransTypeDao;
import com.ebao.ap99.parent.dao.TRiLockDao;
import com.ebao.ap99.parent.entity.TRiLock;
import com.ebao.ap99.parent.model.LockModel;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;

/**
 * @author yujie.zhang
 *
 */
@Module(com.ebao.ap99.parent.constant.Module.COMMON)
@Path("/LockInfo")
public class LockRestfulService {

	@Autowired
	private TRiLockDao tRiLockDao;

	@Autowired
	private LockConvertor lockConvertor;

	@POST
	@Path("CheckLock")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object checkLock(LockModel lockModel) {
		lockModel.setOpeartId(AppContext.getCurrentUser().getUserId());
		lockModel.setOpeartName(AppContext.getCurrentUser().getUserName());
		lockModel.setOwnerName(tRiLockDao.getUserInfo(lockModel.getOwnerId()).getUserName());

		List<TRiLock> lockinfoList = tRiLockDao.getLockInfoByResourceTypeAndId(lockModel);
		// return codeTableDS.searchByCode(query.getCode(),
		// query.getCodeTableId());
		if (lockinfoList != null && lockinfoList.size() > 0) {
			if (lockinfoList.get(0).getLockUserId() != null) {
				lockModel.setLockUserId(lockinfoList.get(0).getLockUserId());
				lockModel.setLockUserName(tRiLockDao.getUserInfo(lockinfoList.get(0).getLockUserId()).getUserName());
				// lockModel.setLockId(lockinfoList.get(0).getLockId());
			}

		}

		return lockModel;
	}

	@SuppressWarnings("deprecation")
	@POST
	@Path("Lock")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object lock(LockModel lockModel) {

		List<TRiLock> lockinfoList = tRiLockDao.getLockInfoByResourceTypeAndId(lockModel);
		if (lockinfoList != null && lockinfoList.size() > 0) {
			TRiLock lockEntity = lockinfoList.get(0);

			if (AppContext.getCurrentUser().getUserId() != lockEntity.getLockUserId()) {

				lockModel.setMessageType(1);
				lockModel.setMessage(tRiLockDao.getUserInfo(lockEntity.getLockUserId()).getUserName()
						+ " is running this task, you cannot edit it. ");

			} else {
				lockEntity.setLockTime(AppContext.getSysDate());
				lockEntity.setLockUserId(AppContext.getCurrentUser().getUserId());
				tRiLockDao.merge(lockEntity);
				lockModel.setMessageType(0);

			}
		} else {
			TRiLock lockEntity = lockConvertor.modelToEntity(lockModel);
			tRiLockDao.insert(lockEntity);
			lockModel.setMessageType(0);
		}

		// return codeTableDS.searchByCode(query.getCode(),
		// query.getCodeTableId());

		return lockModel;
	}

	@SuppressWarnings("deprecation")
	@POST
	@Path("Unlock")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object unlock(LockModel lockModel) {

		List<TRiLock> lockinfoList = tRiLockDao.getLockInfoByResourceTypeAndId(lockModel);

		if (lockinfoList != null && lockinfoList.size() > 0) {
			TRiLock lockEntity = lockinfoList.get(0);
			// lockEntity.setLockUserId(null);
			lockEntity.setUnlockTime(AppContext.getSysDate());
			 tRiLockDao.merge(lockEntity);

			 lockModel.setMessageType(0);
		}
		return lockModel;
	}
}
