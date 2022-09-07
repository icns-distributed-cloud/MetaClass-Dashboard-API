package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Optional<Admin> findByLoginIdAndDeletedEquals(@Param(value="loginId")String loginid, Boolean deleted);
}
