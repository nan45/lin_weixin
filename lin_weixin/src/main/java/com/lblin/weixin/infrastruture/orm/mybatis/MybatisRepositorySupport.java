package com.lblin.weixin.infrastruture.orm.mybatis;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.lblin.weixin.infrastruture.lang.Ghost;
import com.lblin.weixin.infrastruture.orm.Page;

public class MybatisRepositorySupport<ID,T> extends SqlSessionDaoSupport{


	@Autowired
	public void setSqlSession(SqlSessionTemplate sqlSessionTemplate) {
	    super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	private final Logger logger = LoggerFactory.getLogger(super.getClass());
	private final Class<ID> idClazz;
	private final Class<T> entityClazz;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected MybatisRepositorySupport() {
		Class[] genericsTypes = Ghost.me(super.getClass()).genericsTypes(
				MybatisRepositorySupport.class);
		this.idClazz = genericsTypes[0];
		this.entityClazz = genericsTypes[1];
	}

	public T get(ID id) {
		T obj  = null;
		try {
			obj =  getSqlSession().selectOne(getNamespace() + ".get", id);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return obj;
		
	}

	public void save(T entity) {
		try {
			getSqlSession().insert(getNamespace() + ".save", entity);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

	public void update(T entity) {
		try {
			getSqlSession().update(getNamespace() + ".update", entity);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

	public void delete(T entity) {
		try {
			getSqlSession().delete(getNamespace() + ".delete", entity);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

	public List<T> query() {
		List<T> list = Lists.newArrayList();
		try {
			list = getSqlSession().selectList(getNamespace() + ".query");
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<T> queryPage(Page<T> page) {
		try {
			List result = getSqlSession().selectList(
					getNamespace() + ".queryPage", page);
			page.setResult(result);
			return page;
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return page;
	}

	protected Logger getLogger() {
		return this.logger;
	}

	public Class<ID> getIdClazz() {
		return this.idClazz;
	}

	public Class<T> getEntityClazz() {
		return this.entityClazz;
	}

	protected String getNamespace() {
		return this.entityClazz.getName();
	}
	
	
}
