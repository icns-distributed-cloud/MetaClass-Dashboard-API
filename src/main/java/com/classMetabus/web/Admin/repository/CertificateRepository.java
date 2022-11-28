package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,Integer> {
    Optional<Certificate> findByName(String name);
    List<Certificate> findByInstructor_IdAndDeletedEquals(@PathVariable("instructorId") Integer id, Boolean deleted);
}
