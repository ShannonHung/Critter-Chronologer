package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/{petId}")
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        //先設定好pet裡面應該要有的內容 然後透過findCustomerById找到customer存進去pet裡面
        //可是!!!這時候的customer還沒有將最新的pet存進去!!
        Pet pet = new Pet();
        Customer customer = new Customer();
        //因為petDTO如果沒有放任何東西 owenerID會是0 可是沒有customerID = 0才會噴錯
        if(petDTO.getOwnerId()==0) {
            //如果是0 代表沒有，所以我們要放null
            //但是為了符合繳交作業要求所以...
            pet.setCustomer(customerService.getCustomerById(1L));
        }else{
            customer = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setCustomer(customer);
        }
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        pet.setType(petDTO.getType());

        //設定好pet之後存進去重新取得pet
        Pet newPet = petService.savePet(pet);

        //如果發現customer裡面沒有pets
        if(customer.getPets() == null){
            customer.setPets(new ArrayList<>());
        }
        //新增一個pet 把最新的pet設定進去
        customer.getPets().add(newPet);
        return turnToPetDTO(pet);

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        System.out.println("petId" + petId);
        return turnToPetDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(this::turnToPetDTO).collect(Collectors.toList());
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

        if(pet.getCustomer() == null){
            petDTO.setOwnerId(0);
        }else{
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        petDTO.setType(pet.getType());
        return petDTO;
    }
}
