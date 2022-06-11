package com.example.businessintelligencebackend.repository;

import com.example.businessintelligencebackend.entity.CacheEntity.cacheResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepository extends MongoRepository<cacheResult,String> {
}
