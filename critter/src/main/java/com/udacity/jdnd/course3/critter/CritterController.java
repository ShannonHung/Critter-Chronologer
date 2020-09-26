package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;

import static com.udacity.jdnd.course3.critter.user.EmployeeSkill.*;

/**
 * Dummy controller class to verify installation success. Do not use for
 * your project work.
 */
@RestController
public class CritterController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/test")
    public String test(){
        return "Critter Starter installed successfully";
    }
}
