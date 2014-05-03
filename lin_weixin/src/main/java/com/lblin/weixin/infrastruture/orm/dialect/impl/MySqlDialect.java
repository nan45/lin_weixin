package com.lblin.weixin.infrastruture.orm.dialect.impl;

import com.lblin.weixin.infrastruture.orm.dialect.Dialect;

public class MySqlDialect implements Dialect{

	public String getLimitString(String sql, int offset, int offsetSize) {
		StringBuilder builder = new StringBuilder(sql);
		return builder.append(" limit ")
					  .append(offset)
					  .append(",")
					  .append(offsetSize).toString();
	}

}
