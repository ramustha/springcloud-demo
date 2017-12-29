package com.example.springcloud.springclouddemo;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
  @Autowired(required = false)
  MongoDbFactory mongoDbFactory;
  @Autowired(required = false)
  ApplicationInstanceInfo instanceInfo;

  @RequestMapping("/")
  public String home(Model model) {
    model.addAttribute("instanceInfo", instanceInfo);

    if (instanceInfo != null) {
      Map<Class<?>, String> services = new LinkedHashMap<>();
      services.put(mongoDbFactory.getClass(), toString(mongoDbFactory));
      model.addAttribute("services", services.entrySet());
    }

    return "home";
  }

  private static String toString(MongoDbFactory mongoDbFactory) {
    if (mongoDbFactory == null) {
      return "<none>";
    } else {
      try {
        return mongoDbFactory.getDb().getMongo().getAddress().toString();
      } catch (Exception ex) {
        return "<invalid address> " + mongoDbFactory.getDb().getMongo().toString();
      }
    }
  }

}
