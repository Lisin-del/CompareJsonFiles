package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

public class CompareMetadata {
    public void metadataCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("metadata").hashCode() != node2.get("metadata").hashCode()) {
            //check description
            if(node1.get("metadata").get("description").hashCode() == node2.get("metadata").get("description").hashCode()) {
                int hash = node1.get("metadata").get("description").hashCode();

                Compare.resultCompareFiles.put(hash, ResultCompare.EQUAL);
            }
            else {
                int hash1 = node1.get("metadata").get("description").hashCode();
                int hash2 = node2.get("metadata").get("description").hashCode();

                Compare.resultCompareFiles.put(hash1, ResultCompare.NOTEQUAL);
                Compare.resultCompareFiles.put(hash2, ResultCompare.NOTEQUAL);
            }

            //check application
            if(node1.get("metadata").get("application").hashCode() == node2.get("metadata").get("application").hashCode()) {
                int hash = node1.get("metadata").get("application").hashCode();

                Compare.resultCompareFiles.put(hash, ResultCompare.EQUAL);
            }
            else {
                int hash1 = node1.get("metadata").get("application").hashCode();
                int hash2 = node2.get("metadata").get("application").hashCode();

                Compare.resultCompareFiles.put(hash1, ResultCompare.NOTEQUAL);
                Compare.resultCompareFiles.put(hash2, ResultCompare.NOTEQUAL);
            }
        }
    }
}
