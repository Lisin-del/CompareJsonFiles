package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompareServices {

    private ArrayList<String> fieldsServices = new ArrayList<>();
    {
        fieldsServices.add("service_name");
        fieldsServices.add("artifact_type");
        fieldsServices.add("docker_registry");
        fieldsServices.add("docker_image_name");
        fieldsServices.add("docker_tag");
        fieldsServices.add("hashes");
        fieldsServices.add("sha1");
        fieldsServices.add("sha256");
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
                int countMandatory1 = 0;
                int countMandatory2 = 0;

                for(JsonNode service2 : node2.get("services")) {
                    //check mandatory fields json file #1
                    Iterator<Map.Entry<String, JsonNode>> iterator1 = service1.fields();
                    HashMap<String, JsonNode> mandatoryFields1 = new HashMap<>();

                    while(iterator1.hasNext()) {
                        Map.Entry<String, JsonNode> field = iterator1.next();
                        for(String mandatoryField : fieldsServices) {
                            if(!mandatoryField.equals("sha1") && !mandatoryField.equals("sha256") && field.getKey().equals(mandatoryField)) {
                                mandatoryFields1.put(field.getKey(), field.getValue());
                            }
                            else if(mandatoryField.equals("sha1") || mandatoryField.equals("sha256")) {
                                if(field.getKey().equals("hashes")) {
                                    if(field.getValue().get(mandatoryField) != null) {
                                        mandatoryFields1.put(mandatoryField, field.getValue().get(mandatoryField));
                                    }
                                }
                            }

                        }
                    }

                    //check mandatory fields json file #2
                    Iterator<Map.Entry<String, JsonNode>> iterator2 = service2.fields();
                    HashMap<String, JsonNode> mandatoryFields2 = new HashMap<>();

                    while(iterator2.hasNext()) {
                        Map.Entry<String, JsonNode> field = iterator2.next();
                        for(String mandatoryField : fieldsServices) {
                            if(!mandatoryField.equals("sha1") && !mandatoryField.equals("sha256") && field.getKey().equals(mandatoryField)) {
                                mandatoryFields2.put(field.getKey(), field.getValue());
                            }
                            else if(mandatoryField.equals("sha1") || mandatoryField.equals("sha256")) {
                                if(field.getKey().equals("hashes")) {
                                    if(field.getValue().get(mandatoryField) != null) {
                                        mandatoryFields2.put(mandatoryField, field.getValue().get(mandatoryField));
                                    }
                                }
                            }

                        }
                    }

                    if(mandatoryFields1.size() == mandatoryFields2.size()) {
                        System.out.println("It is working!");
                        mandatoryFields1.clear();
                        mandatoryFields2.clear();
                    }


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
