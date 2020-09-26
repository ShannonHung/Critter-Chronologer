package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;

import static com.udacity.jdnd.course3.critter.user.EmployeeSkill.*;

@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class MyTest {
    @Autowired
    EmployeeService employeeService;

    @Test
    public void testJpa(){
//        employeeService.save(new Employee("Gwan", new HashSet<>(Arrays.asList(MEDICATING, PETTING)), new HashSet<>(Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY))));
//        employeeService.save(new Employee("Shannon",new HashSet<>(Arrays.asList(MEDICATING, PETTING, WALKING)), new HashSet<>(Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY))));
//

    }
}
