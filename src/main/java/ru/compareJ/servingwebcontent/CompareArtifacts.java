package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class CompareArtifacts {
    //the mandatory fields for the mvn for the artifacts
    private ArrayList<String> mandatoryFieldsArtifactsMvn = new ArrayList<>();
    {
        mandatoryFieldsArtifactsMvn.add("groupId");
        mandatoryFieldsArtifactsMvn.add("artifactId");
        mandatoryFieldsArtifactsMvn.add("version");
        mandatoryFieldsArtifactsMvn.add("mvn_type");
        mandatoryFieldsArtifactsMvn.add("mvn_repository");
        mandatoryFieldsArtifactsMvn.add("hashes");
        mandatoryFieldsArtifactsMvn.add("sha1");
        mandatoryFieldsArtifactsMvn.add("sha256");
    }

    //the optional fields for the mvn for the artifacts
    private ArrayList<String> optionalFieldsArtifactsMvn = new ArrayList<>();
    {
        optionalFieldsArtifactsMvn.add("service_name");
        optionalFieldsArtifactsMvn.add("classifier");
    }

    //the mandatory fields for the artifacts
    private ArrayList<String> mandatoryFieldsArtifacts = new ArrayList<>();
    {
        mandatoryFieldsArtifacts.add("mvn");
        mandatoryFieldsArtifacts.add("target_repository");
        mandatoryFieldsArtifacts.add("hashes");
        mandatoryFieldsArtifacts.add("sha1");
        mandatoryFieldsArtifacts.add("sha256");
        mandatoryFieldsArtifacts.add("file");
    }

    //the optional fields for the artifacts
    private ArrayList<String> optionalFieldsArtifacts = new ArrayList<>();
    {
        optionalFieldsArtifacts.add("service-short-name");
        optionalFieldsArtifacts.add("service_name");
    }

    public void artifactsCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("artifacts").hashCode() == node2.get("artifacts").hashCode()) {
            Compare.resultCompareFiles.put(node1.get("artifacts").hashCode(), ResultCompare.EQUAL);

            for(JsonNode artifact : node1.get("artifacts")) {
                Compare.resultCompareFiles.put(artifact.hashCode(), ResultCompare.EQUAL);
            }
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
