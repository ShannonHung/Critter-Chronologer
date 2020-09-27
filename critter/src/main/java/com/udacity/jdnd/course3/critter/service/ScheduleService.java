package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.Repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Schedule saveSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());

        List<Employee> employees = findEmployeesById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);

        List<Pet> pets = findPetsById(scheduleDTO.getPetIds());
        schedule.setPets(findPetsById(scheduleDTO.getPetIds()));


        for(Pet pet:pets){
            Customer customers = customerRepository.getOne(pet.getCustomer().getId());
            if(schedule.getCustomers()==null) schedule.setCustomers(new ArrayList<>());
            schedule.getCustomers().add(customers);
        }

        schedule = scheduleRepository.save(schedule);

        //update employees
        for(Employee employee : employees){
            if(employee.getSchedules() == null) employee.setSchedules(new ArrayList<>());
            employee.getSchedules().add(schedule);
            System.out.println("employeeRepository.save(employee).getSchedules().size() =" + employeeRepository.save(employee).getSchedules().size());
        }

        //update pets
        for(Pet pet : pets){
            if(pet.getSchedules() == null) pet.setSchedules(new ArrayList<>());
            pet.getSchedules().add(schedule);
            System.out.println("petRepository.save(pet).getSchedules().size() =" + petRepository.save(pet).getSchedules().size());
        }

        //update customers
        for(Customer customer: schedule.getCustomers()){
            if(customer.getSchedules() == null) customer.setSchedules(new ArrayList<>());
            customer.getSchedules().add(schedule);
        }

        return schedule;

    }
    public List<Schedule> findAllSchedules(){
        return scheduleRepository.findAll();
    }



    public List<Employee> findEmployeesById(List<Long> employeesId){
        List<Employee> employees = new ArrayList<>();
        for(Long id : employeesId){
            employees.add(employeeRepository.getOne(id));
        }
        System.out.println("employees.size() = " + employees.size());
        return employees;
    }
    public List<Pet> findPetsById(List<Long> petsId){
        List<Pet> pets = new ArrayList<>();
        for(Long id : petsId){
            pets.add(petRepository.getOne(id));
        }
        return pets;
    }
}
