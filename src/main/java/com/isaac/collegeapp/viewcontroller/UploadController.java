package com.isaac.collegeapp.viewcontroller;

import com.isaac.collegeapp.model.ProcessDataDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RequestMapping("/file")
@Controller
public class UploadController {

    private final String UPLOAD_DIR = "./uploads/";

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute( "processdata" ) ProcessDataDAO processDataDAO,
                             Model model,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes attributes,
                             @RequestParam("customJwtParameter") String customJwtParameter) {

        // check if file is empty
        if (file.isEmpty()) {
            model.addAttribute("errorMessage","Please select a file to upload.");

            model.addAttribute("processdata", processDataDAO);
            model.addAttribute("customJwtParameter",customJwtParameter);
            return "/newform.html";
        }

        String fileName = "";
        if(file.getOriginalFilename() != null){
//            file.getOriginalFilename() =  file.getOriginalFilename().replaceAll("-","");
            // normalize the file path
            fileName = "/"+processDataDAO.getFilenumber()+"---"+StringUtils.cleanPath(file.getOriginalFilename());
        }

        // save the file on the local file system
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage","file upload failed");
            model.addAttribute("processdata", processDataDAO);
            model.addAttribute("customJwtParameter",customJwtParameter);
            return "newform.html";
        }

        // return success response
        model.addAttribute("successMessage","You successfully uploaded " + fileName + '!');
        model.addAttribute("processdata", processDataDAO);
        model.addAttribute("customJwtParameter",customJwtParameter);
        return "/newform.html";
    }

}