package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

public class CompareServices {
    public void servicesCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("services").hashCode() == node2.get("services").hashCode()) {
            int hash = node1.get("services").hashCode();

            Compare.resultCompareFiles.put(hash, ResultCompare.EQUAL);
        }
        else {
            int hash1 = node1.get("services").hashCode();
            int hash2 = node2.get("services").hashCode();

            Compare.resultCompareFiles.put(hash1, ResultCompare.NOTEQUAL);
            Compare.resultCompareFiles.put(hash2, ResultCompare.NOTEQUAL);

            //json file #1
            for(JsonNode service1 : node1.get("services")) {
                int count = 0;
                for(JsonNode service2 : node2.get("services")) {
                    if(service1.hashCode() == service2.hashCode()) {
                        ++count;
                    }
                }
                if(count > 0) {
                    Compare.resultCompareFiles.put(service1.hashCode(), ResultCompare.EQUAL);
                }
                else {
                    Compare.resultCompareFiles.put(service1.hashCode(), ResultCompare.NOTEQUAL);
                }
            }

            //json file #2
            for(JsonNode service2 : node2.get("services")) {
                int count = 0;
                for(JsonNode service1 : node1.get("services")) {
                    if(service2.hashCode() == service1.hashCode()) {
                        ++count;
                    }
                }
                if(count > 0) {
                    Compare.resultCompareFiles.put(service2.hashCode(), ResultCompare.EQUAL);
                }
                else {
                    Compare.resultCompareFiles.put(service2.hashCode(), ResultCompare.NOTEQUAL);
                }

            }
        }




    }
}
