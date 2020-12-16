package com.offcn;

import com.offcn.user.UserStartApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserStartApplication.class)
public class ScwUserApplicationTest {

    Logger logger = LoggerFactory.getLogger(getClass());  //引入日志文件
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads(){
        redisTemplate.opsForValue().set( "stringRedis", "123" );
        stringRedisTemplate.opsForValue().set( "stringRedis", "123" );
    }
}
