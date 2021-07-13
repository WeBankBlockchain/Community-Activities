package cn.edu.scut.medicalresourceflow.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 知日
 * @version 1.0
 * @date 2021/6/8 14:48
 */
@Component
public final class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public boolean expire(String key, long time){
        try {
            if (time > 0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public void del(String... key){
        if (key !=null && key.length > 0){
            redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
        }
    }

    public Object get(String key){
        return key == null?null:redisTemplate.opsForValue().get(key);
    }

    public boolean set(String key, Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(String key, Object value, long time){
        try {
            if (time>0){
                redisTemplate.opsForValue().set(key, value, time,TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public long inc(String key, long delta){
        if (delta < 0){
            throw new RuntimeException("Delta must be greater or equal to 0");
        }
        return redisTemplate.opsForValue().increment(key,delta);
    }

    public long dec(String key,long delta){
        if (delta<0){
            throw new RuntimeException("Delta must be greater or equal to 0");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    public Object hget(String key, String field){
        return redisTemplate.opsForHash().get(key,field);
    }

    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public boolean hset(String key,Map<String,Object> mp){
        try {
            redisTemplate.opsForHash().putAll(key,mp);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean hmset(String key, Map<String, Object> mp, long time){
        try{
            hset(key,mp);
            if (time > 0){
                expire(key,time);
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean hset(String key, String field, Object value){
        try {
            redisTemplate.opsForHash().put(key,field,value);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean hset(String key, String field, Object value, long time){
        try {
            hset(key,field,value);
            if (time > 0){
                expire(key,time);
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean hHasKey(String key, String field){
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    public long hinc(String key, String field, long delta){
        if (delta < 0){
            throw new RuntimeException("Delta must be greater or equal to 0");
        }
        return redisTemplate.opsForHash().increment(key,field,delta);
    }

    public long hdec(String key, String field, long delta){
        if (delta < 0){
            throw new RuntimeException("Delta must be greater or equal to 0");
        }
        return redisTemplate.opsForHash().increment(key,field,-delta);
    }

    public long hdel(String key, String field){
        return redisTemplate.opsForHash().delete(key,field);
    }

    public long hrmv(String key){
        return redisTemplate.opsForHash().delete(key);
    }

    public long sadd(String key,Object... values){
        try{
            return redisTemplate.opsForSet().add(key, values);
        }catch (Exception ex){
            ex.printStackTrace();;
            return 0;
        }
    }

    public Set<Object> sget(String key){
        return redisTemplate.opsForSet().members(key);
    }
}


