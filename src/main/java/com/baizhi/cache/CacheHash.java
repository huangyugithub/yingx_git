package com.baizhi.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Configuration
@Aspect
public class CacheHash {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    //添加缓存
    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object addCache(ProceedingJoinPoint joinPoint) throws Throwable {
        //序列化乱码
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        System.out.println("环绕通知");
        //key 唯一  类的全限定名 + 方法名 + 实参
        //value 查询方法执行后的结果
        StringBuilder stringBuilder = new StringBuilder();

        //获取类的权限定名
        String className = joinPoint.getTarget().getClass().getName();

        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        stringBuilder.append(methodName);

        //获取参数
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            stringBuilder.append(arg);
        }
        //获取小key  将 方法名+ 实参 作为小key
        String key = stringBuilder.toString();
        //大key  根据大key：类的全限定名  和 小key 判断是否存在
        Boolean aBoolean = redisTemplate.opsForHash().hasKey(className, key);
        //判断 如果redis中key存在，则取出他的值返回； 如果不存在，则将key value存到redis中
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object result = null;
        if (aBoolean) {
            result = hashOperations.get(className, key);
        } else {
            result = joinPoint.proceed();
            //加入缓存
            hashOperations.put(className, key, result);
        }
        return result;
    }

    //清空缓存
    @After("@annotation(com.baizhi.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint) {
        //区分各个模块的key值
        //获取类的全限定名
        String className = joinPoint.getTarget().getClass().getName();
        //获取所有的key
        redisTemplate.delete(className);
    }
}
