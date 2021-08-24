package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class CheckFieldServices {
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

    public void checkAvailabilityFields(JsonNode node) {

    }
}
