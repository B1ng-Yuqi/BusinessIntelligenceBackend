package com.example.businessintelligencebackend.service;

import com.example.businessintelligencebackend.dao.CacheRepository;
import com.example.businessintelligencebackend.entity.CacheEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CacheService {
    @Autowired
    private CacheRepository cacheRepository;
    public User findOne(Long id){
        return cacheRepository.findById(id).get();
    }

    public User save(User user){
        return cacheRepository.save(user);
    }

    public List<User> queryAll(){
        return cacheRepository.findAll();
    }
}
