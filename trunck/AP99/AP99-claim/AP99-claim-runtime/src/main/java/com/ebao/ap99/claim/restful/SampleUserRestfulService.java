/**
 * 
 */
package com.ebao.ap99.claim.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.claim.entity.TSampleUser;
import com.ebao.ap99.claim.service.SampleUserService;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

/**
 * @author gang.wang
 *
 */
@Path("/sample")
@Module(Module.CLAIM)
public class SampleUserRestfulService {
	
	private static Logger logger = LoggerFactory.getLogger();
	
	@Autowired
	private SampleUserService sampleUserService;
	
	@POST
	@Path("/sampleUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TSampleUser createSampleUser(TSampleUser user){
		logger.info("SampleUserRestfulService.createSampleUser");
		logger.info("userName=>{}"+ user.getUserName());
		sampleUserService.createUser(user);
		return user;
	}

}
