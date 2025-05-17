package com.js.people.service.Impl;

import com.js.people.models.People;
import com.js.people.repository.PeopleRepository;
import com.js.people.service.ServicePeople;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicePeopleImpl implements ServicePeople {
    
    @Autowired
    private PeopleRepository peopleRepository;


    @Override
    public List<People> findAll() {
        return peopleRepository.findAll();
    }

    @Override
    public People findById(Long id) {
        People people = peopleRepository.findById(id).orElse(null);
        return people;
    }

    @Override
    public People findByName(String name) {
        return peopleRepository.findByName(name).stream().findFirst().orElse(null);
    }

    @Override
    public People save(People people) {
        return peopleRepository.save(people);
    }

    @Override
    public People update(Long id, People people) {
        People peopleUpdate = findById(id);
        if(peopleUpdate != null){
            peopleUpdate.setName(people.getName());
            peopleUpdate.setAge(people.getAge());
            peopleUpdate.setProfession(people.getProfession());
            peopleUpdate.setPhone(people.getPhone());
            peopleUpdate.setSalary(people.getSalary());
            peopleUpdate.setCity(people.getCity());
            peopleUpdate.setCountry(people.getCountry());
            return peopleRepository.save(peopleUpdate);
        }
        return people;
    }

    @Override
    public void deleteById(Long id) {
        peopleRepository.deleteById(id);
    }
}
