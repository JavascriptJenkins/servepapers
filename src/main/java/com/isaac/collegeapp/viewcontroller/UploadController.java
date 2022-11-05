package com.isaac.collegeapp.viewcontroller;

import com.isaac.collegeapp.model.ProcessDataDAO;
import com.isaac.collegeapp.modelnonpersist.FileVO;
import com.isaac.collegeapp.util.TechvvsFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/file")
@Controller
public class UploadController {

    private final String UPLOAD_DIR = "./uploads/";

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @Autowired
    TechvvsFileHelper techvvsFileHelper;

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute( "processdata" ) ProcessDataDAO processDataDAO,
                             Model model,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes attributes,
                             @RequestParam("customJwtParameter") String customJwtParameter) {

        System.out.println("file upload 1");
        // check if file is empty
        if (file.isEmpty()) {
            model.addAttribute("errorMessage","Please select a file to upload.");
            System.out.println("file upload 2");
            model.addAttribute("processdata", processDataDAO);
            model.addAttribute("customJwtParameter",customJwtParameter);
            return "/newform.html";
        }
        System.out.println("file upload 3");
        String fileName = "";
        if(file.getOriginalFilename() != null){
            System.out.println("file upload 4");
//            file.getOriginalFilename() =  file.getOriginalFilename().replaceAll("-","");
            // normalize the file path
            fileName = "/"+processDataDAO.getFilenumber()+"---"+StringUtils.cleanPath(file.getOriginalFilename());
        }

        // save the file on the local file system
        try {
            System.out.println("file upload 5");
            Path path = Paths.get(UPLOAD_DIR + fileName);
            System.out.println("file upload 6");
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("file upload 7");
        } catch (IOException e) {
            System.out.println("file upload 8");
            e.printStackTrace();
            model.addAttribute("errorMessage","file upload failed");
            model.addAttribute("processdata", processDataDAO);
            model.addAttribute("customJwtParameter",customJwtParameter);
            return "newform.html";
        }


        // write code here to see how many files have been uploaded related to the filenumber on processdata record

        System.out.println("file upload 9");
        List<FileVO> filelist = techvvsFileHelper.getFilesByFileNumber(processDataDAO.getFilenumber(), UPLOAD_DIR);

        System.out.println("file upload 10");


        // return success response
        model.addAttribute("successMessage","You successfully uploaded " + fileName + '!');
        model.addAttribute("processdata", processDataDAO);
        model.addAttribute("filelist", filelist);
        model.addAttribute("customJwtParameter",customJwtParameter);
        return "newform.html";
    }

}