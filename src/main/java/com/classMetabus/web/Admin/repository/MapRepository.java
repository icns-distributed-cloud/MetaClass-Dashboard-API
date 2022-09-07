package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface MapRepository extends JpaRepository<Map,Integer> {
    Optional<Map> findByName(String name);
    Optional<Map> findByNameAndIdIsNot(@PathVariable("name")String name, @PathVariable("id")Integer id);
    Optional<Map> findByIdAndDeletedEquals(Integer id, Boolean deleted);
    List<Map> findByInstructorIdAndDeletedEqualsOrderByCreatedTime(@PathVariable("id") Integer id, Boolean deleted);
}
