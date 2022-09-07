package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentLoginRepository extends JpaRepository<Student,Integer> {
        List<Student> findByDeletedEqualsOrderByJoinDate(Boolean deleted);
        Optional<Student> findByLoginId(String loginId);
        List<Student> findByDepartmentId(Integer departmentId);
        List<Student> findByDepartmentIdAndDeletedEqualsOrderByJoinDate(Integer departmentId,Boolean deleted);
}
