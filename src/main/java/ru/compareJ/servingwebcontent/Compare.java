package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;


public class Compare {
    public static HashMap<Integer, ResultCompare> checkAvailabilityElement = new HashMap<>();
    public static HashMap<Integer, ResultCompare> resultCompareFiles = new HashMap<>();
    private CompareMetadata compareMetadata = new CompareMetadata();
    private CompareServices compareServices = new CompareServices();


    private JsonNode node1;
    private JsonNode node2;

    private ValidatorJson validatorJson = new ValidatorJson();

    public void compareFiles() {
        checkAvailabilityElement.clear();
        resultCompareFiles.clear();

        if(validatorJson.validationObjectJson(node1) & validatorJson.validationObjectJson(node2)) {
            if(node1.hashCode() == node2.hashCode()) {
                int hash = node1.hashCode();
                resultCompareFiles.put(hash, ResultCompare.EQUAL);
            }
            else {
                int hash = node1.hashCode();
                resultCompareFiles.put(hash, ResultCompare.NOTEQUAL);
                compareMetadata.metadataCompare(node1, node2);
                compareServices.servicesCompare(node1, node2);
            }
        }
        else {
            resultCompareFiles.put(1, ResultCompare.WRONGSTRUCTURE);
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
