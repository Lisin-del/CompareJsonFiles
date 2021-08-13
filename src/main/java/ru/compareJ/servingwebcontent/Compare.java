package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;

public class Compare {
    private String infoCompareOk = "exist type equal";
    private String infoCompareNotEqual = "exist type notEqual";
    private String infoCompareWrongType = "exist wrongType notEqual";

    //sample new method
    public static HashMap<String, String> infoElementsFiles = new HashMap<>();
    public static HashMap<String, String> infoCompareFiles = new HashMap<>();

    private CheckElementsMetadata checkElementsMetadata = new CheckElementsMetadata();
    private CheckElementsServices checkElementsServices = new CheckElementsServices();

    public void compareJsonFile(JsonNode node1, JsonNode node2) {
        //---METADATA---
        //---compare versions---
        if(checkElementsMetadata.typeVersionMetadata(node1, node2)) {
            if(checkElementsMetadata.compareVersionsMetadata(node1, node2)) {
                infoCompareFiles.put("metadataVersion", infoCompareOk);
            }
            else {
                infoCompareFiles.put("metadataVersion", infoCompareNotEqual);
            }
        }
        else {
            infoCompareFiles.put("metadataVersion", infoCompareWrongType);
        }


        //---compare names---
        if(checkElementsMetadata.typeNameMetadata(node1, node2)) {
            if(checkElementsMetadata.compareNamesMetadata(node1, node2)) {
                infoCompareFiles.put("metadataName", infoCompareOk);
            }
            else {
                infoCompareFiles.put("metadataName", infoCompareNotEqual);
            }
        }
        else {
            infoCompareFiles.put("metadataName", infoCompareWrongType);
        }



        //---SERVICES---


    }


}
