package org.beautybox.repository;

import org.beautybox.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, String> {
    Boolean existsByName(String name);
    Role findByName(String name);
}
