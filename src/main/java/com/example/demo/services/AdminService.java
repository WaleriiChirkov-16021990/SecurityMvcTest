package com.example.demo.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

//    @PreAuthorize("hasRole('ROLE_ADMIN') or/and hasRole('Role_SOME_OTHER')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void doAdminStuff() {
        System.out.println("Only admin here!");
    }
}
