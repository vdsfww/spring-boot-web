package org.example.bookshop.repository.role;

import org.example.bookshop.model.Role;
import org.example.bookshop.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
