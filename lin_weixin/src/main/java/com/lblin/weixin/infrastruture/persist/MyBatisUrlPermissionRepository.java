package com.lblin.weixin.infrastruture.persist;

import org.springframework.stereotype.Repository;

import com.lblin.weixin.domain.UrlPermission;
import com.lblin.weixin.domain.UrlPermissionRepository;
import com.lblin.weixin.infrastruture.orm.mybatis.MybatisRepositorySupport;
@Repository
public class MyBatisUrlPermissionRepository extends MybatisRepositorySupport<String, UrlPermission> 
											implements UrlPermissionRepository{


}
