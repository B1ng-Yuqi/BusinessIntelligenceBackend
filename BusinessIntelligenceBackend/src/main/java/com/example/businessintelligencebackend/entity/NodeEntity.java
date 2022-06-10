package com.example.businessintelligencebackend.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class NodeEntity implements Serializable {
    public HashMap<String, Object> properties;

    public NodeEntity() {
        properties = new HashMap<>();
    }

    public NodeEntity(HashMap<String, Object> properties) {
        this.properties = properties;
    }

    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }

    public Object get(String key){
        return properties.get(key);
    }

    public void put(String key, Object o){
        properties.put(key, o);
    }

    public void putAll(Map<String, Object> map){
        properties.putAll(map);
    }

    @Override
    public String toString(){
        return getProperties().toString();
    }


    @Override
    public boolean equals(Object object){
        if (object == null)
            return false;
        else if (!(object instanceof NodeEntity))
            return false;
        return getProperties().get("id").equals(((NodeEntity) object).getProperties().get("id"));
    }
}