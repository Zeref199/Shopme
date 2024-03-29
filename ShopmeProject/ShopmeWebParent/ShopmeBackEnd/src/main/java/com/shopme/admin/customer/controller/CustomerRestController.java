package com.shopme.admin.customer.controller;

import com.shopme.admin.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {
    @Autowired
    private CustomerService service;

    @PostMapping("/customer/check_email")
    public String checkDuplicateEmail(Integer id, String email){
        return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
