package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompareScript {
    public static ArrayList<String> mandatoryFieldsScript = new ArrayList<>();
    {
        mandatoryFieldsScript.add("script_name");
        mandatoryFieldsScript.add("hashes");
        mandatoryFieldsScript.add("sha1");
        mandatoryFieldsScript.add("sha256");
        mandatoryFieldsScript.add("url");
    }

    private ArrayList<String> optionalFieldsScript = new ArrayList<>();
    {
        optionalFieldsScript.add("service-short-name");
        optionalFieldsScript.add("start-point");
        optionalFieldsScript.add("end-point");
    }

    public void scriptCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("script").hashCode() == node2.get("script").hashCode()) {
            Compare.resultCompareFiles.put(node1.get("script").hashCode(), ResultCompare.EQUAL);

            for(JsonNode script : node1.get("script")) {
                Compare.resultCompareFiles.put(script.hashCode(), ResultCompare.EQUAL);
            }
        }
        else {
            Compare.resultCompareFiles.put(node1.get("script").hashCode(), ResultCompare.NOTEQUAL);
            Compare.resultCompareFiles.put(node2.get("script").hashCode(), ResultCompare.NOTEQUAL);

            compare(node1.get("script"), node2.get("script"));
            compare(node2.get("script"), node1.get("script"));
        }
        compareOptionalFields1(node1, node2);
        compareOptionalFields2(node2, node1);
    }

    private void compare(JsonNode node1, JsonNode node2) {

        for(JsonNode script1 : node1) {
            int count = 0;

            Iterator<Map.Entry<String, JsonNode>> iterator1 = script1.fields();
            HashMap<String, JsonNode> fieldsCompare1 = new HashMap<>();

            while(iterator1.hasNext()) {
                Map.Entry<String, JsonNode> field = iterator1.next();

                for(String mandatoryField : mandatoryFieldsScript) {
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

            for(JsonNode script2 : node2) {
                Iterator<Map.Entry<String, JsonNode>> iterator2 = script2.fields();
                HashMap<String, JsonNode> fieldsCompare2 = new HashMap<>();

                while(iterator2.hasNext()) {
                    Map.Entry<String, JsonNode> field = iterator2.next();

                    for(String mandatoryField : mandatoryFieldsScript) {
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
                    Compare.resultCompareFiles.put(script1.hashCode(), ResultCompare.EQUAL);
                }
                else {
                    Compare.resultCompareFiles.put(script1.hashCode(), ResultCompare.NOTEQUAL);
                }
            }
        }
    }

    //compare optional fields file #1
    public void compareOptionalFields1(JsonNode node1, JsonNode node2) {
        for(JsonNode script1 : node1.get("script")) {
            HashMap<String, ResultCompare> compareOpt = new HashMap<>();

            for(JsonNode script2 : node2.get("script")) {

                for(String fieldName : optionalFieldsScript) {
                    if(script1.get(fieldName) != null && script2.get(fieldName) != null &&
                            Compare.resultCompareFiles.get(script1.hashCode()) == ResultCompare.EQUAL && Compare.resultCompareFiles.get(script2.hashCode()) == ResultCompare.EQUAL) {

                        if(script1.get(fieldName).equals(script2.get(fieldName)) && compareOpt.get(fieldName) == null) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(script1.get(fieldName).equals(script2.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(!script1.get(fieldName).equals(script2.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.NOTEQUAL);
                        }
                    }
                }
            }
            Compare.checkFieldsOptionalScript1.put(script1.hashCode(), compareOpt);
        }
    }

    //compare optional fields file #2
    public void compareOptionalFields2(JsonNode node1, JsonNode node2) {
        for(JsonNode script2 : node1.get("script")) {
            HashMap<String, ResultCompare> compareOpt = new HashMap<>();

            for(JsonNode script1 : node2.get("script")) {

                for(String fieldName : optionalFieldsScript) {
                    if(script2.get(fieldName) != null && script1.get(fieldName) != null &&
                            Compare.resultCompareFiles.get(script2.hashCode()) == ResultCompare.EQUAL && Compare.resultCompareFiles.get(script1.hashCode()) == ResultCompare.EQUAL) {

                        if(script2.get(fieldName).equals(script1.get(fieldName)) && compareOpt.get(fieldName) == null) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(script2.get(fieldName).equals(script1.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(!script2.get(fieldName).equals(script1.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.NOTEQUAL);
                        }
                    }
                }
            }
            Compare.checkFieldsOptionalScript2.put(script2.hashCode(), compareOpt);
        }
    }

}
