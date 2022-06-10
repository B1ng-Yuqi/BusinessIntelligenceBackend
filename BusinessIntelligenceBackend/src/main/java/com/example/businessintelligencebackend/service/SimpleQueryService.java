package com.example.businessintelligencebackend.service;


import com.example.businessintelligencebackend.repository.MovieQueryDAO;
import com.example.businessintelligencebackend.repository.TimeQueryDAO;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleQueryService {

    private final MovieQueryDAO movieQueryDAO;
    private final TimeQueryDAO timeQueryDAO;

    public SimpleQueryService (MovieQueryDAO movieQueryDAO, TimeQueryDAO timeQueryDAO){
        this.movieQueryDAO = movieQueryDAO;
        this.timeQueryDAO = timeQueryDAO;
    }

    public HashMap<String, Object> generateMovieListByScore(String score){
        List<HashMap<String, String>> ans= new ArrayList<>();
        List<Record> recordList;
        long startTime = System.currentTimeMillis();    //获取开始时间
        recordList = movieQueryDAO.queryMovieByScore(score);
        long endTime = System.currentTimeMillis();    //获取开始时间
        long time = endTime - startTime;
        for (Record record: recordList) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("title", record.get("title").toString().replace("\"",""));
            ans.add(item);
        }
        long num = recordList.size();
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("time", time);
        ret.put("num",num);
        //ret.put("movieList", ans);
        return ret;
    }

    public HashMap<String, Object> generateMovieList(String data, String queryType){
        List<HashMap<String, String>> ans= new ArrayList<>();
        List<Record> recordList;
        long startTime = System.currentTimeMillis();    //获取开始时间
        switch (queryType) {
            case "actor":
            case "Actor":
                recordList = movieQueryDAO.queryMovieByActor(data);
                break;
            case "director":
            case "Director":
                recordList = movieQueryDAO.queryMovieByDirector(data);
                break;
            case "label":
            case "Label":
                recordList = movieQueryDAO.queryMovieByLabel(data);
                break;
            default:
                System.out.println("error");
                return new HashMap<String, Object>();
        }

        long endTime = System.currentTimeMillis();    //获取开始时间
        long time = endTime - startTime;

        for (Record record: recordList) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("title", record.get("title").toString().replace("\"",""));
            ans.add(item);
        }
        long num =recordList.size();
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("time", time);
        ret.put("num",num);
        //ret.put("movieList", ans);
        return ret;
    }

    public HashMap<String, Object> getTimeCount( String year, String month, String season, String day){

        List<HashMap<String, String>> ans= new ArrayList<>();
        List<Record> recordList;
        long startTime;    //获取开始时间
        if (year!=null && month == null && season == null && day == null) {
            startTime = System.currentTimeMillis();
            recordList = timeQueryDAO.queryTimeByYear(year);
        }
        else if(year!=null && month == null && season != null && day == null) {
            startTime = System.currentTimeMillis();
            recordList = timeQueryDAO.queryTimeBySeason(year, season);
        }
        else if(year!=null && month != null && season == null && day == null) {
            startTime = System.currentTimeMillis();
            recordList = timeQueryDAO.queryTimeByMonth(year, month);
        }
        else if(year!=null && month != null && season == null && day != null) {
            startTime = System.currentTimeMillis();
            recordList = timeQueryDAO.queryTimeByDay(year, month, day);
        }
        else{
                System.out.println("error");
                return new HashMap<>();
        }
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        for (Record record: recordList) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("title", record.get("title").toString().replace("\"",""));
            ans.add(item);
        }
        long num =recordList.size();
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("time", time);
        ret.put("num",num);
        //ret.put("movieList", ans);
        return ret;
    }

    public HashMap<String, Object> getMovieByLabelAndYear(String label,String year){
        List<HashMap<String, String>> ans= new ArrayList<>();
        List<Record> recordList;
        long startTime = System.currentTimeMillis();
        recordList = movieQueryDAO.queryMovieByLabelAndYear(label,year);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        for (Record record: recordList) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("title", record.get("title").toString().replace("\"",""));
            ans.add(item);
        }
        long num = recordList.size();
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put("time", time);
        ret.put("num",num);
        //ret.put("movieList", ans);
        return ret;
    }

    public HashMap<Object, Object> getTimeActor( String year, String actor){
        long startTime;    //获取开始时间
        startTime = System.currentTimeMillis();
        Record record = timeQueryDAO.queryTimeByYearAndActor(year,actor);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        HashMap<Object, Object> item = new HashMap<Object, Object>();
        item.put("time",time);
        item.put("count", record.get("count").toString().replace("\"",""));
        return item;
    }

    public static void main(String[] args) {
        MovieQueryDAO query = new MovieQueryDAO("bolt://101.43.113.43:7687", "neo4j", "Neo4j");
        TimeQueryDAO timeQueryDAO = new TimeQueryDAO("bolt://101.43.113.43:7687", "neo4j", "Neo4j");
    }
}
