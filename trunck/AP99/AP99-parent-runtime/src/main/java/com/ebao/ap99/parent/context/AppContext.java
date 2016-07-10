package com.ebao.ap99.parent.context;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ebao.unicorn.platform.foundation.context.AppUser;

public class AppContext extends com.ebao.unicorn.platform.foundation.context.AppContext {
    private static AppUser appUser;

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static AppUser getAppUser() {
        if (null == appUser) {
            appUser = new AppUser();
            appUser.setUserId(-11);
            appUser.setUserName("dumyUser");
        }
        return appUser;
    }

    public static Date getSysDate() {
        return new Date();
    }

    public static String getFormatDate() {
        return sdf.format(getSysDate());
    }
}
