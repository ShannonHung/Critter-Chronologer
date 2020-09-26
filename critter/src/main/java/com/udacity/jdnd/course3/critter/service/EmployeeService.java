package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EmployeeService{
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        Employee employee1 = employeeRepository.save(employee);
        System.out.println("Saved Employee: id is " + employee1.getId());
        return employee1;
    }
    public Employee getEmployeeById(Long id){
        return employeeRepository.getOne(id);
    }
}
