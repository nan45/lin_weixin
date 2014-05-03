package com.lblin.weixin.infrastruture.lang;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedList;
import java.util.List;

public class Ghost<T> {
	private Class<T> clazz;

	private Ghost(Class<T> clazz) {
		this.clazz = clazz;
	}

	public static <T> Ghost<? extends Object> me(T obj) {
		return me(obj.getClass());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Ghost<T> me(Class<T> clazz) {
		assert (clazz != null);
		return new Ghost(clazz);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public Class<?>[] genericsTypes(Class<?> superClazz) {
		Type type = getGenericSupertype(this.clazz,superClazz);

		List types = new LinkedList();
		if (type instanceof ParameterizedType) {
			Type[] paramTypes = ((ParameterizedType) type)
					.getActualTypeArguments();
			types.addAll(evalTypesAsClasses(paramTypes));
		} else if (type instanceof GenericArrayType) {
			Type paramType = ((GenericArrayType) type)
					.getGenericComponentType();
			types.add((Class) evalTypesAsClasses(new Type[] { paramType }).get(
					0));
		}

		return ((Class[]) types.toArray(new Class[types.size()]));
	}
	
	@SuppressWarnings("rawtypes")
	private Type getGenericSupertype(Class<?> raw, Class<?> toResolve) {
		if (toResolve.isInterface()) {
			Class[] interfaces = raw.getInterfaces();
			int i = 0;
			for (int length = interfaces.length; i < length; ++i) {
				if (interfaces[i] == toResolve)
					return this.clazz.getGenericInterfaces()[i];
				if (toResolve.isAssignableFrom(interfaces[i])) {
					return getGenericSupertype(interfaces[i], toResolve);
				}
			}

		}

		if (!(raw.isInterface())) {
			while (raw != Object.class) {
				Class rawSupertype = raw.getSuperclass();
				if (rawSupertype == toResolve)
					return raw.getGenericSuperclass();
				if (toResolve.isAssignableFrom(rawSupertype)) {
					return getGenericSupertype(rawSupertype, toResolve);
				}
				raw = rawSupertype;
			}

		}

		return toResolve;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Class<?>> evalTypesAsClasses(Type[] types) {
		List clazzes = new LinkedList();
		for (Type type : types) {
			if (type instanceof Class) {
				clazzes.add((Class) type);
			} else if (type instanceof ParameterizedType) {
				clazzes.add((Class) ((ParameterizedType) type).getRawType());
			} else if (type instanceof TypeVariable) {
				TypeVariable tv = (TypeVariable) type;
				clazzes.add((Class) tv.getBounds()[0]);
			}
		}
		return clazzes;
	}
}
