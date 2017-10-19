package com.example.hrituserservice.dump;


import com.example.hrituserservice.entity.Privilege;
import com.example.hrituserservice.entity.Role;
import com.example.hrituserservice.entity.User;
import com.example.hrituserservice.repositories.PrivilegeRepository;
import com.example.hrituserservice.repositories.RoleRepository;
import com.example.hrituserservice.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Component
public class H2DumpRunner implements CommandLineRunner {


    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;
    private TransactionTemplate transactionTemplate;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        transactionTemplate.execute(status -> {
            Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
            Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
            List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
            Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
            createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));

            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setPassword("123456");
            user.setRoles(Collections.singletonList(adminRole));
            user.setEnabled(true);
            userRepository.save(user);
            return null;
        });
    }

    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    private Role createRoleIfNotFound(String name, List<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
