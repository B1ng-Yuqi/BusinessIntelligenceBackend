package com.example.businessintelligencebackend.dao;

import com.example.businessintelligencebackend.entity.CacheEntity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepository extends MongoRepository<User,Long> {
}
