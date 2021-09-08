package ru.compareJ.servingwebcontent;

import org.junit.*;


public class ValidatorJsonTest {

    @Test
    public void validationObjectJsonTest() {
        String line = "{\n" +
                "    \"metadata\": {\n" +
                "        \"description\": {\n" +
                "            \"version\": 2\n" +
                "        },\n" +
                "        \"application\": {\n" +
                "            \"name\": \"application_name\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"services\": [\n" +
                "\t\n" +
                "    ],\n" +
                "    \"artifacts\": [ \n" +
                "\n" +
                "    ],\n" +
                "    \"script\": [\n" +
                "\n" +
                "    ],\n" +
                "    \"parameters\": {\n" +
                "\n" +
                "    }\n" +
                "}\n";

        Node node = new Node();
        ValidatorJson validatorJson = new ValidatorJson();
        Assert.assertEquals(true, validatorJson.validationObjectJson(node.getJsonNode(line)));

    }

}
