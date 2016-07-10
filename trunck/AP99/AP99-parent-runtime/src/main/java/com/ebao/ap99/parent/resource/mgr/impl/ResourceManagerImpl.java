package com.ebao.ap99.parent.resource.mgr.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.digester3.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.parent.resource.mgr.DataProvider;
import com.ebao.ap99.parent.resource.mgr.ResourceManager;
import com.ebao.config.core.ModuleConfigManager;
import com.ebao.config.core.ResourceHolder;
import com.ebao.config.core.supports.PropertiesCollector;
import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;
import com.ebao.unicorn.platform.resource.mgr.RoutingManager;
import com.ebao.unicorn.platform.resource.model.Resource;
import com.ebao.unicorn.platform.resource.model.ResourceResult;
import com.ebao.unicorn.platform.resource.model.ResourceSet;
import com.ebao.unicorn.platform.resource.model.Resources;

public class ResourceManagerImpl implements ResourceManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceManagerImpl.class);
	private static final String CONFIG_SECTION__RESOURCE = "resource";
//	private static final String CONFIG_SECTION__DATA_PROVIDER = "dataProvider";
	private Map<String, DataProvider> dataProviders;
	private Map<String, ResourceSet> allResource = new HashMap<String, ResourceSet>();
	@Autowired
	private RoutingManager routingManager;

	public void init() {
		loadAllResource();
		// initDataProvider();
	}

	private void loadAllResource() {
		LOGGER.info(">>>>>>>>>>>>>Load all resources begin");
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("resources", Resources.class);
		digester.addObjectCreate("resources/resource", Resource.class);
		digester.addSetProperties("resources/resource", "name", "name");
		digester.addSetProperties("resources/resource", "type", "type");
		digester.addSetProperties("resources/resource", "tag", "tag");
		digester.addSetProperties("resources/resource", "dataContext", "dataContext");
		digester.addSetProperties("resources/resource", "description", "description");

		digester.addSetNext("resources/resource", "addResource");

		List<ResourceHolder> configFiles = ModuleConfigManager.getInstance().getConfigFiles(CONFIG_SECTION__RESOURCE);
		InputStream fis = null;
		if (configFiles != null) {
			for (ResourceHolder re : configFiles) {
				try {
					fis = re.getResource().getInputStream();
					Resources resources = (Resources) digester.parse(fis);
					for (Resource r : resources.getResourceList()) {
						addResource(r);
					}
				} catch (Exception e) {
					LOGGER.error("Parse " + re.getResource().getFilename() + " error:" + e.getMessage()
							+ " This file will be ignore.", e);
				}
			}
		}
		LOGGER.debug(">>>>>>>>>>>>>Load all resources end");
	}

	private void initDataProvider() {
		dataProviders = new HashMap<String, DataProvider>();
		PropertiesCollector collector = new PropertiesCollector("custDataProvider");
		Properties prop=collector.populateResult();
		for (Object entry:prop.keySet()) {
			String dataContext = (String) entry;
			String dataProviderClassName =prop.getProperty(dataContext) ;
			DataProvider dataProvider;
			try {
				dataProvider = (DataProvider) SpringContextUtils.getBean(Class.forName(dataProviderClassName));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			dataProviders.put(dataContext, dataProvider);
		}
	}

	private void addResource(Resource resource) {
		String type = resource.getType();
		ResourceSet resourceSet = allResource.get(type);
		if (resourceSet == null) {
			resourceSet = new ResourceSet(type);
			allResource.put(type, resourceSet);
		}
		resourceSet.addResource(resource);
	}

	@Override
	public Resource getResource(String type, String name) {
		return allResource.get(type).getResource(name);
	}

	@Override
	public ResourceResult getResourceResult(String type, String name, Object obj) {
		if (dataProviders == null) {
			initDataProvider();
		}
		Resource resource = getResource(type, name);
		String dataContext = resource.getDataContext();
		String[] dataContexts = dataContext.split(",");
		Map<String, Object> context = new HashMap<String, Object>();
		if (dataContexts != null && dataContexts.length > 0) {
			DataProvider dataProvider = dataProviders.get(dataContext);
			Map<String, Object> buildContext = dataProvider.buildDataContext(obj);
			context.putAll(buildContext);
		}

		String value = routingManager.getValue(type, resource.getName(), context);
		ResourceResult resourceResult = new ResourceResult(value, context);
		return resourceResult;
	}

	@Override
	public ResourceResult getResourceResultWithContext(String type, String name, Map<String, Object> context) {
		Resource resource = getResource(type, name);
		String value = routingManager.getValue(type, resource.getName(), context);
		ResourceResult resourceResult = new ResourceResult(value, context);
		return resourceResult;
	}

	@Override
	public ResourceSet getResourceSet(String type) {
		return allResource.get(type);
	}
}
