package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

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
    public static ArrayList<String> mandatoryFieldsArtifacts = new ArrayList<>();
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

            compare(node1.get("artifacts"), node2.get("artifacts"));
            compare(node2.get("artifacts"), node1.get("artifacts"));


        }
        compareOptionalFields1(node1, node2);
        compareOptionalFields2(node2, node1);
    }

    private void compare(JsonNode node1, JsonNode node2) {
        for(JsonNode artifact1 : node1) {
            int count = 0;

            Iterator<Map.Entry<String, JsonNode>> iterator1 = artifact1.fields();
            HashMap<String, JsonNode> fieldsCompare1 = new HashMap<>();

            while(iterator1.hasNext()) {
                Map.Entry<String, JsonNode> field = iterator1.next();

                for(String mandatoryField : mandatoryFieldsArtifacts) {
                    if(!mandatoryField.equals("sha1") && !mandatoryField.equals("sha256") && mandatoryField.equals(field.getKey())) {

                        if(mandatoryField.equals("file") && field.getValue().size() == 1) {
                            fieldsCompare1.put(field.getKey(), field.getValue());
                        }
                        else if(mandatoryField.equals("file") && field.getValue().size() > 1){
                            String randomName = RandomStringUtils.random(5);
                            fieldsCompare1.put(randomName, field.getValue());
                        }

                        if(!mandatoryField.equals("file")) {
                            fieldsCompare1.put(field.getKey(), field.getValue());
                        }

                    }
                    else if(mandatoryField.equals("sha1") || mandatoryField.equals("sha256")) {
                        if(field.getValue().get(mandatoryField) != null) {
                            fieldsCompare1.put(mandatoryField, field.getValue().get(mandatoryField));
                        }
                    }
                }
            }

            for(JsonNode artifact2 : node2) {
                Iterator<Map.Entry<String, JsonNode>> iterator2 = artifact2.fields();
                HashMap<String, JsonNode> fieldsCompare2 = new HashMap<>();

                while(iterator2.hasNext()) {
                    Map.Entry<String, JsonNode> field = iterator2.next();

                    for(String mandatoryField : mandatoryFieldsArtifacts) {
                        if(!mandatoryField.equals("sha1") && !mandatoryField.equals("sha256") && mandatoryField.equals(field.getKey())) {

                            if(mandatoryField.equals("file") && field.getValue().size() == 1) {
                                fieldsCompare2.put(field.getKey(), field.getValue());
                            }
                            else if(mandatoryField.equals("file") && field.getValue().size() > 1) {
                                String randomName = RandomStringUtils.random(5);
                                fieldsCompare2.put(randomName, field.getValue());
                            }

                            if(!mandatoryField.equals("file")) {
                                fieldsCompare2.put(field.getKey(), field.getValue());
                            }
                        }
                        else if(mandatoryField.equals("sha1") || mandatoryField.equals("sha256")) {
                            if(field.getValue().get(mandatoryField) != null) {
                                fieldsCompare2.put(mandatoryField, field.getValue().get(mandatoryField));
                            }
                        }
                    }
                }
                if(fieldsCompare1.equals(fieldsCompare2)) {
                    ++count;
                }
                if(count > 0) {
                    Compare.resultCompareFiles.put(artifact1.hashCode(), ResultCompare.EQUAL);
                }
                else {
                    Compare.resultCompareFiles.put(artifact1.hashCode(), ResultCompare.NOTEQUAL);
                }
            }
        }
    }


    private void compareMvn(JsonNode mvnArtifact1, JsonNode mvnArtifact2) {
        for(JsonNode mvn1 : mvnArtifact1.get("mvn")) {
            int count = 0;

            Iterator<Map.Entry<String, JsonNode>> iterator1 = mvn1.fields();
            HashMap<String, JsonNode> fieldsCompare1 = new HashMap<>();

            while(iterator1.hasNext()) {
                Map.Entry<String, JsonNode> field = iterator1.next();

                for(String mandatoryField : mandatoryFieldsArtifactsMvn) {
                    if(!mandatoryField.equals("sha1") && !mandatoryField.equals("sha256") && mandatoryField.equals(field.getKey())) {
                        fieldsCompare1.put(field.getKey(), field.getValue());
                    }
                    else if(mandatoryField.equals("sha1") || mandatoryField.equals("sha256")) {
                        if(field.getValue().get(mandatoryField) != null) {
                            fieldsCompare1.put(mandatoryField, field.getValue().get(mandatoryField));
                        }
                    }
                }
            }

            for(JsonNode mvn2 : mvnArtifact2.get("mvn")) {
                Iterator<Map.Entry<String, JsonNode>> iterator2 = mvn2.fields();
                HashMap<String, JsonNode> fieldsCompare2 = new HashMap<>();

                while(iterator2.hasNext()) {
                    Map.Entry<String, JsonNode> field = iterator2.next();

                    for(String mandatoryField : mandatoryFieldsArtifactsMvn) {
                        if(!mandatoryField.equals("sha1") && !mandatoryField.equals("sha256") && mandatoryField.equals(field.getKey())) {
                            fieldsCompare2.put(field.getKey(), field.getValue());
                        }
                        else if(mandatoryField.equals("sha1") || mandatoryField.equals("sha256")) {
                            if(field.getValue().get(mandatoryField) != null) {
                                fieldsCompare2.put(mandatoryField, field.getValue().get(mandatoryField));
                            }
                        }
                    }
                }

                if(fieldsCompare1.equals(fieldsCompare2)) {
                    ++count;
                }
                if(count > 0) {
                    int h = mvnArtifact1.hashCode();
                    Compare.resultCompareFiles.put(mvnArtifact1.hashCode(), ResultCompare.EQUAL);
                    Compare.resultCompareFiles.put(mvn1.hashCode(), ResultCompare.EQUAL);
                    Compare.resultCompareFiles.put(mvnArtifact1.get("mvn").hashCode(), ResultCompare.EQUAL);
                }
                else {
                    int h = mvnArtifact1.hashCode();
                    Compare.resultCompareFiles.put(mvnArtifact1.hashCode(), ResultCompare.NOTEQUAL);
                    Compare.resultCompareFiles.put(mvn1.hashCode(), ResultCompare.NOTEQUAL);
                    Compare.resultCompareFiles.put(mvnArtifact1.get("mvn").hashCode(), ResultCompare.NOTEQUAL);

                }
            }


        }
    }

    //compare optional fields node #1
    public void compareOptionalFields1(JsonNode node1, JsonNode node2) {
        for(JsonNode artifact1 : node1.get("artifacts")) {
            HashMap<String, ResultCompare> compareOpt = new HashMap<>();

            for(JsonNode artifact2 : node2.get("artifacts")) {

                for(String fieldName : optionalFieldsArtifacts) {
                    if(artifact1.get(fieldName) != null && artifact2.get(fieldName) != null &&
                            Compare.resultCompareFiles.get(artifact1.hashCode()) == ResultCompare.EQUAL && Compare.resultCompareFiles.get(artifact2.hashCode()) == ResultCompare.EQUAL) {

                        if(artifact1.get(fieldName).equals(artifact2.get(fieldName)) && compareOpt.get(fieldName) == null) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(artifact1.get(fieldName).equals(artifact2.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(!artifact1.get(fieldName).equals(artifact2.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.NOTEQUAL);
                        }
                    }
                }
            }
            Compare.checkFieldsOptionalArtifacts1.put(artifact1.hashCode(), compareOpt);
        }
    }

    //compare optional fields node #2
    public void compareOptionalFields2(JsonNode node2, JsonNode node1) {
        for(JsonNode artifact2 : node2.get("artifacts")) {
            HashMap<String, ResultCompare> compareOpt = new HashMap<>();

            for(JsonNode artifact1 : node1.get("artifacts")) {

                for(String fieldName : optionalFieldsArtifacts) {
                    if(artifact2.get(fieldName) != null && artifact1.get(fieldName) != null &&
                            Compare.resultCompareFiles.get(artifact2.hashCode()) == ResultCompare.EQUAL && Compare.resultCompareFiles.get(artifact1.hashCode()) == ResultCompare.EQUAL) {

                        if(artifact2.get(fieldName).equals(artifact1.get(fieldName)) && compareOpt.get(fieldName) == null) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(artifact2.get(fieldName).equals(artifact1.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(!artifact2.get(fieldName).equals(artifact1.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.NOTEQUAL);
                        }
                    }
                }
            }
            Compare.checkFieldsOptionalArtifacts2.put(artifact2.hashCode(), compareOpt);
        }
    }
}
