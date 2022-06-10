package com.example.businessintelligencebackend.dao;

import org.neo4j.driver.*;

import java.util.List;

public class BIQueryDAO  implements AutoCloseable{
    private final Driver driver;

    public BIQueryDAO(String uri, String user, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public List<Record> querySingle (int step ,int limit,int id){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    String query = "MATCH p=((n)-[*"+step+"]-()) where id(n)="+id+" return p limit " + limit;
                    Result result = transaction.run(query);
                    return result.list();
                }
            });
            return ans;
        }
    }
    public static void main(String[] args) throws Exception {
        try (BIQueryDAO query = new BIQueryDAO("bolt://101.43.113.43:7687", "neo4j", "Neo4j")){
        }
    };
}
