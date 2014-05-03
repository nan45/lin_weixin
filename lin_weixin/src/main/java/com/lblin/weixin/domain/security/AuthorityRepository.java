package com.lblin.weixin.domain.security;

import java.util.List;

import com.lblin.weixin.infrastruture.orm.Page;


/**
 * 
 * @author linqy
 *
 */
public interface AuthorityRepository {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Authority get(String id);

	/**
	 * 
	 *
	 * @return
	 */
	List<Authority> query();

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Authority> queryPage(Page<Authority> page);

	/**
	 * 
	 * @param entity
	 */
	void update(Authority entity);

	/**
	 * 
	 * @param entity
	 */
	void delete(Authority entity);

	/**
	 * 
	 * @param entity
	 */
	void save(Authority entity);
}
