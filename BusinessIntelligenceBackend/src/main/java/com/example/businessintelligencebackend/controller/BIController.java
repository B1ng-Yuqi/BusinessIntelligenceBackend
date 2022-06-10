package com.example.businessintelligencebackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.businessintelligencebackend.entity.NodeEntity;
import com.example.businessintelligencebackend.repository.BIQueryDAO;
import com.example.businessintelligencebackend.service.BIQueryService;
import com.example.businessintelligencebackend.service.SimpleQueryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/BI", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BIController {
    final private BIQueryService biQueryService = new BIQueryService(
            new BIQueryDAO("bolt://101.43.113.43/:7687", "neo4j", "Neo4j"));


    @RequestMapping(value = "searchANode", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(maxAge = 3600, origins = "*")
    public String searchANode(@RequestParam("step") int step, @RequestParam("id") int id, @RequestParam("limit") int limit){
        HashMap<String, ArrayList<NodeEntity>> hashMap = biQueryService.searchByTypeAndId(step,limit,id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(hashMap);
        String results = jsonObject.toJSONString();
        return results;
    }

}
