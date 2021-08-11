package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.util.HashMap;

public class Compare {
    public static  HashMap<String, String> infoAboutCompareProcess = new HashMap<>();

    public void compareJsonFile(JsonNode node1, JsonNode node2) {
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


        if(node1.get("services").get(0) != null && node2.get("services").get(0) != null) {

            if(node1.get("services").get(0).get("service-short-name") != null && node2.get("services").get(0).get("service-short-name") != null) {
                if(node1.get("services").get(0).get("service-short-name").getNodeType().equals(JsonNodeType.STRING) &&
                        node2.get("services").get(0).get("service-short-name").getNodeType().equals(JsonNodeType.STRING)) {
                    if(node1.get("services").get(0).get("service-short-name").asText().equals(node2.get("services").get(0).get("service-short-name").asText())) {
                        String info = "exist type equal";
                        infoAboutCompareProcess.put("servicesTopObjectServiceShortName", info);
                    }
                    else {
                        String info = "exist type notEqual";
                        infoAboutCompareProcess.put("serviceTopObjectServiceShortName", info);
                    }
                }
                else {
                    String info = "exist wrongType notEqual";
                    infoAboutCompareProcess.put("serviceTopObjectServiceShortName", info);
                }
            }
            else {
                String info = "notExist";
                infoAboutCompareProcess.put("serviceTopObjectServiceShortName", info);
            }
        }
        else {
            String info = "notExist";
            infoAboutCompareProcess.put("serviceTopObject", info);
        }

    }

    public HashMap<String, String> getInfoAboutCompareProcess() {
        return infoAboutCompareProcess;
    }

}
