package com.lblin.weixin.domain.security;

import com.lblin.weixin.domain.AbstractDomain;
import javax.validation.constraints.Size;
/**
 * 
 * @author linqy
 * 
 */
public class Authority extends AbstractDomain {

	private static final long serialVersionUID = 1L;

	@Size(min = 1, max = 32)
	private String name;
	@Size(min = 1, max = 64)
	private String permission;

	public String getName() {
		return name;
	}

	public Authority setName(String name) {
		this.name = name;
		return this;
	}

	public String getPermission() {
		return permission;
	}

	public Authority setPermission(String permission) {
		this.permission = permission;
		return this;
	}
}
