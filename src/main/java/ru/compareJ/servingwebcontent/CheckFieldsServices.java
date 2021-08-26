package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CheckFieldsServices {
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
        //создать мапу HashMap<Integer, HashMap<String, ResultCompare>>
        //проводим проверку на отсутствие полей в топовом объекте, если там отстутсвует обязательно поле, обрабатываем это
        //записываем в мапу хэш этого объекта и результаты проверки
        HashMap<String, ResultCompare> noFields = new HashMap<>();

        //#file1 services
        for(JsonNode topObj : node1.get("services")) {
            for(String field1 : fieldsServices) {
                if(!field1.equals("sha1") && !field1.equals("sha256") && topObj.get(field1) == null) {

                    noFields.put(field1, ResultCompare.NOTEXIST);
                    Compare.checkNoFields.put(topObj.hashCode(), noFields);
                }
                else if(field1.equals("sha1") || field1.equals("sha256")) {
                    if(topObj.get("hashes") != null) {
                        if(topObj.get("hashes").get(field1) == null) {
                            noFields.put(field1, ResultCompare.NOTEXIST);
                            Compare.checkNoFields.put(topObj.hashCode(), noFields);
                        }
                    }
                }

            }
        }





        //#file2 services
        for(JsonNode nd2 : node2.get("services")) {
            for(String field2 : fieldsServices) {
                if(!field2.equals("sha1") && !field2.equals("sha256") && nd2.get(field2) == null) {

                }
                else if(field2.equals("sha1") || field2.equals("sha256")) {
                    if(nd2.get("hashes") != null) {
                        if(nd2.get("hashes").get(field2) == null) {

                        }
                    }
                }
            }
        }
    }


}
