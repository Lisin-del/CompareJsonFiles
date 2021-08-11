package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

public class ValidatorJson {

    public boolean validationObjectJson(JsonNode node) {
        int j = 0;
        if(node.get("metadata").get("description").get("version").asInt() == 2 && node.get("metadata").get("application").get("name").asText().equals("application_name")) {
            ++j;
        }
        if(node.get("services") != null) {
            ++j;
        }
        if(node.get("artifacts") != null) {
            ++j;
        }
        if(node.get("script") != null) {
            ++j;
        }
        if(node.get("parameters") != null) {
            ++j;
        }
        return j == 5;
    }

    public boolean validCheck(boolean valid1, boolean valid2) {
        return valid1 && valid2;
    }



}
