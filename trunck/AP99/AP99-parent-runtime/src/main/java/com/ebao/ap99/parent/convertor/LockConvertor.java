/**
 * 
 */
package com.ebao.ap99.parent.convertor;

import com.ebao.ap99.parent.context.AppContext;
import com.ebao.ap99.parent.entity.TRiLock;
import com.ebao.ap99.parent.model.LockModel;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

/**
 * @author yujie.zhang
 *
 */
public class LockConvertor {

	public TRiLock modelToEntity(LockModel lockModel) {

		TRiLock lockEntity = new TRiLock();

		lockEntity.setResourceId(lockModel.getResourceId());
		lockEntity.setResourceType(lockModel.getResourceType());
		lockEntity.setLockUserId(AppContext.getCurrentUser().getUserId());
		lockEntity.setLockTime(AppContext.getSysDate());
		
		return lockEntity;
	}
}
