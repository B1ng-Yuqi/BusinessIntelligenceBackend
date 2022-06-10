package com.example.businessintelligencebackend.repository;

import com.example.businessintelligencebackend.entity.CacheEntity.SingleResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepository extends MongoRepository<SingleResult,String> {
}
