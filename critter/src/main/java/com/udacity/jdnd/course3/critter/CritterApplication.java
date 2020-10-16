package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.udacity.jdnd.course3.critter.user.EmployeeSkill.MEDICATING;
import static com.udacity.jdnd.course3.critter.user.EmployeeSkill.PETTING;

/**
 * Launches the Spring application. Unmodified from starter code.
 */
@SpringBootApplication
public class CritterApplication {
	public static void main(String[] args) {
		SpringApplication.run(CritterApplication.class, args);
	}

}
