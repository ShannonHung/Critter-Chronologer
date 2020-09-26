package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //他是一個獨立table, 連接employee_id跟schedule_id,作為schedule跟employee之間的關聯表
    @ManyToMany
    @JoinTable(name="schedule_employee", joinColumns = @JoinColumn(name="schedule_id"),
                inverseJoinColumns = @JoinColumn(name="employee_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Employee> employees;

    //他也是一個獨立的table，連接pet_id跟schedule_id的關聯表
    @ManyToMany
    @JoinTable(name="schedule_pet", joinColumns = @JoinColumn(name="schedule_id"),
            inverseJoinColumns = @JoinColumn(name="pet_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Pet> pets;

    private LocalDate date;

    //因為他不是獨立的entity所以使用elementCollection就可
    @ElementCollection(fetch =FetchType.EAGER)
    @JoinTable(name="schedule_activity", joinColumns = @JoinColumn(name = "skill_id"))
    @Column(name="activity", nullable = false)
    @Enumerated(EnumType.STRING)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<EmployeeSkill> activities;

    public Schedule(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
