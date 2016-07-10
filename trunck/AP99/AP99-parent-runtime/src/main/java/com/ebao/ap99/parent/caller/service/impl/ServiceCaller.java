package com.ebao.ap99.parent.caller.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.ebao.ap99.parent.caller.service.IServiceCaller;
import com.ebao.ap99.parent.resource.mgr.ResourceManager;
import com.ebao.unicorn.platform.foundation.context.SpringContextUtils;
import com.ebao.unicorn.platform.foundation.exception.SystemError;
import com.ebao.unicorn.platform.log.mdc.IMDCRuntimeBuilder;
import com.ebao.unicorn.platform.resource.model.ResourceConstants;
import com.ebao.unicorn.platform.resource.model.ResourceResult;
import com.ebao.unicorn.platform.service.caller.impl.ParameterPreparer;
import com.ebao.unicorn.platform.service.caller.impl.XStreamWriter;
import com.ebao.unicorn.platform.service.exception.OrchestrationExceptionConstant;
import com.ebao.unicorn.platform.service.parser.CamelConfigParser;

public class ServiceCaller implements IServiceCaller {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCaller.class);
	@Autowired
	private ResourceManager resourceManager;
	private ModelCamelContext camelContext;

	@Autowired(required = false)
	private IMDCRuntimeBuilder mdcBuilder;

	private boolean checkParameter = false;
	private boolean printXml = false;
	private ParameterPreparer parameterPreparer;
	private XStreamWriter xStreamWriter;

	public Object doAction(String name, Object obj) throws Exception {
		LOGGER.debug("ServiceCaller.doAction({},\n {} )", name, obj);
		ResourceResult resourceResult = resourceManager.getResourceResult(ResourceConstants.RESOURCE_TYPE_ACTION, name,
				obj);
		String from = resourceResult.getValue();
		Map<String, Object> context = resourceResult.getContext();

		if (this.mdcBuilder != null) {
			this.mdcBuilder.buildMDC(context);
		}

		// check fromUri
		// TODO remove parameter check
		this.checkParameter(context, from);
		LOGGER.debug("ServiceCaller.doAction parameter check pass!");

//		if (printXml) {
//			xStreamWriter.writeXStream(name, context);
//		}

		DefaultExchange e = this.buildExchange(context);
		Exchange ret = null;
		final String fFrom = from;
		final DefaultExchange fE = e;
		TransactionTemplate transactionTemplate = new TransactionTemplate(SpringContextUtils.getTransactionManager());
		try {
			ret = transactionTemplate.execute(new TransactionCallback<Exchange>() {
				public Exchange doInTransaction(TransactionStatus status) {
					Exchange ret = camelContext.createProducerTemplate().send(fFrom, fE);
					Exception exception = ret.getException();
					if (exception != null) {
						throw new RuntimeException(exception.getMessage(), exception);
					}
					return ret;
				}
			});
		} catch (Exception ex) {
			LOGGER.error("exception happened in from {}", from, ex);
			throw ex;
		}
		return ret.getIn().getBody();// return value has not processed
	}

	private DefaultExchange buildExchange(Map<String, Object> context) {
		DefaultExchange e = new DefaultExchange(camelContext);
		// clone context for camel, because camel will add sth. to context
		Map<String, Object> context4Camel = new HashMap<String, Object>();
		context4Camel.putAll(context);
		removeNullValue(context4Camel);// split component will copy this map and
										// NULL value will cause an
		e.setProperties(context4Camel);
		return e;
	}

	private void checkParameter(Map<String, Object> context, String from) throws Exception {
		if (checkParameter && !parameterPreparer.checkParameterValue(from, context)) {
			RouteDefinition route = CamelConfigParser.getRoute(camelContext, from);
			if (route == null) {
				LOGGER.error("invalid fromUri! fromUri = {}", from);
				throw new SystemError(OrchestrationExceptionConstant.INVALID_FROM_URI, "invalid fromUri! fromUri = {}",
						from);
			} else {
				// check parameter failure. there are not enough information in
				// context
				LOGGER.error("parameter did not has enuough values!");
				throw new SystemError(OrchestrationExceptionConstant.PARAMETER_VALUE_NOT_FOUND,
						"parameter value not found!");
			}
		}
	}

	// private Set<String> getAllToUris() {
	// Set<String> uris = new HashSet<String>();
	// List<RouteDefinition> routeDefList = camelContext.getRouteDefinitions();
	// for (RouteDefinition routeDef : routeDefList) {
	// List<ProcessorDefinition<?>> outputs = routeDef.getOutputs();
	// for (ProcessorDefinition<?> output : outputs) {
	// if (output instanceof ToDefinition) {
	// ToDefinition to = (ToDefinition) output;
	// String uri = to.getUri();
	// if (uri.startsWith(ICamelConstants.BEAN_START)) {
	// // beanString will be processed, resourceString will not
	// // be included
	// // rule module will get resource type bean from
	// // resourceManager
	// uris.add(uri);
	// }
	// }
	// }
	// }
	// return uris;
	// }

	private void removeNullValue(Map<String, Object> map) {
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			if (entry.getValue() == null) {
				it.remove();
			}
		}
	}

	public void setCamelContext(ModelCamelContext camelContext) {
		this.camelContext = camelContext;
	}

	public void setCheckParameter(boolean checkParameter) {
		this.checkParameter = checkParameter;
	}

	public void setPrintXml(boolean printXml) {
		this.printXml = printXml;
	}

	public void setParameterPreparer(ParameterPreparer parameterPreparer) {
		this.parameterPreparer = parameterPreparer;
	}

	public void setxStreamWriter(XStreamWriter xStreamWriter) {
		this.xStreamWriter = xStreamWriter;
	}

	public void setMdcBuilder(IMDCRuntimeBuilder mdcBuilder) {
		this.mdcBuilder = mdcBuilder;
	}
}
