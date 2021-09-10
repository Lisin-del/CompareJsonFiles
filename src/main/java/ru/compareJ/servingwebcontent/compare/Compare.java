package ru.compareJ.servingwebcontent.compare;

import com.fasterxml.jackson.databind.JsonNode;
import ru.compareJ.servingwebcontent.сheck_mandatory_fields.CheckMandatoryFieldsArt;
import ru.compareJ.servingwebcontent.сheck_mandatory_fields.CheckMandatoryFieldsRpm;
import ru.compareJ.servingwebcontent.сheck_mandatory_fields.CheckMandatoryFieldsScript;
import ru.compareJ.servingwebcontent.сheck_mandatory_fields.CheckMandatoryFieldsServices;
import ru.compareJ.servingwebcontent.ResultCompare;
import ru.compareJ.servingwebcontent.ValidatorJson;

import java.util.HashMap;
import java.util.Map;


public class Compare {
    //all result compare
    public static HashMap<Integer, ResultCompare> resultCompareFiles = new HashMap<>();

    //===SERVICES===
    //check the mandatory fields for the services
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsMandatoryServices = new HashMap<>();

    //check the optional fields for the services
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalServices1 = new HashMap<>();
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalServices2 = new HashMap<>();
    //===SERVICES END===

    //===ARTIFACTS===
    //check the mandatory fields for the mvn for the artifacts
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsMandatoryArtifacts = new HashMap<>();
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsMandatoryArtifactsMvn = new HashMap<>();

    //check the optional fields for the artifacts
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalArtifacts1 = new HashMap<>();
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalArtifacts2 = new HashMap<>();

    //check the optional fields for the artifacts
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalArtifactsMvn1 = new HashMap<>();
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalArtifactsMvn2 = new HashMap<>();
    //===ARTIFACTS END===

    //===SCRIPT===
    //check the mandatory fields for the script
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsMandatoryScript = new HashMap<>();

    //check the optional fields for the script
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalScript1 = new HashMap<>();
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalScript2 = new HashMap<>();
    //===SCRIPT END===

    //===RPM===
    //check the mandatory fields for the script
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsMandatoryRpm = new HashMap<>();

    //check the optional fields for the rpm
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalRpm1 = new HashMap<>();
    public static HashMap<Integer, HashMap<String, ResultCompare>> checkFieldsOptionalRpm2 = new HashMap<>();
    //===RPM END===


    private CompareMetadata compareMetadata = new CompareMetadata();
    private CompareServices compareServices = new CompareServices();
    private CompareArtifacts compareArtifacts = new CompareArtifacts();
    private CompareScript compareScript = new CompareScript();
    private CompareRpm compareRpm = new CompareRpm();
    private CompareParameters compareParam = new CompareParameters();

    private CheckMandatoryFieldsServices checkMandatoryFieldsServices = new CheckMandatoryFieldsServices();
    private CheckMandatoryFieldsArt checkMandatoryFieldsArt = new CheckMandatoryFieldsArt();
    private CheckMandatoryFieldsScript checkMandatoryFieldsScript = new CheckMandatoryFieldsScript();
    private CheckMandatoryFieldsRpm checkMandatoryFieldsRpm = new CheckMandatoryFieldsRpm();


    private JsonNode node1;
    private JsonNode node2;

    private ValidatorJson validatorJson = new ValidatorJson();

    public void compareFiles() {

        checkFieldsOptionalServices1.clear();
        checkFieldsOptionalServices2.clear();
        checkFieldsMandatoryServices.clear();

        checkFieldsMandatoryArtifacts.clear();
        checkFieldsMandatoryArtifactsMvn.clear();
        checkFieldsOptionalArtifacts1.clear();
        checkFieldsOptionalArtifacts2.clear();
        checkFieldsOptionalArtifactsMvn1.clear();
        checkFieldsOptionalArtifactsMvn2.clear();

        checkFieldsMandatoryRpm.clear();
        checkFieldsOptionalRpm1.clear();
        checkFieldsOptionalRpm2.clear();

        resultCompareFiles.clear();

        if(validatorJson.validationObjectJson(node1) & validatorJson.validationObjectJson(node2)) {
            if(node1.hashCode() == node2.hashCode()) {
                int hash = node1.hashCode();

                checkMandatoryFieldsServices.checkAvailabilityFields(node1, node2);
                resultCompareFiles.put(hash, ResultCompare.EQUAL);
            }
            else {
                resultCompareFiles.put(node1.hashCode(), ResultCompare.NOTEQUAL);
                resultCompareFiles.put(node2.hashCode(), ResultCompare.NOTEQUAL);

                //compare metadata
                compareMetadata.metadataCompare(node1, node2);

                //compare services
                checkMandatoryFieldsServices.checkAvailabilityFields(node1, node2);
                compareServices.servicesCompare(node1, node2);

                //compare artifacts
                checkMandatoryFieldsArt.checkAvailabilityFieldsArtifacts(node1, node2);
                compareArtifacts.artifactsCompare(node1, node2);

                //compare script
                checkMandatoryFieldsScript.checkAvailabilityFieldsScript(node1, node2);
                compareScript.scriptCompare(node1, node2);

                //compare rpm
                checkMandatoryFieldsRpm.checkAvailabilityFieldsRpm(node1, node2);
                compareRpm.rpmCompare(node1, node2);

                //compare parameters
                compareParam.parametersCompare(node1, node2);
            }
        }
        else {
            resultCompareFiles.put(1, ResultCompare.WRONGSTRUCTURE);
        }

        for(Map.Entry<Integer, ResultCompare> res : resultCompareFiles.entrySet()) {
            System.out.println(res.getKey() + ":" + res.getValue());
        }
    }






    public JsonNode getNode1() {
        return node1;
    }

    public void setNode1(JsonNode node1) {
        this.node1 = node1;
    }

    public JsonNode getNode2() {
        return node2;
    }

    public void setNode2(JsonNode node2) {
        this.node2 = node2;
    }
}
