package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

public class CompareMetadata {
    public void metadataCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("metadata").hashCode() != node2.get("metadata").hashCode()) {
            if(node1.get("metadata").get("description").hashCode() != node2.get("metadata").get("description").hashCode()) {
                Compare.resultCompareFiles.put("metadataDescription", ResultCompare.NOTEQUAL);
            }
            if(node1.get("metadata").get("application").hashCode() != node2.get("metadata").get("application").hashCode()) {
                Compare.resultCompareFiles.put("metadataApplication", ResultCompare.NOTEQUAL);
            }
        }
    }
}
