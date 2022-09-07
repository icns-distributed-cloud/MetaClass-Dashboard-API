package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Integer>{
    List<Quiz> findByInstructor_Id(@PathVariable("instructorId") Integer id);
}
