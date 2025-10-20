package com.example.softwarepos.repository;

import com.example.softwarepos.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserid(String userid);
    Optional<UserEntity> findByUseraddress(String useraddress);
    Optional<UserEntity> findByUseridAndUseraddress(String userid, String useraddress);
}
