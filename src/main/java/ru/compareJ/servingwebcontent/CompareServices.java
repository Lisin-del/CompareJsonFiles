package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompareServices {


    public static ArrayList<String> mandatoryFieldsServices = new ArrayList<>();
    {
        mandatoryFieldsServices.add("service_name");
        mandatoryFieldsServices.add("artifact_type");
        mandatoryFieldsServices.add("docker_registry");
        mandatoryFieldsServices.add("docker_image_name");
        mandatoryFieldsServices.add("docker_tag");
        mandatoryFieldsServices.add("hashes");
        mandatoryFieldsServices.add("sha1");
        mandatoryFieldsServices.add("sha256");
    }

    private ArrayList<String> optionalFieldsServices = new ArrayList<>();
    {
        optionalFieldsServices.add("service-short-name");
        optionalFieldsServices.add("force");
        optionalFieldsServices.add("github_repository");
        optionalFieldsServices.add("github_branch");
        optionalFieldsServices.add("github_hash");
    }

    public void servicesCompare(JsonNode node1, JsonNode node2) {

        if(node1.get("services").hashCode() == node2.get("services").hashCode()) {
            int hash = node1.get("services").hashCode();
            Compare.resultCompareFiles.put(hash, ResultCompare.EQUAL);

            for(JsonNode node : node1.get("services")) {
                Compare.resultCompareFiles.put(node.hashCode(), ResultCompare.EQUAL);
            }
        }
        else {
            int hash1 = node1.get("services").hashCode();
            int hash2 = node2.get("services").hashCode();

            Compare.resultCompareFiles.put(hash1, ResultCompare.NOTEQUAL);
            Compare.resultCompareFiles.put(hash2, ResultCompare.NOTEQUAL);

            //json file #1
            for(JsonNode service1 : node1.get("services")) {
                int count = 0;
                //check mandatory fields services file 1
                Iterator<Map.Entry<String, JsonNode>> iterator1 = service1.fields();
                HashMap<String, JsonNode> fieldsNode1 = new HashMap<>();

                while(iterator1.hasNext()) {
                    Map.Entry<String, JsonNode> field = iterator1.next();
                    for(String mandatoryField : mandatoryFieldsServices) {
                        if(!mandatoryField.equals("sha1") && !mandatoryField.equals("sha256") && mandatoryField.equals(field.getKey())) {
                            fieldsNode1.put(field.getKey(), field.getValue());
                        }
                        else if(mandatoryField.equals("sha1") || mandatoryField.equals("sha256")) {
                            if(field.getValue().get(mandatoryField) != null) {
                                fieldsNode1.put(mandatoryField, field.getValue().get(mandatoryField));
                            }
                        }
                    }
                }


                for(JsonNode service2 : node2.get("services")) {
                    //check mandatory fields services file 2
                    HashMap<String, JsonNode> fieldsNode2 = new HashMap<>();
                    Iterator<Map.Entry<String, JsonNode>> iterator2 = service2.fields();


                    while(iterator2.hasNext()) {
                        Map.Entry<String, JsonNode> field = iterator2.next();
                        for(String mandatoryField : mandatoryFieldsServices) {
                            if(!mandatoryField.equals("sha1") && !mandatoryField.equals("sha256") && mandatoryField.equals(field.getKey())) {
                                fieldsNode2.put(field.getKey(), field.getValue());
                            }
                            else if(mandatoryField.equals("sha1") || mandatoryField.equals("sha256")) {
                                if(field.getValue().get(mandatoryField) != null) {
                                    fieldsNode2.put(mandatoryField, field.getValue().get(mandatoryField));
                                }
                            }
                        }
                    }

                    //check equal mandatory fields
                    if(fieldsNode1.equals(fieldsNode2)) {
                        ++count;
                    }
                    if(count > 0) {
                        Compare.resultCompareFiles.put(service1.hashCode(), ResultCompare.EQUAL);
                    }
                    else {
                        Compare.resultCompareFiles.put(service1.hashCode(), ResultCompare.NOTEQUAL);
                    }

                }

            }


            //json file #2
            for(JsonNode service2 : node2.get("services")) {
                int count = 0;

                //check mandatory fields services file 2
                Iterator<Map.Entry<String, JsonNode>> iterator2 = service2.fields();
                HashMap<String, JsonNode> fieldsNode2 = new HashMap<>();



                while(iterator2.hasNext()) {
                    Map.Entry<String, JsonNode> field = iterator2.next();
                    for(String mandatoryField : mandatoryFieldsServices) {
                        if(!mandatoryField.equals("sha1") && !mandatoryField.equals("sha256") && mandatoryField.equals(field.getKey())) {
                            fieldsNode2.put(field.getKey(), field.getValue());
                        }
                        else if(mandatoryField.equals("sha1") || mandatoryField.equals("sha256")) {
                            if(field.getValue().get(mandatoryField) != null) {
                                fieldsNode2.put(mandatoryField, field.getValue().get(mandatoryField));
                            }
                        }
                    }
                }


                for(JsonNode service1 : node1.get("services")) {
                    //check mandatory fields services file 1
                    HashMap<String, JsonNode> fieldsNode1 = new HashMap<>();
                    Iterator<Map.Entry<String, JsonNode>> iterator1 = service1.fields();

                    while(iterator1.hasNext()) {
                        Map.Entry<String, JsonNode> field = iterator1.next();
                        for(String mandatoryField : mandatoryFieldsServices) {
                            if(!mandatoryField.equals("sha1") && !mandatoryField.equals("sha256") && mandatoryField.equals(field.getKey())) {
                                fieldsNode1.put(field.getKey(), field.getValue());
                            }
                            else if(mandatoryField.equals("sha1") || mandatoryField.equals("sha256")) {
                                if(field.getValue().get(mandatoryField) != null) {
                                    fieldsNode1.put(mandatoryField, field.getValue().get(mandatoryField));
                                }
                            }
                        }
                    }

                    //check equal mandatory fields
                    if(fieldsNode2.equals(fieldsNode1)) {
                        ++count;
                    }
                    if(count > 0) {
                        int h2 = service2.hashCode();
                        Compare.resultCompareFiles.put(service2.hashCode(), ResultCompare.EQUAL);
                    }
                    else {
                        int h2 = service2.hashCode();
                        Compare.resultCompareFiles.put(service2.hashCode(), ResultCompare.NOTEQUAL);
                    }

                }

            }
        }
        optionalCompare1(node1, node2);
        optionalCompare2(node2, node1);

    }

    private void optionalCompare1(JsonNode node1, JsonNode node2) {
        for(JsonNode service1 : node1.get("services")) {
            HashMap<String, ResultCompare> compareOpt = new HashMap<>();

            for(JsonNode service2 : node2.get("services")) {

                for(String name : optionalFieldsServices) {

                    if(service1.get(name) != null && service2.get(name) != null && Compare.resultCompareFiles.get(service1.hashCode()) == ResultCompare.EQUAL
                            && Compare.resultCompareFiles.get(service2.hashCode()) == ResultCompare.EQUAL) {

                        if(service1.get(name).equals(service2.get(name)) && compareOpt.get(name) == null) {
                            compareOpt.put(name, ResultCompare.EQUAL);
                        }
                        else if(service1.get(name).equals(service2.get(name)) && compareOpt.get(name) != ResultCompare.EQUAL) {
                            compareOpt.put(name, ResultCompare.EQUAL);
                        }
                        else if(!service1.get(name).equals(service2.get(name)) && compareOpt.get(name) != ResultCompare.EQUAL) {
                            compareOpt.put(name, ResultCompare.NOTEQUAL);
                        }
                    }
                    else if(service1.get(name) == null) {
                        compareOpt.put(name, ResultCompare.NOTEQUAL);
                    }
                    else if(service2.get(name) == null) {
                        compareOpt.put(name, ResultCompare.NOTEQUAL);
                    }
                }

            }
            Compare.checkFieldsOptionalServices1.put(service1.hashCode(), compareOpt);
        }
    }

    private void optionalCompare2(JsonNode node2, JsonNode node1) {
        for(JsonNode service2 : node2.get("services")) {
            HashMap<String, ResultCompare> compareOpt = new HashMap<>();

            for(JsonNode service1 : node1.get("services")) {

                for(String name : optionalFieldsServices) {

                    if(service2.get(name) != null && service1.get(name) != null && Compare.resultCompareFiles.get(service2.hashCode()) == ResultCompare.EQUAL
                            && Compare.resultCompareFiles.get(service1.hashCode()) == ResultCompare.EQUAL) {

                        if(service2.get(name).equals(service1.get(name)) && compareOpt.get(name) == null) {
                            compareOpt.put(name, ResultCompare.EQUAL);
                        }
                        else if(service2.get(name).equals(service1.get(name)) && compareOpt.get(name) != ResultCompare.EQUAL) {
                            compareOpt.put(name, ResultCompare.EQUAL);
                        }
                        else if(!service2.get(name).equals(service1.get(name)) && compareOpt.get(name) != ResultCompare.EQUAL) {
                            compareOpt.put(name, ResultCompare.NOTEQUAL);
                        }
                    }
                    else if(service2.get(name) == null) {
                        compareOpt.put(name, ResultCompare.NOTEQUAL);
                    }
                    else if(service1.get(name) == null) {
                        compareOpt.put(name, ResultCompare.NOTEQUAL);
                    }
                }

            }
            Compare.checkFieldsOptionalServices2.put(service2.hashCode(), compareOpt);
        }
    }
}
