package com.lblin.weixin.infrastruture.persist.security;

import org.springframework.stereotype.Repository;

import com.lblin.weixin.domain.security.Authority;
import com.lblin.weixin.domain.security.AuthorityRepository;
import com.lblin.weixin.infrastruture.orm.mybatis.MybatisRepositorySupport;


/**
 * 
 * @author linqy
 * 
 */
@Repository
public class MybatisAuthorityRepository extends MybatisRepositorySupport<String, Authority> implements AuthorityRepository {}
