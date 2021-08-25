package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
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


        //#file1 services
        for(JsonNode nd1 : node1.get("services")) {
            for(String field1 : fieldsServices) {
                if(!field1.equals("sha1") && !field1.equals("sha256") && nd1.get(field1) == null) {
                    Compare.checkNoFields1.put(field1, ResultCompare.NOTEXIST);
                }
                else if(field1.equals("sha1") || field1.equals("sha256")) {
                    if(nd1.get("hashes") != null) {
                        if(nd1.get("hashes").get(field1) == null) {
                            Compare.checkNoFields1.put(field1, ResultCompare.NOTEXIST);
                        }
                    }
                }

            }
        }





        //#file2 services
        for(JsonNode nd2 : node2.get("services")) {
            for(String field2 : fieldsServices) {
                if(!field2.equals("sha1") && !field2.equals("sha256") && nd2.get(field2) == null) {
                    Compare.checkNoFields2.put(field2, ResultCompare.NOTEXIST);
                }
                else if(field2.equals("sha1") || field2.equals("sha256")) {
                    if(nd2.get("hashes") != null) {
                        if(nd2.get("hashes").get(field2) == null) {
                            Compare.checkNoFields2.put(field2, ResultCompare.NOTEXIST);
                        }
                    }
                }
            }
        }
    }


}
