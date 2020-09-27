package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.*;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllBySkillsInAndDaysAvailableContains(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek);


}
