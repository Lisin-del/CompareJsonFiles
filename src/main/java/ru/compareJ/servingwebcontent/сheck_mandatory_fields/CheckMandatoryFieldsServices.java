package ru.compareJ.servingwebcontent.сheck_mandatory_fields;

import com.fasterxml.jackson.databind.JsonNode;
import ru.compareJ.servingwebcontent.compare.Compare;
import ru.compareJ.servingwebcontent.ResultCompare;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckMandatoryFieldsServices {
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

    public void checkAvailabilityFields(JsonNode node1, JsonNode node2) {

        //#file1 services
        for(JsonNode topObj : node1.get("services")) {
            HashMap<String, ResultCompare> noFields1 = new HashMap<>();
            for(String field1 : fieldsServices) {
                if(!field1.equals("sha1") && !field1.equals("sha256") && topObj.get(field1) == null) {

                    noFields1.put(field1, ResultCompare.NOTEXIST);
                    Compare.checkFieldsMandatoryServices.put(topObj.hashCode(), noFields1);
                }
                else if(field1.equals("sha1") || field1.equals("sha256")) {
                    if(topObj.get("hashes") != null) {
                        if(topObj.get("hashes").get(field1) == null) {
                            noFields1.put(field1, ResultCompare.NOTEXIST);
                            Compare.checkFieldsMandatoryServices.put(topObj.hashCode(), noFields1);
                        }
                    }
                }
            }
        }


        //#file2 services
        for(JsonNode topObj : node2.get("services")) {
            HashMap<String, ResultCompare> noFields2 = new HashMap<>();
            for(String field2 : fieldsServices) {
                if(!field2.equals("sha1") && !field2.equals("sha256") && topObj.get(field2) == null) {
                    noFields2.put(field2, ResultCompare.NOTEXIST);
                    Compare.checkFieldsMandatoryServices.put(topObj.hashCode(), noFields2);
                }
                else if(field2.equals("sha1") || field2.equals("sha256")) {
                    if(topObj.get("hashes") != null) {
                        if(topObj.get("hashes").get(field2) == null) {
                            noFields2.put(field2, ResultCompare.NOTEXIST);
                            Compare.checkFieldsMandatoryServices.put(topObj.hashCode(), noFields2);
                        }
                    }
                }
            }
        }
    }
}
