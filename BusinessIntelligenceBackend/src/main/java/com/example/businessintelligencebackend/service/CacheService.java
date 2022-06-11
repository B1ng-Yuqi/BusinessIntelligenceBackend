package com.example.businessintelligencebackend.service;

import com.example.businessintelligencebackend.repository.CacheRepository;
import com.example.businessintelligencebackend.entity.CacheEntity.cacheResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CacheService {
    @Autowired
    private CacheRepository cacheRepository;
    public cacheResult findOne(String id){
        return cacheRepository.findById(id).get();
    }

    public cacheResult save(cacheResult result){
        return cacheRepository.save(result);
    }

    public List<cacheResult> queryAll(){
        return cacheRepository.findAll();
    }

    public Boolean existsById(String id){
        return cacheRepository.existsById(id);
    }
}
