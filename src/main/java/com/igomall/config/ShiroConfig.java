package com.igomall.config;

import com.igomall.entity.Admin;
import com.igomall.security.AuthenticationFilter;
import com.igomall.security.AuthorizingRealm;
import com.igomall.security.LogoutFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager,@Qualifier("adminAuthc") AuthenticationFilter adminAuthc){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setUnauthorizedUrl("/common/error/unauthorized");
        shiroFilterFactoryBean.setLoginUrl("/common/error/unauthorized");
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/admin","anon");
        filterChainDefinitionMap.put("/admin/","anon");
        filterChainDefinitionMap.put("/admin/logout","logout");
        //filterChainDefinitionMap.put("/admin/menu/list","adminAuthc,perms[admin:menu:list]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("adminAuthc",adminAuthc);
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("authorizingRealm") AuthorizingRealm authorizingRealm,@Qualifier("shiroCacheManager") EhCacheManager shiroCacheManager){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(authorizingRealm);
        defaultWebSecurityManager.setCacheManager(shiroCacheManager);

        return defaultWebSecurityManager;
    }

    @Bean
    public AuthorizingRealm authorizingRealm(){
        AuthorizingRealm authorizingRealm = new AuthorizingRealm();
        authorizingRealm.setAuthenticationCacheName("authorization");
        return authorizingRealm;
    }

    @Bean
    public EhCacheManager shiroCacheManager(@Qualifier("ehCacheManager") EhCacheManagerFactoryBean ehCacheManager){
        EhCacheManager shiroCacheManager = new EhCacheManager();
        shiroCacheManager.setCacheManager(ehCacheManager.getObject());
        return shiroCacheManager;
    }


    @Bean
    public AuthenticationFilter adminAuthc(){
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setUserClass(Admin.class);
        authenticationFilter.setLoginUrl("/admin/login");
        authenticationFilter.setSuccessUrl("/admin/index");
        return authenticationFilter;
    }

    @Bean
    public LogoutFilter logout(){
       return new LogoutFilter();
    }
}
