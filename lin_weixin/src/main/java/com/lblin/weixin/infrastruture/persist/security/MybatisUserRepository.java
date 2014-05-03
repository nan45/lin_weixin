package com.lblin.weixin.infrastruture.persist.security;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lblin.weixin.domain.security.User;
import com.lblin.weixin.domain.security.UserRepository;
import com.lblin.weixin.infrastruture.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author linqy
 * 
 */
@Repository
public class MybatisUserRepository extends MybatisRepositorySupport<String, User> implements UserRepository {

	private final Cache<String, User> cacheOnUsername = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).maximumSize(16).build();

	public User lazyGet(String id) {
		return (User) getSqlSession().selectOne(getNamespace().concat(".lazyGet"), id);
	}

	public User queryUniqueByUsername(final String username) {
		try {

			return cacheOnUsername.get(username, new Callable<User>() {

				public User call() throws Exception {
					return (User) getSqlSession().selectOne(getNamespace().concat(".queryUniqueByUsername"), username);
				}

			});
		} catch (Exception ignore) {
			getLogger().error("some error occur when access to the cacher", ignore);
			return null;
		}
	}

	public void markLocked(String[] ids) {
		if (isAvaliableIds(ids)) {
			evictCache(ids);
			getSqlSession().update(getNamespace().concat(".markLocked"), ids);
		}
	}

	public void markNotLocked(String[] ids) {
		if (isAvaliableIds(ids)) {
			evictCache(ids);
			getSqlSession().update(getNamespace().concat(".markNotLocked"), ids);
		}
	}

	public void evictCache(String[] ids) {
		List<String> names = getSqlSession().selectList(getNamespace().concat(".idsAsNames"), ids);
		for (String name : names) {
			cacheOnUsername.invalidate(name);
		}
	}

	private boolean isAvaliableIds(String[] ids) {
		return null != ids && ids.length > 0;
	}

	@Override
	public void save(User entity) {
		super.save(entity);
		if (entity.hasRole()) {
			getSqlSession().insert(getNamespace().concat(".saveRoles"), entity);
		}
	}

	@Override
	public void update(User entity) {
		cacheOnUsername.invalidate(entity.getUsername());
		super.update(entity);
		getSqlSession().delete(getNamespace().concat(".deleteRoles"), entity);
		if (entity.hasRole()) {
			getSqlSession().insert(getNamespace().concat(".saveRoles"), entity);
		}

	}

	@Override
	public void delete(User entity) {
		cacheOnUsername.invalidate(entity.getUsername());
		super.delete(entity);
	}

}
