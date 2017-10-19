package com.example.hrituserservice.repositories;

import com.example.hrituserservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long> {

    @RestResource(path = "by-name")
    Role findByName(String name);
}
