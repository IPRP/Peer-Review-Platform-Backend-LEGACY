package com.iprp.backend.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
//import com.iprp.backend.workshop.Workshop;

import java.util.ArrayList;
import java.util.List;

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

    /*
    public List<Workshop> generateWorkshopList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Workshop> returnList = new ArrayList<Workshop>();
        if (this.json != ""){
            returnList = objectMapper.readValue(this.json, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Workshop.class));
        }
        return returnList;
    }
     */
}
