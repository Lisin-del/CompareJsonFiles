package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Iterator;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        ParserJson parser = new ParserJson();
        parser.read();

        Node node = new Node();
        JsonNode nodeFile1 = node.getJsonNode(parser.getResultStringFile1());
        JsonNode nodeFile2 = node.getJsonNode(parser.getResultStringFile2());

        ValidatorJson validatorJson = new ValidatorJson();

        CompareFiles compareFiles = new CompareFiles();


    }

}
