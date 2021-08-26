package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompareServices {

    private ArrayList<String> mandatoryFieldsServices = new ArrayList<>();
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
                int countMandatory1 = 0;
                int countMandatory2 = 0;

                //test code
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
                    //test code
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

                    if(fieldsNode1.equals(fieldsNode2)) {
                        if(service1.hashCode() == service2.hashCode()) {
                            ++count;
                        }
                        else {
                            for(String optionalField : optionalFieldsServices) {
                                //optional fields compare
                                if(service1.get(optionalField).getNodeType() == service2.get(optionalField).getNodeType()) {
                                    if(service1.get(optionalField).hashCode() == service2.get(optionalField).hashCode()) {
                                        Compare.resultCompareFiles.put(service1.get(optionalField).hashCode(), ResultCompare.EQUAL);
                                    }
                                    else {
                                        Compare.resultCompareFiles.put(service1.get(optionalField).hashCode(), ResultCompare.NOTEQUAL);
                                        Compare.resultCompareFiles.put(service2.get(optionalField).hashCode(), ResultCompare.NOTEQUAL);
                                    }
                                }
                                else {
                                    Compare.resultCompareFiles.put(service1.get(optionalField).hashCode(), ResultCompare.WRONGTYPE);
                                    Compare.resultCompareFiles.put(service2.get(optionalField).hashCode(), ResultCompare.WRONGTYPE);
                                }


                            }


                        }
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
