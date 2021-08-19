package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

public class CompareServices {
    public void servicesCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("services").hashCode() != node2.get("services").hashCode()) {
            int hash = node1.get("services").hashCode();
            Compare.resultCompareFiles.put(hash, ResultCompare.NOTEQUAL);

            //check size
            if(node1.get("services").size() > node2.get("services").size()) {
                int value = node1.get("services").size() - 1;
                int hash1 = node1.get("services").get(value).hashCode();
                Compare.checkAvailabilityElement.put(hash1, ResultCompare.EXIST);
            }
            else if(node1.get("services").size() < node2.get("services").size()) {
                int value = node2.get("services").size() - 1;
                int hash2 = node2.get("services").get(value).hashCode();
                Compare.checkAvailabilityElement.put(hash2,ResultCompare.EXIST);
            }


//            for(int i = 0; i < node1.get("services").size(); ++i) {
//                for(int j = 0; j < node2.get("services").size(); ++j) {
//
//                }
//            }
        }




    }
}
