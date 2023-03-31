package com.tmasolutions.service.Impl;

import com.tmasolutions.model.AppUser;
import com.tmasolutions.repo.AppUserRepository;
import com.tmasolutions.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppUserServiceImpl implements IAppUserService{

    @Autowired
    AppUserRepository userRepository;

    @Override
    public AppUser createNewUser(AppUser usr) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usr.setPassword(passwordEncoder.encode(usr.getPassword()));
        // TODO Auto-generated method stub
        return userRepository.save(usr);
    }

    @Override
    public List<AppUser> loadUserByUsername(String Username) {
        // TODO Auto-generated method stub
        return userRepository.findByEmail(Username);
    }

    @Override
    public Page<AppUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<AppUser> findByEmailContaining(String email, Pageable pageable) {
        return userRepository.findByEmailContaining(email, pageable);
    }

    @Cacheable(value = "AppUser", key = "#id")
    @Override
    public AppUser findById(Long id) {
        AppUser usr = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        return usr;
    }

    @Override
    @CachePut(value = "AppUser", key = "#id")
    public AppUser updateUser(Long id, AppUser newAppUser) {
        AppUser bk = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        bk.setFirstname(newAppUser.getFirstname());
        bk.setLastname(newAppUser.getLastname());
        bk.setRole(newAppUser.getRole());
        userRepository.save(bk);
        return bk;
    }

    @Override
    @CacheEvict(value = "AppUser", key = "#id")
    public void deleteAppUser(Long id) {
        AppUser usr = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        userRepository.delete(usr);
    }

}

