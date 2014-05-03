package com.lblin.weixin.domain;

public interface DomainObject<T> {
	public abstract boolean sameIdentityAs(T paramT);
}
