package com.lblin.weixin.domain;

import java.io.Serializable;

public class UrlPermission implements Serializable {

	private static final long serialVersionUID = 7932526805480139388L;

	private String id;
	private String url;
	private String permission;
	public String getId() {
		return id;
	}
	public UrlPermission setId(String id) {
		this.id = id;
		return this;
	}
	public String getUrl() {
		return url;
	}
	public UrlPermission setUrl(String url) {
		this.url = url;
		return this;
	}
	public String getPermission() {
		return permission;
	}
	public UrlPermission setPermission(String permission) {
		this.permission = permission;
		return this;
	}
	
}
