package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Map;

public class CheckElementsServices {
    private String notExist = "notExist";

    //check ServicesTopObj
    public void checkServicesTopObj(JsonNode node1, JsonNode node2) {
        //node1 and node2
        int result = 0;
        int doneRes = 0;
        if(node1.get("services").get(0) != null && node2.get("services").get(0) != null) {

            if(node1.get("services").size() == node2.get("services").size()) {
                for(int i = 0; i < node1.get("services").size(); ++i) {
                    for(int j = 0; j < node2.get("services").size(); ++j) {

                    }
                }
            }
            else if(node1.get("services").size() > node2.get("services").size()) {
                result = node1.get("services").size() - node2.get("services").size();
                doneRes = node1.get("services").size() - result;
                for(int i = 0; i < doneRes; ++i) {
                    for(int j = 0; j < node2.get("services").size(); ++j) {

                    }
                }

            }
            else if(node1.get("services").size() < node2.get("services").size()) {
                result = node2.get("services").size() - node1.get("services").size();
                doneRes = node2.get("services").size() - result;
                for(int i = 0; i < doneRes; ++i) {
                    for(int j = 0; j < node1.get("services").size(); ++j) {

                    }
                }
            }
        }
        else {
            if(node1.get("services").get(0) == null) {
                Compare.infoElementsFiles.put("node1ServicesTopObj", notExist);
            }
            else if(node2.get("services").get(0) == null) {
                Compare.infoElementsFiles.put("node2ServicesTopObj", notExist);
            }
        }

    }

    //compare ServicesTopObjElements
    public void test(JsonNode node1, JsonNode node2) {
        Iterator<Map.Entry<String, JsonNode>> iterator1 = node1.get("services").get(0).fields();
        Iterator<Map.Entry<String, JsonNode>> iterator2 = node2.get("services").get(0).fields();
        while (iterator1.hasNext()) {
            Map.Entry<String, JsonNode> field = iterator1.next();
            System.out.println(field.getKey() + "-" + field.getValue());
        }

    }

    //compare ServicesTopObjFields
    public void compareServicesObjFields(int i, int j, JsonNode node1, JsonNode node2) {
        Iterator<Map.Entry<String, JsonNode>> iterator1 = node1.get(i).fields();
        Iterator<Map.Entry<String, JsonNode>> iterator2 = node2.get(j).fields();

        while(iterator1.hasNext()) {
            Map.Entry<String, JsonNode> field1 = iterator1.next();
            while(iterator2.hasNext()) {
                Map.Entry<String, JsonNode> field2 = iterator2.next();
                if(field1.getKey().equals(field2.getKey())) {
                    //придумать, как считать равные ключи, типа должны быть равны один раз Int = 1
                    //если не равно 1, то взять ключ из первого поля и записать, что такого ключа нет во втором поле
                }
                else {

                }
            }
        }
    }
}
