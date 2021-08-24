package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

public class CompareArtifacts {

    public void artifactsCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("artifacts").hashCode() == node2.get("artifacts").hashCode()) {
            Compare.resultCompareFiles.put(node1.get("artifacts").hashCode(), ResultCompare.EQUAL);
        }
        else {
            Compare.resultCompareFiles.put(node1.get("artifacts").hashCode(), ResultCompare.NOTEQUAL);
            Compare.resultCompareFiles.put(node2.get("artifacts").hashCode(), ResultCompare.NOTEQUAL);

            //json file #1
            for(JsonNode artifact1 : node1.get("artifacts")) {
                int count = 0;
                for(JsonNode artifact2 : node2.get("artifacts")) {
                    if(artifact1.hashCode() == artifact2.hashCode()) {
                        ++count;
                    }
                }
                if(count > 0) {
                    Compare.resultCompareFiles.put(artifact1.hashCode(), ResultCompare.EQUAL);
                }
                else {
                    Compare.resultCompareFiles.put(artifact1.hashCode(), ResultCompare.NOTEQUAL);
                }
            }

            //json file #2
            for(JsonNode artifact2 : node2.get("artifacts")) {
                int count = 0;
                for(JsonNode artifact1 : node1.get("artifacts")) {
                    if(artifact2.hashCode() == artifact1.hashCode()) {
                        ++count;
                    }
                }
                if(count > 0) {
                    Compare.resultCompareFiles.put(artifact2.hashCode(), ResultCompare.EQUAL);
                }
                else {
                    Compare.resultCompareFiles.put(artifact2.hashCode(), ResultCompare.NOTEQUAL);
                }
            }


        }
    }
}
