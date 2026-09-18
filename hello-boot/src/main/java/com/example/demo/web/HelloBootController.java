package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloBootController {

  @GetMapping("/me")
  public String introduce(Model model) {
    model.addAttribute("name", "유동혁");
    model.addAttribute("job", "Developer");
    model.addAttribute("age", 29);
    return "introduceme";
  }

}
