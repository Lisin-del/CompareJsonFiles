package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckMandatoryFieldsMvnArt {
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




    public void checkAvailabilityFieldsArtifacts(JsonNode node1, JsonNode node2) {
        //json file #1
        for(JsonNode artifact : node1.get("artifacts")) {
            HashMap<String, ResultCompare> noFieldsArtifacts = new HashMap<>();
            if(artifact.get("mvn") == null) {
                noFieldsArtifacts.put("mvn", ResultCompare.NOTEXIST);
                Compare.checkFieldsMandatoryArtifacts.put(artifact.hashCode(), noFieldsArtifacts);
            }
            else if(artifact.get("mvn") != null) {
                //call the method checkAvailabilityFieldsMvn
                checkAvailabilityFieldsArtifactsMvn(artifact);
            }




        }

        //json file #2
    }

    private void checkAvailabilityFieldsArtifactsMvn(JsonNode artifact) {
        for(JsonNode mvn : artifact) {
            HashMap<String, ResultCompare> noFieldsMvn = new HashMap<>();

            for(String nameField : mandatoryFieldsArtifactsMvn) {
                if(!nameField.equals("sha1") && !nameField.equals("sha256") && mvn.get(nameField) == null) {
                    noFieldsMvn.put(nameField, ResultCompare.NOTEXIST);
                    Compare.checkFieldsMandatoryArtifactsMvn.put(mvn.hashCode(), noFieldsMvn);
                }
                else if(nameField.equals("sha1") || nameField.equals("sha256")) {
                    if(mvn.get("hashes") != null) {
                        if(mvn.get("hashes").get(nameField) == null) {
                            noFieldsMvn.put(nameField, ResultCompare.NOTEXIST);
                        }
                    }
                }
            }
        }
    }

}
