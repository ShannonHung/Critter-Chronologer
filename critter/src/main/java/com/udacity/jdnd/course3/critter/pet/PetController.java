package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        /*1. we get petDTO from the POSTMAN, then save this object into the database
            - the object we get from the postman included customerid, name and type*/
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        pet.setType(petDTO.getType());
        pet.setCustomer(customerService.getCustomerById(petDTO.getOwnerId()));
        pet = petService.savePet(pet);

        //2. update pets in customer !! we have to add the new pet into the customer
        Customer customer = customerService.getCustomerById(pet.getCustomer().getId());
        customer.getPets().add(pet);
        customer = customerService.saveCustomer(customer);
        return turnToPetDTO(petService.savePet(pet));



        //先設定好pet裡面應該要有的內容 然後透過findCustomerById找到customer存進去pet裡面
        //可是!!!這時候的customer還沒有將最新的pet存進去!!
//        Pet pet = new Pet();
//        Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
//        pet.setName(petDTO.getName());
//        pet.setBirthDate(petDTO.getBirthDate());
//        pet.setNotes(petDTO.getNotes());
//        pet.setType(petDTO.getType());
//        pet.setCustomer(customer);
//
//        //設定好pet之後存進去重新取得pet
//        Pet newPet = petService.savePet(pet);
//
//        //如果發現customer裡面沒有pets
//        if(customer.getPets() == null){
//            customer.setPets(new ArrayList<>());
//        }
//        //新增一個pet 把最新的pet設定進去
//        customer.getPets().add(newPet);
//        return turnToPetDTO(pet);

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return turnToPetDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){

        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        List<PetDTO> petDTOS = new ArrayList<>();
        for(Pet pet : pets){
            petDTOS.add(turnToPetDTO(pet));
        }
        return petDTOS;
    }

    public PetDTO turnToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setName(pet.getName());
        petDTO.setNotes(pet.getNotes());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setType(pet.getType());
        return petDTO;
    }
}
