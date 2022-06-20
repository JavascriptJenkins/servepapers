package com.isaac.collegeapp;

import com.isaac.collegeapp.model.ProfessorDAO;
import com.isaac.collegeapp.repo.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.List;


@SpringBootApplication
public class CollegeappApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollegeappApplication.class, args);
	}

}
