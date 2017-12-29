package com.example.springcloud.springclouddemo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends MongoRepository<Person, String> {
  List<Person> findPeopleByAge(@Param("age") int aAge);
  List<Person> findPeopleByName(@Param("name") String aName);
  List<Person> findPeopleByNameAndAge(@Param("name") String aName, @Param("age") int aAge);
}
