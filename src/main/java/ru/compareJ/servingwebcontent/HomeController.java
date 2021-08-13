package ru.compareJ.servingwebcontent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {


    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("title", "Главная страница");
        model.addAttribute("jsonFile1Version", 3);
        model.addAttribute("compare", Compare.infoAboutCompareProcess);

        model.addAttribute("file1", App.nodeFile1);
        model.addAttribute("file2", App.nodeFile2);
        return "homepage";
    }


}
