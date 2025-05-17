package com.js.people.service;

import com.js.people.models.People;

import java.util.List;

public interface ServicePeople {

    List<People> findAll();

    People findById(Long id);

    People findByName(String name);

    People save(People people);

    People update(Long id, People people);

    void deleteById(Long id);
}
