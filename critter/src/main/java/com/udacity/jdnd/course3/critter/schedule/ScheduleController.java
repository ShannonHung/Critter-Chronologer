package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("scheduleDTO.getPetIds().size()" + scheduleDTO.getPetIds().size());
        Schedule schedule = scheduleService.saveSchedule(scheduleDTO);
        System.out.println("Now siz is = " + schedule.getPets().size());
        return turnToScheduleDTO(schedule);
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
}
