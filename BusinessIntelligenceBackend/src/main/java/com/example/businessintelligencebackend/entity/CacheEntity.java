package com.example.businessintelligencebackend.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class CacheEntity {
    @Document(collection = "cache")
    @Data
    @AllArgsConstructor
    @ToString
    public class cacheResult {
        @Id
        private String id;
        private String time;
        private JSONObject result;

    }

}
