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

                HashMap<String, JsonNode> fieldsNode2 = new HashMap<>();
                for(JsonNode service2 : node2.get("services")) {
                    //check mandatory fields services file 2
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

                }
                //check equal mandatory fields
                if(fieldsNode1.equals(fieldsNode2)) {
                    ++count;
                    compareOptionalFields(node1, node2);
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

                HashMap<String, JsonNode> fieldsNode1 = new HashMap<>();
                for(JsonNode service1 : node1.get("services")) {
                    //check mandatory fields services file 1
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

                }
                //check equal mandatory fields
                if(fieldsNode2.equals(fieldsNode1)) {
                    ++count;
                    compareOptionalFields(node1, node2);
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

    public void compareOptionalFields(JsonNode node1, JsonNode node2) {
        for(String field : optionalFieldsServices) {
            for(JsonNode service1 : node1.get("services")) {
                int count = 0;
                for(JsonNode service2 : node2.get("services")) {
                    if(service1.get(field) != null && service2.get(field) != null) {
                        if(service1.get(field).equals(service2.get(field))) {
                            ++count;
                        }
                    }
                }
                if(count > 0) {
                    Compare.checkFieldsOptional1.put(service1.get(field).hashCode(), ResultCompare.EQUAL);
                }
                else {
                    Compare.checkFieldsOptional1.put(service1.get(field).hashCode(), ResultCompare.NOTEQUAL);
                }
            }
        }


        for(String field : optionalFieldsServices) {
            for(JsonNode service2 : node1.get("services")) {
                int count = 0;
                for(JsonNode service1 : node2.get("services")) {
                    if(service2.get(field) != null && service1.get(field) != null) {
                        if(service2.get(field).equals(service1.get(field))) {
                            ++count;
                        }
                    }
                }
                if(count > 0) {
                    Compare.checkFieldsOptional2.put(service2.get(field).hashCode(), ResultCompare.EQUAL);
                }
                else {
                    Compare.checkFieldsOptional2.put(service2.get(field).hashCode(), ResultCompare.NOTEQUAL);
                }
            }
        }

        System.out.println("test1");
        for(Map.Entry<Integer, ResultCompare> map : Compare.checkFieldsOptional1.entrySet()) {
            System.out.println(map.getKey() + ":" + map.getValue());
        }

        System.out.println("test2");
        for(Map.Entry<Integer, ResultCompare> map : Compare.checkFieldsOptional2.entrySet()) {
            System.out.println(map.getKey() + ":" + map.getValue());
        }
    }

}
