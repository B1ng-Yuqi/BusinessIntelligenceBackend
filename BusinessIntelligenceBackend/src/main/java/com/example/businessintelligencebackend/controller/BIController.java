package com.example.businessintelligencebackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.businessintelligencebackend.entity.CacheEntity;
import com.example.businessintelligencebackend.entity.NodeEntity;
import com.example.businessintelligencebackend.dao.BIQueryDAO;
import com.example.businessintelligencebackend.service.BIQueryService;
import com.example.businessintelligencebackend.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/BI", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BIController {
    final private BIQueryService biQueryService = new BIQueryService(
            new BIQueryDAO("bolt://101.43.113.43/:7687", "neo4j", "Neo4j"));

    @Autowired
    private CacheService cacheService = new CacheService();

    Calendar calendar = Calendar.getInstance(); // 获取当前时间
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); //时间格式


    @RequestMapping(value = "searchANode", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(maxAge = 3600, origins = "*")
    public String searchANode(@RequestParam("step") int step, @RequestParam("id") int id, @RequestParam("limit") int limit){
        String Id = ""+step+id+limit;
        if(!cacheService.existsById(Id)){
            HashMap<String, ArrayList<NodeEntity>> hashMap = biQueryService.searchByTypeAndId(step,limit,id);
            JSONObject result = new JSONObject();
            result.putAll(hashMap);
            String time = formatter.format(calendar.getTime());
            CacheEntity.SingleResult singleResult = new CacheEntity().new SingleResult(Id,time,result);
            cacheService.save(singleResult);
        }
        JSONObject result = cacheService.findOne(Id).getResult();
        return result.toJSONString();
    }

    @RequestMapping(value = "searchByTwoNodes", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(maxAge = 3600, origins = "*")
    public String searchByTwoNodes(@RequestParam("step") int step,
            @RequestParam("limit") int limit, @RequestParam("sourceId") int sourceId, @RequestParam("targetId") int targetId){

        HashMap<String, ArrayList<NodeEntity>> hashMap = biQueryService.searchByTwoNodes(step, limit, sourceId, targetId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(hashMap);
        String results = jsonObject.toJSONString();
        return results;
    }
}
