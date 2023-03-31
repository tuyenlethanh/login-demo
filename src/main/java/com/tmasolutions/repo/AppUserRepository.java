package com.tmasolutions.repo;

import com.tmasolutions.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long>  {
    List<AppUser> findByEmail(String Email);

    Page<AppUser> findAll(Pageable pageable);
    Page<AppUser> findByEmailContaining(String email, Pageable pageable);
}
