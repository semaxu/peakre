package com.ebao.ap99.parent.resource.mgr;

import java.util.Map;

import com.ebao.unicorn.platform.resource.model.Resource;
import com.ebao.unicorn.platform.resource.model.ResourceResult;
import com.ebao.unicorn.platform.resource.model.ResourceSet;

public interface ResourceManager {
	public Resource getResource(String type, String name);
	
	public ResourceSet getResourceSet(String type);

	public ResourceResult getResourceResult(String type, String name, Object obj);

	public ResourceResult getResourceResultWithContext(String type, String name, Map<String, Object> context);
}
