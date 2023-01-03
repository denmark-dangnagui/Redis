package com.example.red;

import com.example.red.entity.Address;
import com.example.red.entity.Person;
import com.example.red.repository.PersonRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInit {

    private final PersonRedisRepository personRedisRepository;

    @PostConstruct
    void init(){
        Address seoul = new Address("서울");
        Person person = new Person(null, "first", "last", seoul);

        Person savedPerson = personRedisRepository.save(person);

        personRedisRepository.findById(person.getId());


    }
}
