package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;


public class Compare {
    public static HashMap<String, JsonNode> checkAvailabilityElement = new HashMap<>();
    public static HashMap<String, ResultCompare> resultCompareFiles = new HashMap<>();
    private CompareMetadata compareMetadata = new CompareMetadata();



    private JsonNode node1;
    private JsonNode node2;

    private ValidatorJson validatorJson = new ValidatorJson();

    public void compareFiles() {
        checkAvailabilityElement.clear();
        resultCompareFiles.clear();

        if(validatorJson.validationObjectJson(node1) & validatorJson.validationObjectJson(node2)) {
            if(node1.hashCode() == node2.hashCode()) {
                resultCompareFiles.put("nodes", ResultCompare.EQUAL);
            }
            else {
                compareMetadata.metadataCompare(node1, node2);

            }
        }
        else {
            resultCompareFiles.put("validFile", ResultCompare.WRONGSTRUCTURE);
        }
    }





    public JsonNode getNode1() {
        return node1;
    }

    public void setNode1(JsonNode node1) {
        this.node1 = node1;
    }

    public JsonNode getNode2() {
        return node2;
    }

    public void setNode2(JsonNode node2) {
        this.node2 = node2;
    }
}
