package ru.compareJ.servingwebcontent.compare;

import com.fasterxml.jackson.databind.JsonNode;
import ru.compareJ.servingwebcontent.ResultCompare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompareRpm {
    public static ArrayList<String> mandatoryFieldsRpm = new ArrayList<>();
    {
        mandatoryFieldsRpm.add("url");
        mandatoryFieldsRpm.add("rpm_repository_name");
        mandatoryFieldsRpm.add("hashes");
        mandatoryFieldsRpm.add("sha1");
        mandatoryFieldsRpm.add("sha256");
    }

    private ArrayList<String> optionalFieldsRpm = new ArrayList<>();
    {
        optionalFieldsRpm.add("service-short-name");
    }

    public void rpmCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("rpm") != null && node2.get("rpm") != null) {
            if(node1.get("rpm").hashCode() == node2.get("rpm").hashCode()) {
                Compare.resultCompareFiles.put(node1.get("rpm").hashCode(), ResultCompare.EQUAL);

                for(JsonNode rpm : node1.get("rpm")) {
                    Compare.resultCompareFiles.put(rpm.hashCode(), ResultCompare.EQUAL);
                }
            }
            else {
                Compare.resultCompareFiles.put(node1.get("rpm").hashCode(), ResultCompare.NOTEQUAL);
                Compare.resultCompareFiles.put(node2.get("rpm").hashCode(), ResultCompare.NOTEQUAL);

                compare(node1.get("rpm"), node2.get("rpm"));
                compare(node2.get("rpm"), node1.get("rpm"));
            }
            compareOptionalFields1(node1, node2);
            compareOptionalFields2(node2, node1);
        }

    }

    private void compare(JsonNode node1, JsonNode node2) {
        for(JsonNode rpm1 : node1) {
            int count = 0;

            Iterator<Map.Entry<String, JsonNode>> iterator1 = rpm1.fields();
            HashMap<String, JsonNode> fieldsCompare1 = new HashMap<>();
            while(iterator1.hasNext()) {
                Map.Entry<String, JsonNode> field = iterator1.next();

                for(String mandatoryField : mandatoryFieldsRpm) {
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

            for(JsonNode rpm2 : node2) {
                Iterator<Map.Entry<String, JsonNode>> iterator2 = rpm2.fields();
                HashMap<String, JsonNode> fieldsCompare2 = new HashMap<>();

                while(iterator2.hasNext()) {
                    Map.Entry<String, JsonNode> field = iterator2.next();

                    for(String mandatoryField : mandatoryFieldsRpm) {
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
                    Compare.resultCompareFiles.put(rpm1.hashCode(), ResultCompare.EQUAL);
                }
                else {
                    Compare.resultCompareFiles.put(rpm1.hashCode(), ResultCompare.NOTEQUAL);
                }
            }
        }
    }

    //compare optional fields file #1
    public void compareOptionalFields1(JsonNode node1, JsonNode node2) {
        for(JsonNode rpm1 : node1.get("rpm")) {
            HashMap<String, ResultCompare> compareOpt = new HashMap<>();

            for(JsonNode rpm2 : node2.get("rpm")) {

                for(String fieldName : optionalFieldsRpm) {
                    if(rpm1.get(fieldName) != null && rpm2.get(fieldName) != null &&
                            Compare.resultCompareFiles.get(rpm1.hashCode()) == ResultCompare.EQUAL && Compare.resultCompareFiles.get(rpm2.hashCode()) == ResultCompare.EQUAL) {

                        if(rpm1.get(fieldName).equals(rpm2.get(fieldName)) && compareOpt.get(fieldName) == null) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(rpm1.get(fieldName).equals(rpm2.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(!rpm1.get(fieldName).equals(rpm2.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.NOTEQUAL);
                        }
                    }
                }
            }
            Compare.checkFieldsOptionalRpm1.put(rpm1.hashCode(), compareOpt);
        }
    }

    //compare optional fields file #2
    public void compareOptionalFields2(JsonNode node1, JsonNode node2) {
        for(JsonNode rpm2 : node1.get("rpm")) {
            HashMap<String, ResultCompare> compareOpt = new HashMap<>();

            for(JsonNode rpm1 : node2.get("rpm")) {

                for(String fieldName : optionalFieldsRpm) {
                    if(rpm2.get(fieldName) != null && rpm1.get(fieldName) != null &&
                            Compare.resultCompareFiles.get(rpm2.hashCode()) == ResultCompare.EQUAL && Compare.resultCompareFiles.get(rpm1.hashCode()) == ResultCompare.EQUAL) {

                        if(rpm2.get(fieldName).equals(rpm1.get(fieldName)) && compareOpt.get(fieldName) == null) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(rpm2.get(fieldName).equals(rpm1.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.EQUAL);
                        }
                        else if(!rpm2.get(fieldName).equals(rpm1.get(fieldName)) && compareOpt.get(fieldName) != ResultCompare.EQUAL) {
                            compareOpt.put(fieldName, ResultCompare.NOTEQUAL);
                        }
                    }
                }
            }
            Compare.checkFieldsOptionalRpm2.put(rpm2.hashCode(), compareOpt);
        }
    }
}
