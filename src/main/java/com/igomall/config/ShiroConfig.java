package com.igomall.config;

import com.igomall.security.AuthorizingRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);


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
}
