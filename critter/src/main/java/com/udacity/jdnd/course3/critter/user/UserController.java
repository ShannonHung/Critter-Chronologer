package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        //1. 取得CustomerDTO之後 轉程 Customer丟到service去儲存
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
            //但是customerDTO裡面取得的是petid，所以要先把petid轉換成pet再丟回來
        List<Long> petids = customerDTO.getPetIds();

        //2. 因為testCreateCustomer規定回傳的是CustomerDTO因此需要把service回傳的customer轉成DTO
        return turnToCustomerDTO(customerService.saveCustomer(customer, petids));
    }



    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        //因為資料庫的customer只有三的屬性分別是: id, name, note, phonenumber
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        List<Customer> customers = customerService.getAllCustomers();

        for(Customer customer : customers){
            customerDTOS.add(turnToCustomerDTO(customer));
        }

        return customerDTOS;

        //高階寫法
//        return customers.stream().map(this::turnToCustomerDTO).collect(Collectors.toList());
    }
    public List<Long> turnToPetIds(List<Pet> pets){
        List<Long> petids = new ArrayList<>();
        for(Pet pet:pets){
            petids.add(pet.getId());
        }
        System.out.println("petids = "+ petids.get(0));
        return petids;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        //1. will get petid, then use petid to return the pet object
        Pet pet = petService.getPetById(petId);

        //2. get the pet object, then find the pet.customer
        Customer customer = pet.getCustomer();

        //3. turn customer into CustomerDTO
        return turnToCustomerDTO(customer);
    }


    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        //1. 取得employeeDTO 然後丟到service去存入資料庫
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());

        //2. 存入資料庫之後會回傳employee的物件，把她轉換成employeeDTO
        return turnToEmployeeDTO(employeeService.saveEmployee(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return turnToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        //1. now we get daysAvailable and employeeId, find the employeeById first
        Employee employee = employeeService.getEmployeeById(employeeId);

        //2. set the daysAvailable to employee
        employee.setDaysAvailable(daysAvailable);

        //3. save the newEmployee to the database
        Employee newEmployee = employeeService.saveEmployee(employee);
        System.out.println("http://localhost:8082/user/employee/1 = " + newEmployee.getName() + newEmployee.getSchedules());
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.getEmployeesBySkillAndDay(
                employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());

//        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        //stream()就是在做這件事
//        for(Employee employee : employees){
//            // .collect(Collectors.toList()) 就是在做 employeeDTOs.add
//            // this::turnToEmployeeDTO = 這裡的this是stream map出來的東西也就是employee, 然後::旁邊的是將this放入某個方法
//            employeeDTOS.add(turnToEmployeeDTO(employee));
//        }
//        return employeeDTOS;


        return employees.stream().map(this::turnToEmployeeDTO).collect(Collectors.toList());

    }

    //this method will get the object from the database, and we need to turn it into CustomerDTO
    public CustomerDTO turnToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setId(customer.getId());
        System.out.println("customer.getPets().size()" + customer.getPets().size());

        List<Long> petids = new ArrayList<>();
        for(Pet pet : customer.getPets()){
            System.out.println("pet.getId()" + pet.getId());
            petids.add(pet.getId());
        }
//        System.out.println("petids.get(0)" + petids.get(0));
        customerDTO.setPetIds(petids);
        return customerDTO;
    }

    //this method will get the object from the database, and we need to turn it into EmployeeDTO
    public EmployeeDTO turnToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        employeeDTO.setSkills(employee.getSkills());
        return employeeDTO;
    }


}
