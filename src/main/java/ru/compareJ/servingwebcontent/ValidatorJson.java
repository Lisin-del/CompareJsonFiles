package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import ru.compareJ.servingwebcontent.compare.Compare;

import java.util.*;

public class ValidatorJson {

    public boolean validationObjectJson(JsonNode node) {
        int j = 0;

        try {
            if(node.get("metadata").get("description").get("version").asInt() == 2 && node.get("metadata").get("application").get("name") != null &&
                    node.get("metadata").get("application").get("name").getNodeType().equals(JsonNodeType.STRING)) {
                ++j;
            }
            else {
                if(node.get("metadata").get("description").get("version").asInt() != 2) {
                    if(Compare.infoStructure.get(node.hashCode()) != null) {
                        Compare.infoStructure.put(node.hashCode(), Compare.infoStructure.get(node.hashCode()) + " " + "Version: wrong value!");
                    }
                    else{
                        Compare.infoStructure.put(node.hashCode(), "Version: wrong value!");
                    }
                }
                if(node.get("metadata").get("application").get("name") == null) {
                    if(Compare.infoStructure.get(node.hashCode()) != null) {
                        Compare.infoStructure.put(node.hashCode(), Compare.infoStructure.get(node.hashCode()) + " " + "Name: null!");
                    }
                    else{
                        Compare.infoStructure.put(node.hashCode(), "Name: null!");
                    }
                }
                else if(!node.get("metadata").get("application").get("name").getNodeType().equals(JsonNodeType.STRING)) {
                    if(Compare.infoStructure.get(node.hashCode()) != null) {
                        Compare.infoStructure.put(node.hashCode(), Compare.infoStructure.get(node.hashCode()) + " " + "Name: wrong type!");
                    }
                    else{
                        Compare.infoStructure.put(node.hashCode(), "Name: wrong type!");
                    }
                }
            }

            if(node.get("services") != null) {
                ++j;
            }
            else {
                if(Compare.infoStructure.get(node.hashCode()) != null) {
                    Compare.infoStructure.put(node.hashCode(), Compare.infoStructure.get(node.hashCode()) + " " + "Services: null!");
                }
                else{
                    Compare.infoStructure.put(node.hashCode(), "Services: null!");
                }
            }

            if(node.get("artifacts") != null) {
                ++j;
            }
            else {
                if(Compare.infoStructure.get(node.hashCode()) != null) {
                    Compare.infoStructure.put(node.hashCode(), Compare.infoStructure.get(node.hashCode()) + " " + "Artifacts: null!");
                }
                else{
                    Compare.infoStructure.put(node.hashCode(), "Artifacts: null!");
                }
            }

            if(node.get("script") != null) {
                ++j;
            }
            else {
                if(Compare.infoStructure.get(node.hashCode()) != null) {
                    Compare.infoStructure.put(node.hashCode(), Compare.infoStructure.get(node.hashCode()) + " " + "Script: null!");
                }
                else{
                    Compare.infoStructure.put(node.hashCode(), "Script: null!");
                }
            }

            if(node.get("parameters") != null) {
                ++j;
            }
            else {
                if(Compare.infoStructure.get(node.hashCode()) != null) {
                    Compare.infoStructure.put(node.hashCode(), Compare.infoStructure.get(node.hashCode()) + " " + "Parameters: null!");
                }
                else{
                    Compare.infoStructure.put(node.hashCode(), "Parameters: null!");
                }
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
