package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("select p from Pet p where p.customer.id = :id")
    List<Pet> findPetByownerid(Long id);
}
