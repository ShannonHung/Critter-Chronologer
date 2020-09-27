package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    private Long id;

    @Nationalized
    @Column(length = 65)
    private String name;

    //單一個skill的展現
//    @Column
//    @Enumerated(EnumType.STRING)
//    private EmployeeSkill skills;


    @ElementCollection(fetch = FetchType.EAGER)
    @Column
    @Enumerated(EnumType.STRING)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    private Set<EmployeeSkill> skills;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="employee_week",
            joinColumns = @JoinColumn(name="employee_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Column
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "employees")
    List<Schedule> schedules;

    public Employee(){}

    public Employee(String name, Set<EmployeeSkill> skill, Set<DayOfWeek> daysAvailable){
        this.name = name;
        this.skills = skill;
        this.daysAvailable = daysAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public EmployeeSkill getSkills() {
//        return skills;
//    }
//
//    public void setSkills(EmployeeSkill skills) {
//        this.skills = skills;
//    }

        public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
