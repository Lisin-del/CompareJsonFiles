package ru.compareJ.servingwebcontent.CheckMandatoryFields;

import com.fasterxml.jackson.databind.JsonNode;
import ru.compareJ.servingwebcontent.Compare.Compare;
import ru.compareJ.servingwebcontent.ResultCompare;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckMandatoryFieldsRpm {
    private ArrayList<String> mandatoryFieldsRpm = new ArrayList<>();
    {
        mandatoryFieldsRpm.add("url");
        mandatoryFieldsRpm.add("rpm_repository_name");
        mandatoryFieldsRpm.add("hashes");
        mandatoryFieldsRpm.add("sha1");
        mandatoryFieldsRpm.add("sha256");
    }

    public void checkAvailabilityFieldsRpm(JsonNode node1, JsonNode node2) {
        //file #1 script
        if(node1.get("rpm") != null && node2.get("rpm") != null) {

            for(JsonNode rpm1 : node1.get("rpm")) {
                HashMap<String, ResultCompare> noFields1 = new HashMap<>();

                for(String fieldName1 : mandatoryFieldsRpm) {
                    if(!fieldName1.equals("sha1") && !fieldName1.equals("sha256") && rpm1.get(fieldName1) == null) {
                        noFields1.put(fieldName1, ResultCompare.NOTEXIST);
                        Compare.checkFieldsMandatoryRpm.put(rpm1.hashCode(), noFields1);
                    }
                    else if(fieldName1.equals("sha1") || fieldName1.equals("sha256")) {
                        if(rpm1.get("hashes") != null) {
                            if(rpm1.get("hashes").get(fieldName1) == null) {
                                noFields1.put(fieldName1, ResultCompare.NOTEXIST);
                                Compare.checkFieldsMandatoryRpm.put(rpm1.hashCode(), noFields1);
                            }
                        }
                    }
                }
            }

            //file #2 script
            for(JsonNode rpm2 : node2.get("rpm")) {
                HashMap<String, ResultCompare> noFields2 = new HashMap<>();

                for(String fieldName2 : mandatoryFieldsRpm) {
                    if(!fieldName2.equals("sha1") && !fieldName2.equals("sha256") && rpm2.get(fieldName2) == null) {
                        noFields2.put(fieldName2, ResultCompare.NOTEXIST);
                        Compare.checkFieldsMandatoryRpm.put(rpm2.hashCode(), noFields2);
                    }
                    else if(fieldName2.equals("sha1") || fieldName2.equals("sha256")) {
                        if(rpm2.get("hashes") != null) {
                            if(rpm2.get("hashes").get(fieldName2) == null) {
                                noFields2.put(fieldName2, ResultCompare.NOTEXIST);
                                Compare.checkFieldsMandatoryRpm.put(rpm2.hashCode(), noFields2);
                            }
                        }
                    }
                }
            }
        }
    }
}
