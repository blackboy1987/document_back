package com.igomall.service.impl;

import com.igomall.dao.ResourceDao;
import com.igomall.entity.Resource;
import com.igomall.service.ResourceService;
import io.jsonwebtoken.lang.Assert;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource,Long> implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Resource findByName(String name) {
        return resourceDao.find("name",name);
    }

    @Override
    public long downloadHits(Long id) {
        Assert.notNull(id);

        Ehcache cache = cacheManager.getEhcache(Resource.DOWNLOAD_CACHE_NAME);
        cache.acquireWriteLockOnKey(id);
        try {
            Element element = cache.get(id);
            Long hits;
            if (element != null) {
                hits = (Long) element.getObjectValue() + 1;
            } else {
                Resource resource = resourceDao.find(id);
                if (resource == null) {
                    return 0L;
                }
                hits = resource.getDownloadHits() + 1;
            }
            cache.put(new Element(id, hits));
            return hits;
        } finally {
            cache.releaseWriteLockOnKey(id);
        }
    }
}
