package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class CheckElementsMetadata {

    //check MetadataVersion type
    public boolean typeVersionMetadata(JsonNode node1, JsonNode node2) {
        if(node1.get("metadata").get("description").get("version").getNodeType().equals(JsonNodeType.NUMBER) &&
                node2.get("metadata").get("description").get("version").getNodeType().equals(JsonNodeType.NUMBER)) {
            return true;
        }
        else {
            return false;
        }
    }

    //compare MetadataVersions
    public boolean compareVersionsMetadata(JsonNode node1, JsonNode node2) {
        if(node1.get("metadata").get("description").get("version").asInt() == node2.get("metadata").get("description").get("version").asInt()) {
            return true;
        }
        else {
            return false;
        }
    }

    //check MetadataName type
    public boolean typeNameMetadata(JsonNode node1, JsonNode node2) {
        if(node1.get("metadata").get("application").get("name").getNodeType().equals(JsonNodeType.STRING) &&
                node2.get("metadata").get("application").get("name").getNodeType().equals(JsonNodeType.STRING)) {
            return true;
        }
        else {
            return false;
        }
    }

    //compare MetadataNames
    public boolean compareNamesMetadata(JsonNode node1, JsonNode node2) {
        if(node1.get("metadata").get("application").get("name").asText().equals(node2.get("metadata").get("application").get("name").asText())) {
            return true;
        }
        else {
            return false;
        }
    }
}
