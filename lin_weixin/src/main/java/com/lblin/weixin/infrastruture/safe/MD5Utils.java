package com.lblin.weixin.infrastruture.safe;

import org.apache.shiro.crypto.hash.Md5Hash;

public final class MD5Utils {

	/**
	 * 
	 * @param source
	 * @param salt
	 * @return
	 */
	public static String asMD5(Object source, Object salt) {
		return new Md5Hash(source, salt).toBase64();
	}

	private MD5Utils() {}
}
