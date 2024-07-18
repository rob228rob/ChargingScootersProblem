package com.example.services;

import com.example.entities.Role;
import com.example.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

}
