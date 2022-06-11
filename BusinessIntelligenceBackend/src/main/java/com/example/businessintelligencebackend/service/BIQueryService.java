package com.example.businessintelligencebackend.service;

import com.example.businessintelligencebackend.entity.NodeEntity;
import com.example.businessintelligencebackend.entity.RelationEntity;
import com.example.businessintelligencebackend.dao.BIQueryDAO;
import io.swagger.models.auth.In;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BIQueryService {
    private final BIQueryDAO biQueryDAO;
    public BIQueryService(BIQueryDAO biQueryDAO){
        this.biQueryDAO = biQueryDAO;
    }

    public HashMap<String, Integer> getAimID(final String label, final String name) {
        Record record = biQueryDAO.getID(label, name);
        HashMap<String, Integer> item = new HashMap<String, Integer>();
        item.put("id", record.get("id").asInt());
        return item;
    }

    public HashMap<String, NodeEntity> getBusinessAuthor(final String name) {
        List<Record> recordList = biQueryDAO.businessAuthor(name);
        NodeEntity nodeEntity=new NodeEntity();
        HashMap<String,NodeEntity> hashMap = new HashMap<>();
        int max=0;
        float h_max=0;
        for (Record record : recordList) {
            Node node =record.get("p").asNode();
            float now = Float.parseFloat(node.get("h_index").toString().replace("\"", ""));
            int citation = Integer.parseInt(node.get("total_citations").toString().replace("\"", ""));
            if (now >= h_max)
                if (citation >= max) {
                    max=citation;
                    h_max=now;
                    nodeEntity = nodeToEntity(node);
                }
        }
        hashMap.put("nodes",nodeEntity);
        return hashMap;
    }

    public HashMap<String, ArrayList<NodeEntity>> searchByTypeAndId(int step ,int limit,int id)
    {
        List<Record> result = biQueryDAO.querySingle(step,limit,id);
        return getStringArrayListHashMap(result);
    }

    public HashMap<String, ArrayList<NodeEntity>> searchByTwoNodes( int step, int limit, int sourceId, int targetId)
    {
        List<Record> result=biQueryDAO.queryDouble(step,limit,sourceId,targetId);
        return getStringArrayListHashMap(result);
    }

    private HashMap<String, ArrayList<NodeEntity>> getStringArrayListHashMap(List<Record> result) {
        HashMap<String,ArrayList<NodeEntity>> hashMap = new HashMap<>();
        ArrayList<NodeEntity> nodeList = new ArrayList<>();
        ArrayList<NodeEntity> relationList = new ArrayList<>();
        for(Record record:result){
            // 一个path相当于一条结果，这“一条结果”就是在查询语句中定义的'p'对应的表达式
            Path path = record.get("p").asPath();
            // 遍历节点添加
            Iterable<Node> nodes = path.nodes();
            for (Node node: nodes){
                NodeEntity nodeEntity = nodeToEntity(node);
                if (!nodeList.contains(nodeEntity))
                    nodeList.add(nodeEntity);
            }
            // 遍历关系添加
            Iterable<Relationship> relationships = path.relationships();
            for (Relationship relationship : relationships){
                RelationEntity relationEntity = relationToEntity(relationship);
                if (!relationList.contains(relationEntity))
                    relationList.add(relationEntity);
            }
        }
        hashMap.put("nodes", nodeList);
        hashMap.put("relations", relationList);
        return hashMap;
    }

    private NodeEntity nodeToEntity(Node node){
        NodeEntity nodeEntity = null;
        if (node != null){
            nodeEntity = new NodeEntity();
            nodeEntity.put("id", node.id());
            nodeEntity.put("label", node.labels());
            nodeEntity.putAll(node.asMap());
            return nodeEntity;
        }else {
            return null;
        }
    }
    private RelationEntity relationToEntity(Relationship relationship){
        RelationEntity relationEntity = null;
        if (relationship != null){
            relationEntity = new RelationEntity();
            relationEntity.put("id", relationship.id());
//            relationEntity.put("id", new Random().nextInt());
            relationEntity.put("source", relationship.startNodeId());
            relationEntity.put("target", relationship.endNodeId());
            relationEntity.put("label", relationship.type());
            relationEntity.putAll(relationship.asMap());
            return relationEntity;
        }else {
            return null;
        }
    }


    public static void main(String[] args) {
        BIQueryDAO query = new BIQueryDAO("bolt://101.43.113.43:7687", "neo4j", "Neo4j");
        BIQueryService service = new BIQueryService(query);
    }

}
