package com.lblin.weixin.infrastruture.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;


public class ConvertHelper {

	@SuppressWarnings("unchecked")
	public static <T, PK, PV> void propertyToMap(Collection<T> beans,
			String keyPropertyName, String valuePropertyName, Map<PK, PV> target) {
		Preconditions.notNull(beans, "beans must not null");
		Preconditions.hasLength(keyPropertyName,
				"keyPropertyName must not blank");
		Preconditions.hasLength(valuePropertyName,
				"valuePropertyName must not blank");
		Preconditions.notNull(target, "target must not null");
		try {
			for (Object bean : beans) {
				PK pk = (PK)BeanUtils.getProperty(bean, keyPropertyName);
				PV pv = (PV)BeanUtils.getProperty(bean, valuePropertyName);
				target.put(pk, pv);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> String propertyToString(Collection<T> beans,
			String propertyName, String separator) {
		Preconditions.notNull(beans, "beans must not null");
		Preconditions.hasLength(propertyName, "propertyName must not blank");

		List target = new ArrayList();
		propertyToList(beans, propertyName, target);
		return StringUtils.join(target, separator);
	}

	@SuppressWarnings("unchecked")
	public static <T, PV> void propertyToList(Collection<T> beans,
			String propertyName, List<PV> target) {
		Preconditions.notNull(beans, "beans must not null");
		Preconditions.hasLength(propertyName, "propertyName must not blank");
		Preconditions.notNull(target, "target must not null");
		try {
			for (Object bean : beans)
				target.add((PV)BeanUtils.getProperty(bean, propertyName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
