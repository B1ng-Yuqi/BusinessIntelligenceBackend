package com.example.businessintelligencebackend.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class CacheEntity {
    @Document(collection = "single")
    @Data
    @AllArgsConstructor
    @ToString
    public class SingleResult {
        @Id
        private String id;
        private String time;
        private JSONObject result;

    }

}
