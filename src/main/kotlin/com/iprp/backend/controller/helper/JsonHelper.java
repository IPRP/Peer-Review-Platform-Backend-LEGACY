package com.iprp.backend.controller.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.iprp.backend.controller.obj.Workshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonHelper {

    private Object obj;
    private String json;

    public JsonHelper(Object obj){
        this.obj = obj;
        this.json = "";
    }
    public JsonHelper(String json){
        this.json = json;
        this.obj = null;
    }

    /**
     * Json zu Map
     * @param payload Json String
     * @return Map aus dem json, bei einer JsonProcessingException null
     */
    public static Map<String, Object> jsonPayloadToMap(String payload){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(payload, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Erzeugt aus einem Object eine Json
     * @return Json des Objects
     */
    public String generateJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "[";
        if(this.obj != null) {
            try {
                json += objectMapper.writeValueAsString(this.obj);
            } catch (JsonProcessingException e) {
                json = "Error: JsonProcessingException -> " + e.getMessage();
            }
        }
        json += "]";
        return json;
    }

    public Workshop generateWorkshop(){
        ObjectMapper objectMapper = new ObjectMapper();
        Workshop workshop = null;
        if (!this.json.isEmpty()) {
            try {
                workshop = objectMapper.readValue(this.json, Workshop.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return workshop;
    }


    public List<Workshop> generateWorkshopList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Workshop> returnList = new ArrayList<Workshop>();
        if (!this.json.isEmpty()){
            returnList = objectMapper.readValue(this.json, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Workshop.class));
        }
        return returnList;
    }

}
