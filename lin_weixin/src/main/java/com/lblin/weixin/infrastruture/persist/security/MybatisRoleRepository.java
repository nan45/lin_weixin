package com.lblin.weixin.infrastruture.persist.security;

import org.springframework.stereotype.Repository;

import com.lblin.weixin.domain.security.Role;
import com.lblin.weixin.domain.security.RoleRepository;
import com.lblin.weixin.infrastruture.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author linqy
 * 
 */
@Repository
public class MybatisRoleRepository extends MybatisRepositorySupport<String, Role> implements RoleRepository {

	
	@Override
	public void save(Role entity) {
		super.save(entity);
		if (entity.hasAuthority()) {
			getSqlSession().insert(getNamespace().concat(".saveAuthorities"), entity);
		}
	}

	@Override
	public void update(Role entity) {
		super.update(entity);
		getSqlSession().delete(getNamespace().concat(".deleteAuthorities"), entity);
		if (entity.hasAuthority()) {
			getSqlSession().insert(getNamespace().concat(".saveAuthorities"), entity);
		}
		
		getLogger().warn("must evict the user cache when update the role");
	}
}
