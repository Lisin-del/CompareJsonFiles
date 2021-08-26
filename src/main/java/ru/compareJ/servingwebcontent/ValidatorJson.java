package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.util.*;

public class ValidatorJson {

    public boolean validationObjectJson(JsonNode node) {
        int j = 0;

        try {
            if(node.get("metadata").get("description").get("version").asInt() == 2 && node.get("metadata").get("application").get("name") != null &&
                    node.get("metadata").get("application").get("name").getNodeType().equals(JsonNodeType.STRING)) {
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
        }
        catch(NullPointerException exception) {
            exception.printStackTrace();
            j = 0;
        }

        return j == 5;
    }

    public boolean validCheck(boolean valid1, boolean valid2) {
        return valid1 && valid2;
    }



}
