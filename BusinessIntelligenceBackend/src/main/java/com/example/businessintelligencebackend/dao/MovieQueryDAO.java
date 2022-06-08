package com.example.businessintelligencebackend.dao;

import org.neo4j.driver.*;

import java.util.List;

public class MovieQueryDAO implements AutoCloseable{

    private final Driver driver;

    public MovieQueryDAO(String uri, String user, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }


    public List<Record>queryMovieByDirector (final String director){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match (d:person)-[:DIRECT_TO]-(a:movie) where d.name=\"" +
                            director + "\"" +
                                    " return a.title as title;");
                    return result.list();
                }
            });
            return ans;
        }
    }


    public List<Record>queryMovieByActor (final String actor){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match (d:person)-[:ACTED_IN]-(a:movie) where d.name=\"" +
                            actor + "\"" +
                            " return a.title as title;");
                    return result.list();
                }
            });
            return ans;
        }
    }


    public List<Record>queryMovieByLabel (final String style){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match (a:movie) where a.style=~\"(?i).*"+
                            style +
                                    ".*\" return a.title as title ;");
                    return result.list();
                }
            });
            return ans;
        }
    }


    public List<Record>queryMovieByScore (String rating){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result;
                    result = transaction.run("match (a:movie) where a.rating>\""+
                            rating + "\"" +
                            " return a.title as title;");
                    return result.list();
                }
            });
            return ans;
        }
    }

    public List<Record>queryMovieByLabelAndYear (final String style,final String year){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    Result result = transaction.run("match (a:movie) where a.style=~\"(?i).*"+
                            style +
                            ".*\" and a.year=" +
                            year +
                            " return a.title as title ;");
                    return result.list();
                }
            });
            return ans;
        }
    }

    public static void main(String[] args) throws Exception {
        try (MovieQueryDAO query = new MovieQueryDAO("bolt://101.43.113.43:7687", "neo4j", "Neo4j")){
        }
    };
}
