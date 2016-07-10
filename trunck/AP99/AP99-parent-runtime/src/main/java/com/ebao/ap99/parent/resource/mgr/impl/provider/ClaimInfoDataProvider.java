package com.ebao.ap99.parent.resource.mgr.impl.provider;

import java.util.HashMap;
import java.util.Map;

import com.ebao.ap99.parent.resource.mgr.DataProvider;

public class ClaimInfoDataProvider implements DataProvider {
	@Override
	public Map<String, Object> buildDataContext(Object obj) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(KEY__CURRENT_MODEL, obj);
		context.put(obj.getClass().getSimpleName(), obj);
		return context;
	}

}
