package com.igomall.util;

import com.igomall.wechat.entity.response.AccessToken;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheUtils {
    private static final CacheManager cacheManager = CacheManager.create();
    private static final Cache accessTokenCache = cacheManager.getCache("accessToken");

    public static AccessToken getCacheAccessToken(){
        if(accessTokenCache!=null){
            Element element = accessTokenCache.get("accessToken");
            if(element==null){
                return null;
            }else{
                Object result = element.getObjectValue();
                if(result==null){
                    return null;
                }
                return (AccessToken)result;
            }
        }else{
            return null;
        }
    }

    public static void setCacheAccessToken(AccessToken accessToken){
        if(accessTokenCache!=null&&accessToken!=null){
            accessTokenCache.put(new Element("accessToken",accessToken));
        }
    }

    public static void removeCacheVideo(){
        if(accessTokenCache!=null){
            accessTokenCache.remove("accessToken");
        }
    }
}
