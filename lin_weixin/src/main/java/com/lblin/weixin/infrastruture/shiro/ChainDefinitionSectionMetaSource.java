package com.lblin.weixin.infrastruture.shiro;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.lblin.weixin.domain.UrlPermission;
import com.lblin.weixin.domain.UrlPermissionRepository;

public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section>{
	@Autowired
	private UrlPermissionRepository urlPermissionRepository;
	private String filterChainDefinitions;

	public Section getObject() throws Exception {
		//��ȡ����Resource
        List<UrlPermission> list = urlPermissionRepository.query();

        Ini ini = new Ini();
        //����Ĭ�ϵ�url
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        //ѭ��Resource��url,�����ӵ�section�С�section����filterChainDefinitionMap,
        //����ļ���������URL,ֵ���Ǵ���ʲô�������ܷ��ʸ�����
        for (Iterator<UrlPermission> it = list.iterator(); it.hasNext();) {

        	UrlPermission resource = it.next();
            //�����Ϊ��ֵ��ӵ�section��
            if(StringUtils.isNotEmpty(resource.getUrl()) && StringUtils.isNotEmpty(resource.getPermission())) {
                section.put(resource.getUrl(), resource.getPermission());
            }

        }

        return section;
	}

	/**
     * ͨ��filterChainDefinitions��Ĭ�ϵ�url���˶���
     * 
     * @param filterChainDefinitions Ĭ�ϵ�url���˶���
     */
    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }
    
	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}

	
}
