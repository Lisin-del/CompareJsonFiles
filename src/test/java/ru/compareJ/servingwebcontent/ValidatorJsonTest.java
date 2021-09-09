package ru.compareJ.servingwebcontent;

import org.junit.*;

import java.io.*;


public class ValidatorJsonTest {

    @Test
    public void validationObjectJsonTest() {
        File jsonTestFile = new File("./src/main/resources/uploadFilesTest/v2_json_sample1.json");
        StringBuilder resultLine = new StringBuilder();
        String line;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonTestFile));
            while((line = bufferedReader.readLine()) != null) {
                resultLine.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node node = new Node();
        ValidatorJson validatorJson = new ValidatorJson();
        Assert.assertEquals(true, validatorJson.validationObjectJson(node.getJsonNode(resultLine.toString())));

    }

}
