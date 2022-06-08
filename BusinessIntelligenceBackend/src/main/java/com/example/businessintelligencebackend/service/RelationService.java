package com.example.businessintelligencebackend.service;

import com.example.businessintelligencebackend.dao.RelationQueryDAO;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RelationService {

    private final RelationQueryDAO relationQueryDAO;

    public RelationService(RelationQueryDAO relationQueryDAO){
        this.relationQueryDAO = relationQueryDAO;
    }

    public HashMap<Object, Object> generateRelation(final String startName, final String type){

        List<HashMap<String, String>> ans= new ArrayList<>();

        long startTime = System.currentTimeMillis();    //获取开始时间
        Record record = relationQueryDAO.findRelation(startName, type);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        HashMap<Object, Object> item = new HashMap<Object, Object>();
        item.put("time",time);
        item.put("name", record.get("name").toString().replace("\"",""));
        item.put("count", record.get("count").toString().replace("\"",""));
        return item;
    }
    public HashMap<Object, Object> productRelation(final String title){
        long startTime = System.currentTimeMillis();    //获取开始时间
        Record record = relationQueryDAO.findProduct(title);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        HashMap<Object, Object> item = new HashMap<Object, Object>();
        item.put("time",time);
        item.put("count", record.get("count").toString().replace("\"",""));
        return item;
    }

    public HashMap<Object,Object> getCooperateMost(){
        long startTime = System.currentTimeMillis();    //获取开始时间
        Record record = relationQueryDAO.findCooperate();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        HashMap<Object, Object> item = new HashMap<Object, Object>();
        item.put("time",time);
        item.put("name1", record.get("name1").toString().replace("\"",""));
        item.put("name2", record.get("name2").toString().replace("\"",""));
        item.put("count",record.get("count").toString().replace("\"",""));
        return item;
    }

    public static void main(String[] args) {
        RelationQueryDAO query = new RelationQueryDAO("bolt://101.43.113.43:7687", "neo4j", "Neo4j");
        RelationService service = new RelationService(query);
    }
}
