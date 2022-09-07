package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentsRepository extends JpaRepository<Content,Integer> {
    Optional<Content> findByName(String name);
    List<Content> findByInstructor_IdAndDeletedEquals(@PathVariable("instructorId") Integer id,Boolean deleted);
}
