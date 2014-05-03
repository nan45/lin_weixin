package com.lblin.weixin.domain;

import java.util.List;

/**
 * 
 * @author linqy
 *
 */
public interface UrlPermissionRepository {

	public UrlPermission get(String id);
	
	public void save(UrlPermission urlPermission);
	
	public void update(UrlPermission urlPermission);
	
	public void delete(UrlPermission urlPermission);
	
	public List<UrlPermission> query();

}
