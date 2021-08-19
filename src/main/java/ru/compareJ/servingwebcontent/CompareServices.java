package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

public class CompareServices {
    public void servicesCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("services").hashCode() != node2.get("services").hashCode()) {
            Compare.resultCompareFiles.put("services", ResultCompare.NOTEQUAL);

            //check size
            int value;
            if(node1.get("services").size() > node2.get("services").size()) {
                value = 1;
            }
            else if(node1.get("services").size() < node2.get("services").size()) {
                value = 2;
            }


            for(int i = 0; i < node1.get("services").size(); ++i) {
                for(int j = 0; j < node2.get("services").size(); ++j) {

                }
            }
        }




    }
}
