package com.example.businessintelligencebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class CacheEntity {
    @Document(collection = "user")
    @Data
    @AllArgsConstructor
    @ToString
    public class User {
        @Id
        private Long id;
        private String name;
    }

}
