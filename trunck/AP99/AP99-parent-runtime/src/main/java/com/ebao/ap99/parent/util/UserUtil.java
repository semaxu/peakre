package com.ebao.ap99.parent.util;

import com.ebao.unicorn.platform.foundation.context.AppContext;
import com.ebao.unicorn.platform.foundation.context.AppUser;

public class UserUtil {

	public static void setDummyUser() throws Exception{
		AppUser appUser = AppContext.getCurrentUser();
		if(appUser == null){
			appUser = new AppUser();
			appUser.setUserId(-99L);
			AppContext.setCurrentUser(appUser);
		}
	}
}
