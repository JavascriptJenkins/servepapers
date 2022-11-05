package com.isaac.collegeapp.util;

import com.isaac.collegeapp.modelnonpersist.FileVO;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class TechvvsFileHelper {


    public List<FileVO> getFilesByFileNumber(Integer filenumber, String uploaddir){
        List<FileVO> filelist = new ArrayList<>(2);

        try{
            Path path = Paths.get(uploaddir);
            File dir = new File(String.valueOf(path));
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    // Do something with child
                    System.out.println("listing files");
                    child.getAbsoluteFile();
                    System.out.println("listing files 2");
                    child.getAbsoluteFile().getName();
                    System.out.println("listing files 3");
                    if(child.getAbsoluteFile().getName().contains(String.valueOf(filenumber))){
                        System.out.println("listing files 4");
                        FileVO fileVO = new FileVO();
                        fileVO.setFilename(child.getAbsoluteFile().getName());
                        filelist.add(fileVO); // add it to a nonpersisted list that will be displayed on the ui
                    }
                }
            } else {
                System.out.println("Error getting list of files, should never happen. ");
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
            }
        } catch(Exception ex){
            System.out.println("listing files Exception");
            System.out.println("Caught exception listing files: "+ex.getMessage());
        }

        return filelist;
    }





}
