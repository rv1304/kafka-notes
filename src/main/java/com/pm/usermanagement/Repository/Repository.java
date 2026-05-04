package com.pm.usermanagement.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.pm.usermanagement.model.user;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface Repository extends JpaRepository<user, UUID> {
    Page<user> findByNameContainingAndAge(String name, Integer age, Pageable pageable);

    Optional<user> findByEmail(String email);
}

