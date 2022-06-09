package com.example.businessintelligencebackend.service;

import com.example.businessintelligencebackend.entity.CacheEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
class CacheServiceTest {
    @Autowired
    private CacheService cacheService = new CacheService();

    @Test
    void findOne() {
        CacheEntity.User user = cacheService.findOne(3L);
        System.out.println(user);
        assertEquals("ccc",user.getName());
    }

    @Test
    void queryAll() {
        List<CacheEntity.User> list = cacheService.queryAll();
        assertEquals(3,list.size());
    }
}