package ru.compareJ.servingwebcontent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class HomeController {

    Compare compareFiles = new Compare();

    @GetMapping("/")
    public String greeting(Model model) {
        deleteAllFilesFolder("./src/main/resources/uploadFiles/");
        model.addAttribute("title", "Home page");
        model.addAttribute("node1", compareFiles.getNode1());
        model.addAttribute("node2", compareFiles.getNode2());
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
            for (MultipartFile file:uploadFiles){
                MultipartFile uploadFile = file;
                String realPath = req.getSession().getServletContext().getRealPath(uploadPath);
                String format = sdf.format(new Date());
                File folder = new File(uploadPath);
                if (!folder.isDirectory()){ // Если текущий каталог не существует
                    folder.mkdirs(); // Создать новый каталог
                }
                String oldName = uploadFile.getOriginalFilename(); // Старое имя
                String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length()); //Новое имя
                try {
                    uploadFile.transferTo(new File(folder,newName)); //загрузить файлы
                } catch (Exception e){
                    e.printStackTrace();
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

    public static void deleteAllFilesFolder(String path) {
        for (File myFile : new File(path).listFiles()) {
            myFile.delete();
        }
    }

    @GetMapping("/result")
    public String result(Model model) {
        int var = 0;
        model.addAttribute("title", "Result page");
        model.addAttribute("node1", compareFiles.getNode1());
        model.addAttribute("node2", compareFiles.getNode2());
        model.addAttribute("result", Compare.resultCompareFiles);
        model.addAttribute("variable", var);
        model.addAttribute("checkFieldsMandatoryServices", Compare.checkFieldsMandatoryServices);
        model.addAttribute("mandatoryFieldsServices", CompareServices.mandatoryFieldsServices);
        model.addAttribute("checkFieldsOptionalServices1", Compare.checkFieldsOptionalServices1);
        model.addAttribute("checkFieldsOptionalServices2", Compare.checkFieldsOptionalServices2);
        model.addAttribute("checkFieldsMandatoryArtifactsMvn", Compare.checkFieldsMandatoryArtifactsMvn);

        for(ResultCompare resultCompare : ResultCompare.values()) {
            model.addAttribute(resultCompare.toString(), resultCompare);
        }

        compareFiles.compareFiles();

        return "resultpage";
    }
}
