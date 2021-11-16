package com.huajuan.killer.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

//封装本地缓存操作类
@Service
public class CacheService {

    private Cache<String, Object> commonCache = null;

    @PostConstruct
    public void init() {
        //设置缓存容器初始容量
        //最大容量
        //写缓存后过期时间
        commonCache = CacheBuilder.newBuilder()
                .initialCapacity(10)
        .maximumSize(100)
        .expireAfterWrite(60, TimeUnit.SECONDS)
        .build();
    }

    public void setCommonCache(String key, Object value) {
        commonCache.put(key, value);
    }

    public Object getFromCommonCache(String key) {
        return commonCache.getIfPresent(key);
    }
}
