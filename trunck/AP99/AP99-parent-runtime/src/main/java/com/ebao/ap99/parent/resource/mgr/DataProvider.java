package com.ebao.ap99.parent.resource.mgr;

import java.util.Map;

public interface DataProvider {
	public Map<String, Object> buildDataContext(Object obj);
	
	public static final String KEY__CURRENT_MODEL = "currentModel";
	public static final String KEY__CURRENT_USER = "currentUser";
}
