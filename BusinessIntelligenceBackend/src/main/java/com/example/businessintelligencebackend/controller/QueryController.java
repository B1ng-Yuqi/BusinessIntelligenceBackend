package com.example.businessintelligencebackend.controller;

import com.example.businessintelligencebackend.dao.MovieQueryDAO;
import com.example.businessintelligencebackend.dao.TimeQueryDAO;
import com.example.businessintelligencebackend.service.SimpleQueryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;



@RestController
@CrossOrigin("*")
@RequestMapping(value = "/neo4j/query", produces = {MediaType.APPLICATION_JSON_VALUE})
public class QueryController {
    final private SimpleQueryService simpleQueryService = new SimpleQueryService(
            new MovieQueryDAO("bolt://101.43.113.43/:7687", "neo4j", "Neo4j"),
            new TimeQueryDAO("bolt://101.43.113.43:7687", "neo4j", "Neo4j")
    );


    @ResponseBody
    @RequestMapping(value = "/actor", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByActor(@RequestParam String actor){
        return simpleQueryService.generateMovieList(actor, "Actor");
    }

    @ResponseBody
    @RequestMapping(value = "/director", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByDirector(@RequestParam String director){
        return simpleQueryService.generateMovieList(director, "Director");
    }

    @ResponseBody
    @RequestMapping(value = "/label", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByLabel(@RequestParam String label){
        return simpleQueryService.generateMovieList(label, "Label");
    }

    @ResponseBody
    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByScore(@RequestParam String rating){
        return simpleQueryService.generateMovieListByScore(rating);
    }

    @ResponseBody
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public HashMap<String, Object> getCountByTimeInNeo4j(@RequestParam String year,
                                                         @RequestParam(required = false)String month,
                                                         @RequestParam(required = false)String season,
                                                         @RequestParam(required = false)String day) {
        return simpleQueryService.getTimeCount(year, month, season, day);
    }

    @ResponseBody
    @RequestMapping(value = "/label/year", method = RequestMethod.GET)
    public HashMap<String, Object> getMovieListByLabelAndYear(@RequestParam String label,@RequestParam String year) {
        return simpleQueryService.getMovieByLabelAndYear(label, year);
    }

    @ResponseBody
    @RequestMapping(value = "/actor/time", method = RequestMethod.GET)
    public HashMap<Object, Object> getCountByTimeActor(@RequestParam String year, @RequestParam String actor) {
        return simpleQueryService.getTimeActor(year, actor);
    }
}
