package ru.compareJ.servingwebcontent;

import com.fasterxml.jackson.databind.JsonNode;

public class CompareFiles {
    private JsonNode nodeFile1;
    private JsonNode nodeFile2;

    public CompareFiles(JsonNode nodeFile1, JsonNode nodeFile2) {
        this.nodeFile1 = nodeFile1;
        this.nodeFile2 = nodeFile2;
    }
}
