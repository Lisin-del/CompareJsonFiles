package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class App {
    public static JsonNode nodeFile1;
    public static JsonNode nodeFile2;
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);


        ParserJson parser = new ParserJson();
        parser.read();

        Node node = new Node();
        nodeFile1 = node.getJsonNode(parser.getResultStringFile1());
        nodeFile2 = node.getJsonNode(parser.getResultStringFile2());


        ValidatorJson validatorJson = new ValidatorJson();
        Compare compare = new Compare();

        if(validatorJson.validationObjectJson(nodeFile1) && validatorJson.validationObjectJson(nodeFile2)) {
            compare.compareJsonFile(nodeFile1, nodeFile2);
        }
        else {
            System.out.println("it is not work!");
        }


    }

}
