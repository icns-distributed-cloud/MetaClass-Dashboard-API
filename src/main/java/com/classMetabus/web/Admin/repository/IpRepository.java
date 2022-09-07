package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.IP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IpRepository extends JpaRepository<IP,Integer> {
    Optional<IP> findByAddressOrName(@Param(value="address")String address,@Param(value="name")String name);
    List<IP> findByDeletedEquals(@Param(value = "deleted")Boolean deleted);
    Optional<IP> findMaxUserById(Integer id);
}
