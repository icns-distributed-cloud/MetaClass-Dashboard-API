package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.adminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<adminUser,Long>{
    Optional<adminUser> findByEmail(String email);
}
