package com.example.hrituserservice.repositories;

import com.example.hrituserservice.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @RestResource(path = "by-name")
    Privilege findByName(String name);
}
