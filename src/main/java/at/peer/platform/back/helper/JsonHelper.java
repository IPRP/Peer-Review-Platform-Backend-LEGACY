package at.peer.platform.back.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

    private Object obj;

    public JsonHelper(Object obj){
        this.obj = obj;
    }

    public String generateJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(this.obj);
        } catch (JsonProcessingException e) {
            json = "Error: JsonProcessingException -> " + e.getMessage();
        }
        return json;
    }
}
