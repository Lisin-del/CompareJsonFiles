package ru.compareJ.servingwebcontent.сheck_mandatory_fields;

import com.fasterxml.jackson.databind.JsonNode;
import ru.compareJ.servingwebcontent.compare.Compare;
import ru.compareJ.servingwebcontent.ResultCompare;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckMandatoryFieldsScript {
    private ArrayList<String> mandatoryFieldsScript = new ArrayList<>();
    {
        mandatoryFieldsScript.add("script_name");
        mandatoryFieldsScript.add("hashes");
        mandatoryFieldsScript.add("sha1");
        mandatoryFieldsScript.add("sha256");
        mandatoryFieldsScript.add("url");
    }

    public void checkAvailabilityFieldsScript(JsonNode node1, JsonNode node2) {
        //file #1 script
        for(JsonNode script : node1.get("script")) {
            HashMap<String, ResultCompare> noFields1 = new HashMap<>();

            for(String fieldName1 : mandatoryFieldsScript) {
                if(!fieldName1.equals("sha1") && !fieldName1.equals("sha256") && script.get(fieldName1) == null) {
                    noFields1.put(fieldName1, ResultCompare.NOT_EXIST);
                    Compare.checkFieldsMandatoryScript.put(script.hashCode(), noFields1);
                }
                else if(fieldName1.equals("sha1") || fieldName1.equals("sha256")) {
                    if(script.get("hashes") != null) {
                        if(script.get("hashes").get(fieldName1) == null) {
                            noFields1.put(fieldName1, ResultCompare.NOT_EXIST);
                            Compare.checkFieldsMandatoryScript.put(script.hashCode(), noFields1);
                        }
                    }
                }
            }
        }

        //file #2 script
        for(JsonNode script : node2.get("script")) {
            HashMap<String, ResultCompare> noFields2 = new HashMap<>();

            for(String fieldName2 : mandatoryFieldsScript) {
                if(!fieldName2.equals("sha1") && !fieldName2.equals("sha256") && script.get(fieldName2) == null) {
                    noFields2.put(fieldName2, ResultCompare.NOT_EXIST);
                    Compare.checkFieldsMandatoryScript.put(script.hashCode(), noFields2);
                }
                else if(fieldName2.equals("sha1") || fieldName2.equals("sha256")) {
                    if(script.get("hashes") != null) {
                        if(script.get("hashes").get(fieldName2) == null) {
                            noFields2.put(fieldName2, ResultCompare.NOT_EXIST);
                            Compare.checkFieldsMandatoryScript.put(script.hashCode(), noFields2);
                        }
                    }
                }
            }
        }
    }

}
