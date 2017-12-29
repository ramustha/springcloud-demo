package com.example.springcloud.springclouddemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class SpringcloudDemoApplication {

  public static void main(String[] args) {
    setCertificateProperty();
    SpringApplication.run(SpringcloudDemoApplication.class, args);
  }

  static void setCertificateProperty() {
    System.setProperty("TRUSTED_CA_CERTIFICATE", "-----BEGIN CERTIFICATE-----\n" +
        "          YOUR TRUSTED_CA_CERTIFICATE \n" +
        "          -----END CERTIFICATE-----");
  }

  @Autowired
  MongoTemplate fTemplate;

  @Bean
  public CommandLineRunner cmd() {
    return arg -> {

      if (!fTemplate.collectionExists(Person.class)) {
        fTemplate.createCollection(Person.class, new CollectionOptions(128000000, 128000000, true));
      }

      for (String s : fTemplate.getCollectionNames()) {
        System.out.println("##### COLLECTION " + s);
      }
    };
  }
}
