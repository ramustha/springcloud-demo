package com.example.springcloud.springclouddemo;

import io.reactivex.Observable;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
  @Autowired
  PersonRepository repository;

  @RequestMapping(value = "/persons", method = RequestMethod.GET)
  List<Person> persons() {
    return repository.findAll();
  }

  @RequestMapping(value = "/person", method = RequestMethod.GET)
  List<Person> person(
      @RequestParam(value = "name", required = false) String aName,
      @RequestParam(value = "age", required = false) String aAge) {
    if (aName != null && aAge != null) {
      return repository.findPeopleByNameAndAge(aName, Integer.parseInt(aAge));
    } else if (aName != null) {
      return repository.findPeopleByName(aName);
    } else if (aAge != null) {
      return repository.findPeopleByAge(Integer.parseInt(aAge));
    }
    return null;
  }

  @RequestMapping(value = "/save", method = RequestMethod.GET)
  String save(@RequestParam(value = "size", required = false) String aSize) {

    String[] names = {"Eddard", "Catelyn", "Jon", "Rob", "Sansa", "Aria", "Bran", "Rickon"};
    Random ramdom = new Random();
    Observable.defer(() -> Observable.just(names[ramdom.nextInt(names.length)]))
        .repeat(aSize == null ? 1 : Long.parseLong(aSize))
        .flatMap(aName ->
        {
          repository.insert(new Person(aName, ramdom.nextInt(names.length)));
          return Observable.just(aName);
        })
        .doOnNext(aPerson -> System.out.println(aPerson + " size " + repository.findAll().size()))
        .subscribe();

    return "Saved";
  }
}
