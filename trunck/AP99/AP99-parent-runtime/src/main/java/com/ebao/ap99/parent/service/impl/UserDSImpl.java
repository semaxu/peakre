package com.ebao.ap99.parent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.parent.service.UserDS;
import com.ebao.unicorn.platform.foundation.security.entity.User;
import com.ebao.unicorn.platform.urpmgmt.service.UserService;

public class UserDSImpl implements UserDS{
	@Autowired
    private UserService userService;
	
	public String getUserNameByUserId(Long userId){
		if(null != userId && userId != 0L){
			User user = userService.load(userId);
			if(null != user){
				return user.getUsername();
			}
		}
		return null;
	}
}
