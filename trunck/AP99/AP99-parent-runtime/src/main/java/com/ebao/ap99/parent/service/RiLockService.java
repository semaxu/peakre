/**
 * 
 */
package com.ebao.ap99.parent.service;

import com.ebao.ap99.parent.entity.TRiLock;

/**
 * @author yujie.zhang
 *
 */
public interface RiLockService {

	void createLockInfo(TRiLock tRiLock) throws Exception;

	TRiLock updateLockInfo(TRiLock tRiLock);

}
