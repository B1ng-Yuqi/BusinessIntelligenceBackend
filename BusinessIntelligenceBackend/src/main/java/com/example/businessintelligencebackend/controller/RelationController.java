package com.example.businessintelligencebackend.controller;

import com.example.businessintelligencebackend.dao.RelationQueryDAO;
import com.example.businessintelligencebackend.service.RelationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/neo4j/relationships", produces = {MediaType.APPLICATION_JSON_VALUE})
public class RelationController {

    final private RelationService relationService = new RelationService(
            new RelationQueryDAO("bolt://101.43.113.43:7687", "neo4j", "Neo4j")
    );


    @ResponseBody
    @RequestMapping(value = "/actor", method = RequestMethod.GET)
    public HashMap<Object, Object> getActorByActorInNeo4j(String actor) {
        return this.relationService.generateRelation(actor, "Actor");
    }


    @ResponseBody
    @RequestMapping(value = "/director", method = RequestMethod.GET)
    public HashMap<Object, Object> getDirectorByActorInNeo4j(String director) {
        return this.relationService.generateRelation(director,"Director");
    }

    @ResponseBody
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public HashMap<Object, Object> getProductNum(String title) {
        return this.relationService.productRelation(title);
    }

    @ResponseBody
    @RequestMapping(value = "/cooperate", method = RequestMethod.GET)
    public HashMap<Object, Object> getCooperateMost() {
        return this.relationService.getCooperateMost();
    }
}
