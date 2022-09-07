package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.Instructor;
import com.classMetabus.web.Admin.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstructorLoginRepository extends JpaRepository<Instructor, Integer> {
    Optional<Instructor> findByLoginId(String loginId);
    List<Instructor> findByDeletedEqualsOrderByJoinDate(Boolean deleted);
    Optional<Instructor> findByIdAndDeletedEquals(Integer id, Boolean deleted);
}
