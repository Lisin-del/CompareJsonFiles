package ru.compareJ.servingwebcontent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.compareJ.servingwebcontent.compare.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class MainController {
    Compare compareFiles = new Compare();

    //home page
    @GetMapping("/")
    public String greeting(Model model) {
        deleteAllFilesFolder("./src/main/resources/uploadFiles/");
        model.addAttribute("title", "Home page");
        return "homepage";
    }


    @Value("${multipart.location}")
    private String uploadPath;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");


    /**
     * Загрузка нескольких файлов
     * @param uploadFiles многофайловый массив
     * @param req
     * @return многофайловая ссылка
     */


    @PostMapping("/uploads")
    public String uploads(MultipartFile[] uploadFiles, HttpServletRequest req, Model model){

        if (uploadFiles.length > 0){
            for (MultipartFile file : uploadFiles){

                MultipartFile uploadFile = file;
                File folder = new File(uploadPath);

                if (!folder.isDirectory()){ // Если текущий каталог не существует
                    folder.mkdirs(); // Создать новый каталог
                }
                String oldName = uploadFile.getOriginalFilename(); // Старое имя

                try {
                    String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length()); //Новое имя
                    uploadFile.transferTo(new File(folder,newName)); //загрузить файлы
                } catch (IOException e) {
                    e.printStackTrace();
                } catch(StringIndexOutOfBoundsException exception) {
                    exception.printStackTrace();
                }
            }

        }else if (uploadFiles.length == 0){
            return "Пожалуйста, выберите файл!";
        }

        ParserJson parserJson = new ParserJson();
        parserJson.read();

        Node node = new Node();

        compareFiles.setNode1(node.getJsonNode(parserJson.getResultStringFile1()));
        compareFiles.setNode2(node.getJsonNode(parserJson.getResultStringFile2()));
        deleteAllFilesFolder("./src/main/resources/uploadFiles/");

        return "homepage";
    }


    //method for clear the directory
    public static void deleteAllFilesFolder(String path) {
        for (File myFile : new File(path).listFiles()) {
            myFile.delete();
        }
    }


    //result page
    @GetMapping("/result")
    public String result(Model model) {
        model.addAttribute("title", "Result page");
        model.addAttribute("node1", compareFiles.getNode1());
        model.addAttribute("node2", compareFiles.getNode2());
        model.addAttribute("result", Compare.resultCompareFiles);
        model.addAttribute("infoStructure", Compare.infoStructure);

        //attributes services
        model.addAttribute("checkFieldsMandatoryServices", Compare.checkFieldsMandatoryServices);
        model.addAttribute("mandatoryFieldsServices", CompareServices.mandatoryFieldsServices);
        model.addAttribute("checkFieldsOptionalServices1", Compare.checkFieldsOptionalServices1);
        model.addAttribute("checkFieldsOptionalServices2", Compare.checkFieldsOptionalServices2);

        //attributes artifacts
        model.addAttribute("checkFieldsMandatoryArtifacts", Compare.checkFieldsMandatoryArtifacts);
        model.addAttribute("checkFieldsMandatoryArtifactsMvn", Compare.checkFieldsMandatoryArtifactsMvn);
        model.addAttribute("checkFieldsOptionalArtifacts1", Compare.checkFieldsOptionalArtifacts1);
        model.addAttribute("checkFieldsOptionalArtifacts2", Compare.checkFieldsOptionalArtifacts2);
        model.addAttribute("checkFieldsOptionalArtifactsMvn1", Compare.checkFieldsOptionalArtifactsMvn1);
        model.addAttribute("checkFieldsOptionalArtifactsMvn2", Compare.checkFieldsOptionalArtifactsMvn2);
        model.addAttribute("mandatoryFieldsArtifacts", CompareArtifacts.mandatoryFieldsArtifacts);
        model.addAttribute("mandatoryFieldsArtifactsMvn", CompareArtifacts.mandatoryFieldsArtifactsMvn);

        //attributes script
        model.addAttribute("checkFieldsMandatoryScript", Compare.checkFieldsMandatoryScript);
        model.addAttribute("mandatoryFieldsScript", CompareScript.mandatoryFieldsScript);
        model.addAttribute("checkFieldsOptionalScript1", Compare.checkFieldsOptionalScript1);
        model.addAttribute("checkFieldsOptionalScript2", Compare.checkFieldsOptionalScript2);

        //attributes rpm
        model.addAttribute("mandatoryFieldsRpm", CompareRpm.mandatoryFieldsRpm);
        model.addAttribute("checkFieldsMandatoryRpm", Compare.checkFieldsMandatoryRpm);
        model.addAttribute("checkFieldsOptionalRpm1", Compare.checkFieldsOptionalRpm1);
        model.addAttribute("checkFieldsOptionalRpm2", Compare.checkFieldsOptionalRpm2);

        //attributes enum
        for(ResultCompare resultCompare : ResultCompare.values()) {
            model.addAttribute(resultCompare.toString(), resultCompare);
        }

        compareFiles.compareFiles();

        return "resultComparePage";
    }
}
