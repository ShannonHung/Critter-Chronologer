package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PetRepository petRepository;

    public Customer saveCustomer(Customer customer, List<Long> petids){
        List<Pet> pets = new ArrayList<>();
        if(petids != null && !petids.isEmpty()){
            for(Long id : petids){
                pets.add(petRepository.getOne(id));
            }
        }
        customer.setPets(pets);
        Customer customer1 = customerRepository.save(customer);
        System.out.println(customer1.getId());
        return customer1;
    }
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public Customer getCustomerById(Long id){
        return customerRepository.getOne(id);
    }
}
