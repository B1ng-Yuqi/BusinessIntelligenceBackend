package com.example.businessintelligencebackend.dao;

import org.neo4j.driver.*;

import java.util.List;


public class TimeQueryDAO implements AutoCloseable{
    private final Driver driver;

    public TimeQueryDAO(String uri, String user, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }


    public List<Record> queryTimeByYear(final String year){
        try(Session session = driver.session()){
            List<Record> cnt = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result;
                    result = transaction.run("match(a:movie) where a.year="+ year +" return a.title as title;");
                    return result.list();
                }
            });
            return cnt;
        }
    }


    public List<Record> queryTimeBySeason(final String year, final String season){
        try(Session session = driver.session()){
            List<Record> cnt = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result ;
                    switch (season) {
                        case "1":
                            result = transaction.run("match(a:movie) where a.year=" + year + " and a.month>0 and a.month<4 return a.title as title;");
                            break;
                        case "2":
                            result = transaction.run("match(a:movie) where a.year=" + year + " and a.month>3 and a.month<7 return a.title as title;");
                            break;
                        case "3":
                            result = transaction.run("match(a:movie) where a.year=" + year + " and a.month>6 and a.month<10 return a.title as title;");
                            break;
                        case "4":
                            result = transaction.run("match(a:movie) where a.year=" + year + " and a.month>9 and a.month<13 return a.title as title;");
                            break;
                        default:
                            result = transaction.run("match(a:movie) where a.year=" + year +" return a.title as title;");
                            break;
                    }
                    return result.list();
                }
            });
            return cnt;
        }
    }


    public List<Record> queryTimeByMonth(final String year, final String month){
        try(Session session = driver.session()){
            List<Record> cnt = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result;
                    result = transaction.run("match(a:movie) where a.year=" +
                            year +
                            " and a.month=" +
                            month +
                            " return a.title as title;");
                    return result.list();
                }
            });
            return cnt;
        }
    }


    public List<Record> queryTimeByDay(final String year, final String month, final String day){
        try(Session session = driver.session()){
            List<Record> cnt = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result;
                    result = transaction.run("match(a:movie) where a.year=" +
                            year +
                            " and a.month=" +
                            month +
                            " and a.day=" +
                            day +
                            " return a.title as title;");
                    return result.list();
                }
            });
            return cnt;
        }
    }
    public Record queryTimeByYearAndActor(String year, String actor) {
        try(Session session = driver.session()) {
            Record ans = session.readTransaction(new TransactionWork<Record>() {
                @Override
                public Record execute(Transaction transaction) {
                    Result result;
                    result = transaction.run("MATCH (d:person)-[r:ACTED_IN]->(a:movie)  where a.year=" +
                            year +
                            " and d.name=\"" +
                            actor +
                            "\" RETURN count(r) as count");
                    return result.single();
                }
            });
            return ans;
        }
    }

    public static void main(String[] args) throws Exception {
        try (TimeQueryDAO query = new TimeQueryDAO("bolt://101.43.113.43:7687", "neo4j", "Neo4j")){
        }
    };

}
