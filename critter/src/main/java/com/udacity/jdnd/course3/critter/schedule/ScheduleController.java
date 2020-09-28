package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        System.out.println("test = " +scheduleDTO.getPetIds().get(0));
        System.out.println("test2 = " + scheduleDTO.getEmployeeIds().get(0) + scheduleDTO.getActivities().toString() + scheduleDTO.getDate());

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Employee> employees = findEmployeesById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);

        List<Pet> pets = findPetsById(scheduleDTO.getPetIds());
        schedule.setPets(pets);


        for(Pet pet:pets){
            Customer customers = customerService.getCustomerById(pet.getCustomer().getId());
            if(schedule.getCustomers()==null) schedule.setCustomers(new ArrayList<>());
            schedule.getCustomers().add(customers);
        }

        Schedule newSchedule = scheduleService.saveSchedule(schedule);

        //update employees
        for(Employee employee : employees){
            if(employee.getSchedules() == null) employee.setSchedules(new ArrayList<>());
            employee.getSchedules().add(schedule);
            employeeService.saveEmployee(employee);
        }

        //update pets
        for(Pet pet : pets){
            if(pet.getSchedules() == null) pet.setSchedules(new ArrayList<>());
            pet.getSchedules().add(schedule);
            petService.savePet(pet);
        }

        //update customers
        for(Customer customer: schedule.getCustomers()){
            if(customer.getSchedules() == null) customer.setSchedules(new ArrayList<>());
            customer.getSchedules().add(schedule);
            customerService.saveCustomer(customer);
        }

        return turnToScheduleDTO(newSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAllSchedules();
        return schedules.stream().map(this::turnToScheduleDTO).collect(Collectors.toList());

    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        //1. use petid to get the pet, then find the schedules from the pet
        List<Schedule> schedules = petService.getPetById(petId).getSchedules();
        return schedules.stream().map(this::turnToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        //1. find employee by id, then get the schedule from the employee
        List<Schedule> schedules = employeeService.getEmployeeById(employeeId).getSchedules();
        return schedules.stream().map(this::turnToScheduleDTO).collect(Collectors.toList());

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = customerService.getCustomerById(customerId).getSchedules();
        System.out.println("getScheduleForCustomer = " + schedules.size());
        return schedules.stream().map(this::turnToScheduleDTO).collect(Collectors.toList());
    }

    public ScheduleDTO turnToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setPetIds(turnToPetsId(schedule.getPets()));
        scheduleDTO.setEmployeeIds(turnToEmployeesId(schedule.getEmployees()));
        return scheduleDTO;
    }
    public List<Long> turnToEmployeesId(List<Employee> employees){
        List<Long> employeesId = new ArrayList<>();
        for(Employee employee:employees){
            employeesId.add(employee.getId());
        }
        return employeesId;
    }
    public List<Long> turnToPetsId(List<Pet> pets){
        List<Long> petsId = new ArrayList<>();
        for(Pet pet:pets){
            petsId.add(pet.getId());
        }
        return petsId;
    }
    public List<Employee> findEmployeesById(List<Long> employeesId){
        List<Employee> employees = new ArrayList<>();
        for(Long id : employeesId){
            employees.add(employeeService.getEmployeeById(id));
        }
        System.out.println("employees.size() = " + employees.size());
        return employees;
    }
    public List<Pet> findPetsById(List<Long> petsId){
        List<Pet> pets = new ArrayList<>();
        for(Long id : petsId){
            pets.add(petService.getPetById(id));
        }
        System.out.println("pets.size() = " + pets.size());
        return pets;
    }
}
