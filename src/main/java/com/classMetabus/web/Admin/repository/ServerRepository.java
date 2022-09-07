package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server,Integer> {
    //Optional<Server> findByIp_Id(@Param(value="ipId")Integer ipId);
    //Optional<Server> findByLectureIdAndDeletedEquals(Integer lectureId,Boolean deleted);
    Optional<Server> findTop1ById(Integer id);
    Optional<Server> findByLectureIdAndLecture_DeletedEquals(@Param(value="lectureId")Integer lectureId,Boolean deleted);
    Optional<Server> findByIp_IdAndIp_DeletedEquals(@Param(value="ipId")Integer ipId, Boolean deleted);
    List<Server> findByLecture_Instructor_Id(@Param(value="id")Integer id);

    @Query(value = "select sum(a.lecture.map.maxUser) from Server a where a.ip.id = :ipId")
    Integer getSumTotalUser(Integer ipId);

    @Query(value = "select distinct a.lecture.id as lectureId from Server as a")
    List<Integer> findByDistinctServerList();
}
