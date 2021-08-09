package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Node {
    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode jsonNode;

    public JsonNode getJsonNode(String line) {
        try {
            jsonNode = objectMapper.readTree(line);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }


}
