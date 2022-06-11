package com.example.businessintelligencebackend.dao;


import org.neo4j.driver.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    public Record getID(String label,String name){
        try(Session session = driver.session()){
            Record ans = session.readTransaction(new TransactionWork<Record>() {
                @Override
                public Record execute(Transaction transaction) {
                    String query = null;
                    if(Objects.equals(label, "author"))
                        query = "match (a:author) where a.name=\""+ name + "\" return id(a) as id limit 1 ;";
                    else if(Objects.equals(label, "paper"))
                        query= "match (a:paper) where a.title=~\"(?i).*"+ name + ".*\" return id(a) as id limit 1 ;";
                    else if(Objects.equals(label,"interest"))
                        query="match (a:interest) where a.interest_name=~\"(?i).*"+ name + ".*\" return id(a) as id limit 1 ;";
                    else if(Objects.equals(label,"publication"))
                        query="match (a:publication) where a.publication_name=~\"(?i).*"+ name + ".*\" return id(a) as id limit 1 ;";
                    else
                        query="match (a:affiliation) where a.affiliation_name=~\"(?i).*"+ name + ".*\" return id(a) as id limit 1 ;";
                    Result result = transaction.run(query);
                    return result.single();
                }
            });
            return ans;
        }
    }
    public List<Record> businessAuthor(String name){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    String query = "MATCH (p:author)-[r:is_interested_in]->(b:interest) where b.interest_name=\""+name+"\" RETURN p order by p.h_index DESC LIMIT 25 ";
                    Result result = transaction.run(query);
                    return result.list();
                }
            });
            return ans;
        }
    }

    public List<Record> businessAffiliation(String name){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    String query = "MATCH (n:author)-[r:is_interested_in]->(i:interest) where i.interest_name=\""+name+"\"  " +
                            "with id(n) as id " +
                            "Match (m:author)-[q:belong_to]->(p:affiliation) where id(m)=id " +
                            "RETURN p ";
                    Result result = transaction.run(query);
                    return result.list();
                }
            });
            return ans;
        }
    }

    public List<Record> businessPublication(String name){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    String query = "MATCH (n:author)-[r:is_interested_in]->(i:interest) where i.interest_name=\""+name +"\" " +
                            "with id(n) as id " +
                            "Match (m:author)-[q:write]->(s:paper) where id(m)=id " +
                            "with id(s) as sid " +
                            "Match (z:paper)<-[y:publish]-(p:publication) where id(z)=sid " +
                            "return p ";
                    Result result = transaction.run(query);
                    return result.list();
                }
            });
            return ans;
        }
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


    public List<Record> queryDouble (int step, int limit,int sourceId, int targetId){
        try(Session session = driver.session()){
            List<Record> ans = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction transaction) {
                    String query = "MATCH p=((n)-[*1.."+step+"]-(m)) where id(n)="+sourceId+" and id(m)="+targetId+" return p limit " + limit;
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
