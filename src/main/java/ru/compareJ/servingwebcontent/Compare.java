package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.util.HashMap;
import java.util.Map;

public class Compare {
    public static  HashMap<String, String> infoAboutCompareProcess = new HashMap<>();
    public static  HashMap<String, JsonNode> infoAboutAvailabilityNode1 = new HashMap<>();
    public HashMap<String, JsonNode> infoAboutAvailabilityNode2 = new HashMap<>();

    private String infoCompareOk = "exist type equal";
    private String infoCompareNotEqual = "exist type notEqual";
    private String infoCompareWrongType = "exist wrongType notEqual";
    private String infoCompareNotExist = "notExist";
    private String exist = "exist";




    public void compareJsonFile(JsonNode node1, JsonNode node2) {
        //---METADATA---
        if(node1.get("metadata").get("description").get("version").getNodeType().equals(JsonNodeType.NUMBER) &&
                node2.get("metadata").get("description").get("version").getNodeType().equals(JsonNodeType.NUMBER)) {

            if(node1.get("metadata").get("description").get("version").asInt() == node2.get("metadata").get("description").get("version").asInt()) {
                String info = "exist type equal";
                infoAboutCompareProcess.put("metadataDescriptionVersion", info);
            }
            else {
                String info = "exist type notEqual";
                infoAboutCompareProcess.put("metadataDescriptionVersion", info);
            }
        }
        else {
            String info = "exist wrongType notEqual";
            infoAboutCompareProcess.put("metadataDescriptionVersion", info);
        }

        if(node1.get("metadata").get("application").get("name").getNodeType().equals(JsonNodeType.STRING) &&
                node2.get("metadata").get("application").get("name").getNodeType().equals(JsonNodeType.STRING)) {
            if(node1.get("metadata").get("application").get("name").asText().equals(node2.get("metadata").get("application").get("name").asText())) {
                String info = "exist type equal";
                infoAboutCompareProcess.put("metadataApplicationName", info);
            }
            else {
                String info = "exist type notEqual";
                infoAboutCompareProcess.put("metadataApplicationName", info);
            }
        }
        else {
            String info = "exist wrongType notEqual";
            infoAboutCompareProcess.put("metadataApplicationName", info);
        }



        //---SERVICES---
        //CHECK TOP SERVICES OBJECTS NODE1
        if(node1.get("services").get(0) != null) {
            for(int i = 0; i < node1.get("services").size(); ++i) {
                infoAboutAvailabilityNode1.put("nodeServicesTopObject" + i, node1.get("services").get(i));
            }
        }
        else {
            infoAboutAvailabilityNode1.put("nodeServicesTopObject", null);
        }

        //CHECK TOP SERVICES OBJECTS NODE2
        if(node2.get("services").get(0) != null) {
            for(int i = 0; i < node2.get("services").size(); ++i) {
                infoAboutAvailabilityNode2.put("nodeServicesTopObject" + i, node2.get("services").get(i));
            }
        }
        else {
            infoAboutAvailabilityNode2.put("nodeServicesTopObject", null);
        }

        //CHECK SERVICE-SHORT-NAME
        for(Map.Entry<String, JsonNode> elementNode1 : infoAboutAvailabilityNode1.entrySet()) {
            for(Map.Entry<String, JsonNode> elementNode2 : infoAboutAvailabilityNode2.entrySet()) {
                if(elementNode1.getKey().equals(elementNode2.getKey())) {
                    if(elementNode1.getValue().get("service-short-name") != null) {
                        infoAboutAvailabilityNode1.put("serviceShortName" + elementNode1.getKey(), elementNode1.getValue());
                    }
                    else {
                        infoAboutAvailabilityNode1.put("serviceShortName" + elementNode1.getKey(), null);
                    }

                    if(elementNode2.getValue().get("service-short-name") != null) {
                        infoAboutAvailabilityNode2.put("serviceShortName" + elementNode2.getKey(), elementNode1.getValue());
                    }
                    else {
                        infoAboutAvailabilityNode2.put("serviceShortName" + elementNode2.getKey(), null);
                    }

                    if(infoAboutAvailabilityNode1.get("service-short-name" + elementNode1.getKey()) != null &&
                            infoAboutAvailabilityNode2.get("service-short-name" + elementNode2.getKey()) != null) {
                        if(elementNode1.getValue().get("service-short-name").getNodeType().equals(JsonNodeType.STRING) &&
                                elementNode2.getValue().get("service-short-name").getNodeType().equals(JsonNodeType.STRING)) {
                            if(elementNode1.getValue().get("service-short-name").asText().equals(elementNode2.getValue().get("service-short-name").asText())) {
                                infoAboutCompareProcess.put("servicesShortName", infoCompareOk);
                            }
                            else {
                                infoAboutCompareProcess.put("servicesShortName", infoCompareNotEqual);
                            }
                        }
                        else {
                            infoAboutCompareProcess.put("servicesShortName", infoCompareWrongType);
                        }
                    }
                    else {
                        infoAboutCompareProcess.put("servicesShortName", infoCompareNotExist);
                    }
                }
            }
        }

        for(Map.Entry<String, JsonNode> te : infoAboutAvailabilityNode1.entrySet()) {
            System.out.println(te.getKey() + te.getValue());
        }
        System.out.println();
        for(Map.Entry<String, JsonNode> te : infoAboutAvailabilityNode2.entrySet()) {
            System.out.println(te.getKey() + te.getValue());
        }
        System.out.println();
        for(Map.Entry<String, String> te : infoAboutCompareProcess.entrySet()) {
            System.out.println(te.getKey() + te.getValue());
        }
        int l = 0;





    }

    public HashMap<String, String> getInfoAboutCompareProcess() {
        return infoAboutCompareProcess;
    }

}
