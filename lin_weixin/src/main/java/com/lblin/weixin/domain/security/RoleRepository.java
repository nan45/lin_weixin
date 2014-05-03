package com.lblin.weixin.domain.security;

import java.util.List;

import com.lblin.weixin.infrastruture.orm.Page;


/**
 * 
 * @author linqy
 * 
 */
public interface RoleRepository {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Role get(String id);

	/**
	 * 
	 * 
	 * @return
	 */
	List<Role> query();

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Role> queryPage(Page<Role> page);

	/**
	 * 
	 * @param entity
	 */
	void update(Role entity);

	/**
	 * 
	 * @param entity
	 */
	void delete(Role entity);

	/**
	 * 
	 * @param entity
	 */
	void save(Role entity);
}
