package com.example.businessintelligencebackend.service;

import com.example.businessintelligencebackend.repository.CacheRepository;
import com.example.businessintelligencebackend.entity.CacheEntity.SingleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CacheService {
    @Autowired
    private CacheRepository cacheRepository;
    public SingleResult findOne(String id){
        return cacheRepository.findById(id).get();
    }

    public SingleResult save(SingleResult result){
        return cacheRepository.save(result);
    }

    public List<SingleResult> queryAll(){
        return cacheRepository.findAll();
    }
}
