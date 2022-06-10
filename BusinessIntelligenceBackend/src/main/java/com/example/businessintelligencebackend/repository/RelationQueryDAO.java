package com.example.businessintelligencebackend.repository;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import static org.neo4j.driver.Values.parameters;

public class RelationQueryDAO implements AutoCloseable{
    private final Driver driver;

    public RelationQueryDAO(String uri, String user, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }


    public Record findRelation(final String startName, final String endLabel){
        try(Session session = driver.session()) {
            Record ans = session.readTransaction(new TransactionWork<Record>() {
                @Override
                public Record execute(Transaction transaction) {
                    Result result;
                    if( endLabel.equals("Actor")){
                        result = transaction.run("match (d:person)-[r:COOPERATE]-(a:person) where a.name=$data and d.act_num>0 return d.name as name, r.num as count order by count DESC limit 1;",
                                parameters("data", startName));
                    } else
                        result = transaction.run("match (d:person)-[r:COOPERATE]-(a:person) where a.name=$data and d.direct_num>0 return d.name as name, r.num as count order by count DESC limit 1;",
                                parameters("data", startName));
                    return result.single();
                }
            });
            return ans;
        }
    }

    public Record findProduct(final String title){
        try(Session session = driver.session()) {
            Record ans = session.readTransaction(new TransactionWork<Record>() {
                @Override
                public Record execute(Transaction transaction) {
                    Result result;
                        result = transaction.run("match p=(a:movie)-[r:INCLUDE]->(d:product) where a.title=\"" +
                                title +
                                "\" return count(d) as count;");
                    return result.single();
                }
            });
            return ans;
        }
    }
    public Record findCooperate() {
        try(Session session = driver.session()) {
            Record ans = session.readTransaction(new TransactionWork<Record>() {
                @Override
                public Record execute(Transaction transaction) {
                    Result result;
                    result = transaction.run("match (a:person)-[r:COOPERATE]-(b:person)" +
                            " return a.name as name1, b.name as name2, r.num as count" +
                            " order by count DESC limit 1;");
                    return result.single();
                }
            });
            return ans;
        }
    }


    public static void main(String[] args) throws Exception {
        try (RelationQueryDAO query = new RelationQueryDAO("bolt://101.43.113.43:7687", "neo4j", "Neo4j")){
        }
    };

}
