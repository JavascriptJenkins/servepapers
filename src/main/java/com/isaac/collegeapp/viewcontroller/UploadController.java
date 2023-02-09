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
import java.io.*;
import java.nio.file.*;
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

    @PostMapping("/upload2")
    public String uploadFile2(@ModelAttribute( "processdata" ) ProcessDataDAO processDataDAO,
                             Model model,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes attributes,
                             @RequestParam("customJwtParameter") String customJwtParameter) {

        System.out.println("file upload 1");
        // check if file is empty
        if (file.isEmpty()) {
            model.addAttribute("errorMessage","Please select a file to upload.");
            model.addAttribute("processdata", processDataDAO);
            model.addAttribute("customJwtParameter",customJwtParameter);
            return "/editforms.html";
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
            return "editforms.html";
        }


        // write code here to see how many files have been uploaded related to the filenumber on processdata record

        List<FileVO> filelist = techvvsFileHelper.getFilesByFileNumber(processDataDAO.getFilenumber(), UPLOAD_DIR);



        // return success response
        model.addAttribute("successMessage","You successfully uploaded " + fileName + '!');
        model.addAttribute("processdata", processDataDAO);
        model.addAttribute("filelist", filelist);
        model.addAttribute("customJwtParameter",customJwtParameter);
        return "editforms.html";
    }

    // note: must return null otherwise file download sucks
    @RequestMapping(value="/download", method=RequestMethod.GET)
    public void downloadFile(@RequestParam("filename") String filename,
                                       HttpServletResponse response,
                                       @RequestParam("customJwtParameter") String customJwtParameter,
                                       Model model,
                                        @ModelAttribute( "processdata" ) ProcessDataDAO processDataDAO
                               ) {
        File file;

        try {
            if(filename.contains(".pdf")){
                response.setContentType("application/pdf");
            }

            response.setHeader("Content-Disposition","attachment; filename="+filename);

            file = new File(UPLOAD_DIR+filename);

            byte[] fileContent = Files.readAllBytes(file.toPath());


            // get your file as InputStream
            InputStream is = new ByteArrayInputStream(fileContent);


            model.addAttribute("customJwtParameter",customJwtParameter);
            System.out.println("has value: "+processDataDAO);

            model.addAttribute("customJwtParameter",customJwtParameter);
            model.addAttribute("processdata",processDataDAO);
            model.addAttribute("disableupload","true");
            List<FileVO> filelist = techvvsFileHelper.getFilesByFileNumber(processDataDAO.getFilenumber(), UPLOAD_DIR);
            model.addAttribute("filelist",filelist);


            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
        } catch (IOException ex) {
            System.out.println("Error writing file to output stream. Filename was: " +filename);
            System.out.println("Error writing file to output stream. exception: " +ex.getMessage());
            model.addAttribute("customJwtParameter",customJwtParameter);
            throw new RuntimeException("IOError writing file to output stream");
        }


//        return "redirect: /newform/viewNewForm";
    }

}