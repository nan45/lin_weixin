package com.lblin.weixin.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.lblin.weixin.infrastruture.lang.StringUtils;



public abstract class AbstractDomain implements DomainObject<AbstractDomain>,
Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean hasIdentity() {
		return (!(StringUtils.isBlank(getId())));
	}

	public boolean sameIdentityAs(AbstractDomain other) {
		if (other == null) {
			return false;
		}

		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
