package ru.compareJ.servingwebcontent.compare;

import com.fasterxml.jackson.databind.JsonNode;
import ru.compareJ.servingwebcontent.ResultCompare;

import java.util.Iterator;
import java.util.Map;

public class CompareParameters {

    public void parametersCompare(JsonNode node1, JsonNode node2) {
        if(node1.get("parameters").hashCode() == node2.get("parameters").hashCode()) {
            Compare.resultCompareFiles.put(node1.get("parameters").hashCode(), ResultCompare.EQUAL);
        }
        else {
            Compare.resultCompareFiles.put(node1.get("parameters").hashCode(), ResultCompare.NOT_EQUAL);
            Compare.resultCompareFiles.put(node2.get("parameters").hashCode(), ResultCompare.NOT_EQUAL);

            compare(node1, node2);
        }

    }

    private void compare(JsonNode node1, JsonNode node2) {

        if(node1.get("parameters").get("common") != null && node2.get("parameters").get("common") != null) {
            if(node1.get("parameters").get("common").hashCode() == node2.get("parameters").get("common").hashCode()) {
                Compare.resultCompareFiles.put(node1.get("parameters").get("common").hashCode(), ResultCompare.EQUAL);
            }
            else {
                Compare.resultCompareFiles.put(node1.get("parameters").get("common").hashCode(), ResultCompare.NOT_EQUAL);
                Compare.resultCompareFiles.put(node2.get("parameters").get("common").hashCode(), ResultCompare.NOT_EQUAL);

                Iterator<Map.Entry<String, JsonNode>> iterator = node1.get("parameters").get("common").fields();

                while(iterator.hasNext()) {
                    Map.Entry<String, JsonNode> field = iterator.next();

                    if(node2.get("parameters").get("common").get(field.getKey()) != null) {
                        if(field.getValue().hashCode() == node2.get("parameters").get("common").get(field.getKey()).hashCode()) {
                            Compare.resultCompareFiles.put(field.getValue().hashCode(), ResultCompare.EQUAL);
                        }
                        else {
                            Compare.resultCompareFiles.put(field.getValue().hashCode(), ResultCompare.NOT_EQUAL);
                            Compare.resultCompareFiles.put(node2.get("parameters").get("common").get(field.getKey()).hashCode(), ResultCompare.NOT_EQUAL);
                        }
                    }
                }
            }
        }

        if(node1.get("parameters").get("services") != null && node2.get("parameters").get("services") != null) {
            if(node1.get("parameters").get("services").hashCode() == node2.get("parameters").get("services").hashCode()) {
                Compare.resultCompareFiles.put(node1.get("parameters").get("services").hashCode(), ResultCompare.EQUAL);
            }
            else {
                Compare.resultCompareFiles.put(node1.get("parameters").get("services").hashCode(), ResultCompare.NOT_EQUAL);
                Compare.resultCompareFiles.put(node2.get("parameters").get("services").hashCode(), ResultCompare.NOT_EQUAL);

                Iterator<Map.Entry<String, JsonNode>> iterator = node1.get("parameters").get("services").fields();

                while(iterator.hasNext()) {
                    Map.Entry<String, JsonNode> service = iterator.next();

                    if(node2.get("parameters").get("services").get(service.getKey()) != null) {
                        if(service.getValue().hashCode() == node2.get("parameters").get("services").get(service.getKey()).hashCode()) {
                            Compare.resultCompareFiles.put(service.getValue().hashCode(), ResultCompare.EQUAL);
                        }
                        else {
                            Compare.resultCompareFiles.put(service.getValue().hashCode(), ResultCompare.NOT_EQUAL);
                            Compare.resultCompareFiles.put(node2.get("parameters").get("services").get(service.getKey()).hashCode(), ResultCompare.NOT_EQUAL);

                            Iterator<Map.Entry<String, JsonNode>> iteratorServices = service.getValue().fields();

                            while(iteratorServices.hasNext()) {
                                Map.Entry<String, JsonNode> fieldServiceName = iteratorServices.next();

                                if(node2.get("parameters").get("services").get(service.getKey()).get(fieldServiceName.getKey()) != null) {
                                    if(fieldServiceName.getValue().hashCode() == node2.get("parameters").get("services").get(service.getKey()).get(fieldServiceName.getKey()).hashCode()) {
                                        Compare.resultCompareFiles.put(fieldServiceName.getValue().hashCode(), ResultCompare.EQUAL);
                                    }
                                    else {
                                        Compare.resultCompareFiles.put(fieldServiceName.getValue().hashCode(), ResultCompare.NOT_EQUAL);
                                        Compare.resultCompareFiles.put(node2.get("parameters").get("services").get(service.getKey()).get(fieldServiceName.getKey()).hashCode(), ResultCompare.NOT_EQUAL);
                                    }
                                }
                            }
                        }
                    }
                }
            }


        }
    }
}
