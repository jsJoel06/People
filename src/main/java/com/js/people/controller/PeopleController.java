package com.js.people.controller;

import com.js.people.controller.request.CreateUserDTO;
import com.js.people.models.ERoleEnum;
import com.js.people.models.People;
import com.js.people.models.RoleEntity;
import com.js.people.models.UserEntity;
import com.js.people.repository.UserRepository;
import com.js.people.service.ServicePeople;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private ServicePeople servicePeople;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public ResponseEntity<List<People>> getAll(){
        List<People> people = servicePeople.findAll();
        return people != null ? ResponseEntity.ok(people) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/A/{id}")
    public ResponseEntity<People> getById(@PathVariable Long id){
        People people = servicePeople.findById(id);
        return people != null ? ResponseEntity.ok(people) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<People> getByName(@PathVariable String name){
        People people = servicePeople.findByName(name);
        return people != null ? ResponseEntity.ok(people) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<People> save(@RequestBody People people){
        People peopleSave = servicePeople.save(people);
        return peopleSave != null ? ResponseEntity.status(HttpStatus.CREATED).body(peopleSave) :
                ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<People> update(@PathVariable Long id, @RequestBody People people){
        People peopleUpdate = servicePeople.update(id, people);
        return peopleUpdate != null ? ResponseEntity.ok(peopleUpdate) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        servicePeople.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<UserEntity> create(@Valid @RequestBody CreateUserDTO createUserDTO) {
        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERoleEnum.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }
}
