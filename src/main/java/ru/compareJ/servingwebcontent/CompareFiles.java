package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;

public class CompareFiles {
    private HashMap<String, Boolean> check = new HashMap<String, Boolean>();




    public void checkFields(boolean valid, JsonNode nodeFile) {
        for(JsonNode it : nodeFile.get("services")) {
            if(it != null) {
                check.put("services", true);
            }
            else {
                check.put("services", false);
            }

            if(it.get("service-short-name") != null) {
                check.put("service-short-name", true);
            }
        }
    }

}
