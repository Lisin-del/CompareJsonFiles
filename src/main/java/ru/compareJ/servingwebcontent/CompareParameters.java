package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompareParameters {
    public static ArrayList<String> mandatoryFieldsParam = new ArrayList<>();
    {
        mandatoryFieldsParam.add("some-param");
        mandatoryFieldsParam.add("service_name_1");
        mandatoryFieldsParam.add("some-third-param");
        mandatoryFieldsParam.add("service_name_2");
        mandatoryFieldsParam.add("some-third-param-2");

    }

    private ArrayList<String> optionalFieldsParam = new ArrayList<>();
    {
        optionalFieldsParam.add("common");
        optionalFieldsParam.add("services");
    }

    public void parametersCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("parameters").hashCode() == node2.get("parameters").hashCode()) {
            Compare.resultCompareFiles.put(node1.get("parameters").hashCode(), ResultCompare.EQUAL);
        }
        else {
            Compare.resultCompareFiles.put(node1.get("parameters").hashCode(), ResultCompare.NOTEQUAL);
            Compare.resultCompareFiles.put(node2.get("parameters").hashCode(), ResultCompare.NOTEQUAL);

            compare(node1, node2);

        }

    }

    private void compare(JsonNode node1, JsonNode node2) {
        //json file #1
        Iterator<Map.Entry<String, JsonNode>> iterator1 = node1.get("parameters").fields();
        HashMap<String, JsonNode> fieldsCompare1 = new HashMap<>();

        while(iterator1.hasNext()) {
            Map.Entry<String, JsonNode> fieldOptional = iterator1.next();

            for(String optionalField : optionalFieldsParam) {
                if(fieldOptional.getKey().equals(optionalField)) {
                    Iterator<Map.Entry<String, JsonNode>> iterator2 = fieldOptional.getValue().fields();

                    while(iterator2.hasNext()) {
                        Map.Entry<String, JsonNode> fieldMandatory = iterator2.next();

                        for(String mandatoryField : mandatoryFieldsParam) {
                            if(fieldMandatory.getKey().equals(mandatoryField)) {
                                fieldsCompare1.put(fieldMandatory.getKey(), fieldMandatory.getValue());
                            }
                        }
                    }
                }
            }
        }

        //json file #2
        Iterator<Map.Entry<String, JsonNode>> iterator2 = node2.get("parameters").fields();
        HashMap<String, JsonNode> fieldsCompare2 = new HashMap<>();

        while(iterator1.hasNext()) {
            Map.Entry<String, JsonNode> fieldOptional = iterator2.next();

            for(String optionalField : optionalFieldsParam) {
                if(fieldOptional.getKey().equals(optionalField)) {
                    Iterator<Map.Entry<String, JsonNode>> iterator3 = fieldOptional.getValue().fields();

                    while(iterator2.hasNext()) {
                        Map.Entry<String, JsonNode> fieldMandatory = iterator3.next();

                        for(String mandatoryField : mandatoryFieldsParam) {
                            if(fieldMandatory.getKey().equals(mandatoryField)) {
                                fieldsCompare2.put(fieldMandatory.getKey(), fieldMandatory.getValue());
                            }
                        }
                    }
                }
            }
        }

        if(fieldsCompare1.equals(fieldsCompare2)) {
            int i = 0;
        }
        else {
            int i = 1;
        }
    }
}
