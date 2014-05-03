package com.lblin.weixin.infrastruture.orm.dialect;

public interface Dialect {
	public String getLimitString(String sql, int offset, int offsetSize);
}
