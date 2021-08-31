package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;


public class Compare {
    //all result compare
    public static HashMap<Integer, ResultCompare> resultCompareFiles = new HashMap<>();

    //===SERVICES===
    //check the mandatory fields for the services
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsMandatoryServices = new HashMap<>();

    //check the optional fields for the services
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalServices1 = new HashMap<>();
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalServices2 = new HashMap<>();
    //===SERVICES END===

    //===ARTIFACTS===
    //check the mandatory fields for the mvn for the artifacts
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsMandatoryArtifacts = new HashMap<>();
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsMandatoryArtifactsMvn = new HashMap<>();



    private CompareMetadata compareMetadata = new CompareMetadata();
    private CompareServices compareServices = new CompareServices();
    private CompareArtifacts compareArtifacts = new CompareArtifacts();

    private CheckMandatoryFieldsServices checkMandatoryFieldsServices = new CheckMandatoryFieldsServices();


    private JsonNode node1;
    private JsonNode node2;

    private ValidatorJson validatorJson = new ValidatorJson();

    public void compareFiles() {

        checkFieldsOptionalServices1.clear();
        checkFieldsOptionalServices2.clear();
        checkFieldsMandatoryServices.clear();
        resultCompareFiles.clear();

        if(validatorJson.validationObjectJson(node1) & validatorJson.validationObjectJson(node2)) {
            if(node1.hashCode() == node2.hashCode()) {
                int hash = node1.hashCode();

                checkMandatoryFieldsServices.checkAvailabilityFields(node1, node2);
                resultCompareFiles.put(hash, ResultCompare.EQUAL);
            }
            else {
                resultCompareFiles.put(node1.hashCode(), ResultCompare.NOTEQUAL);
                resultCompareFiles.put(node2.hashCode(), ResultCompare.NOTEQUAL);

                //compare metadata
                compareMetadata.metadataCompare(node1, node2);

                //compare services
                checkMandatoryFieldsServices.checkAvailabilityFields(node1, node2);
                compareServices.servicesCompare(node1, node2);

                //compare artifacts

            }
        }
        else {
            resultCompareFiles.put(1, ResultCompare.WRONGSTRUCTURE);
        }

        for(Map.Entry<Integer, HashMap<String, ResultCompare>> map : checkFieldsOptionalServices1.entrySet()) {
            System.out.println(map.getKey() + ":" + map.getValue());
        }
        System.out.println("##########");

        for(Map.Entry<Integer, HashMap<String, ResultCompare>> map : checkFieldsOptionalServices2.entrySet()) {
            System.out.println(map.getKey() + ":" + map.getValue());
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
