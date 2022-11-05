package com.isaac.collegeapp.service;


import com.isaac.collegeapp.jparepo.ProcessDataRepo;
import com.isaac.collegeapp.model.ProcessDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class NewFormService {

    @Autowired
    ProcessDataRepo processDataRepo;

    private List<ProcessDataDAO> processdatalist = new ArrayList<>();

    public Page<ProcessDataDAO> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProcessDataDAO> list;

        System.out.println("Initializing processdata pagination... ");
        processdatalist = processDataRepo.findAll();

        if (processdatalist.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, processdatalist.size());
            list = processdatalist.subList(startItem, toIndex);
        }

        Page<ProcessDataDAO> processdataPage
                = new PageImpl<ProcessDataDAO>(list, PageRequest.of(currentPage, pageSize), processdatalist.size());

        return processdataPage;
    }


//    @PostConstruct
//    void setProcessDataList(){
//
//        System.out.println("Initializing processdata pagination... ");
//        processdatalist = processDataRepo.findAll();
//    }

}
