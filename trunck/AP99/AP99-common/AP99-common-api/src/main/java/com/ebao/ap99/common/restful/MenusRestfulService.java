package com.ebao.ap99.common.restful;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.beanutils.BeanUtils;
import org.restlet.engine.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.common.dao.TRiMenusDao;
import com.ebao.ap99.common.entity.TRiMenus;
import com.ebao.ap99.common.module.MenusVO;
import com.ebao.unicorn.platform.foundation.context.AppContext;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.urpmgmt.service.UserService;

@Path("/menus")
@Module(Module.PUBLIC)
public class MenusRestfulService {
	@Autowired
	private TRiMenusDao menusDao;
	@Autowired
	private UserService userService;

	@GET
	@Path("/loadMenus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<MenusVO> loadContractInfo() throws Exception {
		List<MenusVO> menuList = new ArrayList<MenusVO>();
		List<TRiMenus> entities = menusDao.loadAllMenus();
		List<TRiMenus> tempEntites = new ArrayList<TRiMenus>();
		Long userId = AppContext.getCurrentUser().getUserId();
		//Remove no-permission menus
		for (TRiMenus entity : entities) {
			if (StringUtils.isNullOrEmpty(entity.getPermissionCode())
					|| userService.hasPermissions(userId, entity.getPermissionCode())) {
				tempEntites.add(entity);
			}
		}
		menuList = convertToMenusVO(tempEntites);
		return menuList;
	}
	/**
	 * convert the menus entities to UI menus model
	 * @param entities
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private List<MenusVO> convertToMenusVO(List<TRiMenus> entities) throws IllegalAccessException, InvocationTargetException{
		List<MenusVO> menuList = new ArrayList<MenusVO>();
		for (TRiMenus entity : entities) {
			MenusVO menusVO = new MenusVO();
			if (entity.getParentId() == null || entity.getParentId() == 0l) {
				BeanUtils.copyProperties(menusVO, entity);
				for (TRiMenus child : entities) {
					if (child.getParentId() != null && child.getParentId() == entity.getMenuId()) {
						MenusVO childMenu = new MenusVO();
						BeanUtils.copyProperties(childMenu, child);
						menusVO.getChildren().add(childMenu);
					}
				}
			}
			if (null != menusVO.getChildren() && menusVO.getChildren().size() > 0) {
				menuList.add(menusVO);
			}
		}
		return menuList;
	}
}
