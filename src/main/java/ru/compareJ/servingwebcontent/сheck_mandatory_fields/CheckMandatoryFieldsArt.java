package ru.compareJ.servingwebcontent.сheck_mandatory_fields;

import com.fasterxml.jackson.databind.JsonNode;
import ru.compareJ.servingwebcontent.compare.Compare;
import ru.compareJ.servingwebcontent.ResultCompare;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckMandatoryFieldsArt {
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

    //the mandatory fields for the artifacts1
    private ArrayList<String> mandatoryFieldsArtifacts1 = new ArrayList<>();
    {
        mandatoryFieldsArtifacts1.add("mvn");
        mandatoryFieldsArtifacts1.add("target_repository");
    }


    //the mandatory fields for the artifacts2
    private ArrayList<String> mandatoryFieldsArtifacts2 = new ArrayList<>();
    {
        mandatoryFieldsArtifacts2.add("target_repository");
        mandatoryFieldsArtifacts2.add("hashes");
        mandatoryFieldsArtifacts2.add("sha1");
        mandatoryFieldsArtifacts2.add("sha256");
        mandatoryFieldsArtifacts2.add("file");
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

            if(artifact.get("mvn") != null || artifact.get("target_repository") != null) {
                if(artifact.size() == 2 || artifact.size() == 1) {
                    for(String fieldName : mandatoryFieldsArtifacts1) {
                        if(artifact.get(fieldName) == null) {
                            noFieldsArtifacts.put(fieldName, ResultCompare.NOTEXIST);
                            Compare.checkFieldsMandatoryArtifacts.put(artifact.hashCode(), noFieldsArtifacts);
                        }
                        else if(fieldName.equals("mvn") && artifact.get(fieldName) != null) {
                            checkAvailabilityFieldsArtifactsMvn(artifact.get(fieldName));
                        }
                    }
                }
            }


        }

        for(JsonNode artifact : node1.get("artifacts")) {
            HashMap<String, ResultCompare> noFieldsArtifacts = new HashMap<>();

            for(String fieldName : mandatoryFieldsArtifacts2) {
                if(artifact.get("mvn") == null && !fieldName.equals("sha1") && !fieldName.equals("sha256") && artifact.get(fieldName) == null) {
                    noFieldsArtifacts.put(fieldName, ResultCompare.NOTEXIST);
                    Compare.checkFieldsMandatoryArtifacts.put(artifact.hashCode(), noFieldsArtifacts);
                }
                else if(artifact.get("mvn") == null && fieldName.equals("sha1") || fieldName.equals("sha256")) {
                    if(artifact.get("hashes") != null) {
                        if(artifact.get("hashes").get(fieldName) == null) {
                            noFieldsArtifacts.put(fieldName, ResultCompare.NOTEXIST);
                            Compare.checkFieldsMandatoryArtifacts.put(artifact.hashCode(), noFieldsArtifacts);
                        }
                    }
                }
            }
        }

        //json file #2
        for(JsonNode artifact : node2.get("artifacts")) {
            HashMap<String, ResultCompare> noFieldsArtifacts = new HashMap<>();

            if(artifact.get("mvn") != null || artifact.get("target_repository") != null) {
                if(artifact.size() == 2 || artifact.size() == 1) {
                    for(String fieldName : mandatoryFieldsArtifacts1) {
                        if(artifact.get(fieldName) == null) {
                            noFieldsArtifacts.put(fieldName, ResultCompare.NOTEXIST);
                            Compare.checkFieldsMandatoryArtifacts.put(artifact.hashCode(), noFieldsArtifacts);
                        }
                        else if(fieldName.equals("mvn") && artifact.get(fieldName) != null) {
                            checkAvailabilityFieldsArtifactsMvn(artifact.get(fieldName));
                        }
                    }
                }
            }
        }

        for(JsonNode artifact : node2.get("artifacts")) {
            HashMap<String, ResultCompare> noFieldsArtifacts = new HashMap<>();

            for(String fieldName : mandatoryFieldsArtifacts2) {
                if(artifact.get("mvn") == null && !fieldName.equals("sha1") && !fieldName.equals("sha256") && artifact.get(fieldName) == null) {
                    noFieldsArtifacts.put(fieldName, ResultCompare.NOTEXIST);
                    Compare.checkFieldsMandatoryArtifacts.put(artifact.hashCode(), noFieldsArtifacts);
                }
                else if(artifact.get("mvn") == null && fieldName.equals("sha1") || fieldName.equals("sha256")) {
                    if(artifact.get("hashes") != null) {
                        if(artifact.get("hashes").get(fieldName) == null) {
                            noFieldsArtifacts.put(fieldName, ResultCompare.NOTEXIST);
                            Compare.checkFieldsMandatoryArtifacts.put(artifact.hashCode(), noFieldsArtifacts);
                        }
                    }
                }
            }
        }

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
                            Compare.checkFieldsMandatoryArtifactsMvn.put(mvn.hashCode(), noFieldsMvn);
                        }
                    }
                }
            }
        }
    }

}
