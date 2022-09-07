package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.Lecture;
import com.classMetabus.web.Admin.dto.user.InstructorIdcntProjectionInterface;
import com.classMetabus.web.Admin.dto.user.countOfStudentProjectionInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Integer> {
    Optional<Lecture> findByName(String name);
    Optional<Lecture> findById(Integer id);
    Optional<Lecture> findByNameAndIdIsNot(@PathVariable("name")String name,@PathVariable("id")Integer id);
    Optional<Lecture> findByIdAndDeletedEquals(Integer id,Boolean deleted);
    List<Lecture> findByContentId(Integer contentId);
    List<Lecture> findByMapId(Integer mapId);
    List<Lecture> findByQuizId(Integer quizId);
    Optional<Lecture> findMap_MaxUserById(Integer id);
    @Query(value = "Select a.id as id, a.name as name, a.startTime as startTime, a.endTime as endTime,c.id as contentId, c.name as contentName, a.instructor.id as instructorId, a.instructor.name as instructorName,a.map.id as mapId,a.map.maxUser as mapMaxUser, a.map.type as mapType ,a.map.name as mapName,d.id as quizId, d.name as quizName " +
            "from Lecture a " +
            "left join Content c on a.content.id = c.id " +
            "left join Quiz d on a.quiz.id = d.id " +
            "where a.deleted = false and a.instructor.id = :instructorId " +
            "and a.startTime between :start and :end ")
    List<InstructorIdcntProjectionInterface> findAllCntByInstructorId(LocalDateTime start, LocalDateTime end, Integer instructorId);

    @Query(value = "Select a.lecture.id as id,a.lecture.name as name, a.quizScore as quizScore, a.lecture.startTime as startTime, a.lecture.endTime as endTime, c.id as contentId ,c.name as contentName, a.lecture.instructor.id as instructorId, a.lecture.instructor.name as instructorName , a.lecture.map.id as mapId,a.lecture.map.maxUser as mapMaxUser, a.lecture.map.type as mapType ,a.lecture.map.name as mapName,d.id as quizId, d.name as quizName " +
            ", e.participationLevel as participationLevel " +
            ", e.absentDateTime as absentTime " +
            "from StudentList a " +
            "left join Content c on a.lecture.content.id = c.id " +
            "left join Quiz d on a.lecture.quiz.id = d.id " +
            "left join AbsentClassInfo e on a.id = e.studentList.id "+
            "where a.lecture.deleted = false and a.student.id = :studentId " +
            "and a.lecture.startTime between :start and :end ")// +
            //"group by a.lecture.id ")
    List<InstructorIdcntProjectionInterface> findAllCntByStudentId(LocalDateTime start, LocalDateTime end, Integer studentId);

    @Query(value = "Select a.id as id, a.name as name, a.startTime as startTime, a.endTime as endTime, b.id as contentId, b.name as contentName, a.instructor.id as instructorId, a.instructor.name as instructorName,a.map.id as mapId,a.map.maxUser as mapMaxUser, a.map.type as mapType ,a.map.name as mapName " +
            "from Lecture a " +
            "left join Content b " +
            "on a.content.id = b.id " +
            "where a.deleted = false and a.id not in (select e.lecture.id from StudentList as e where e.student.id = :studentId) " +
            "and a.startTime between :start and :end " +
            "group by a.id")
    List<InstructorIdcntProjectionInterface> findByStudentIdCodeNot(LocalDateTime start, LocalDateTime end, Integer studentId);

    @Query(value ="select max(a.lecture.id) as lectureId, nullif(count(a.id),0) as countStudent " +
            "from StudentList a " +
            "where a.lecture.deleted = false " +
            "and a.lecture.startTime between :start and :end " +
            "group by a.lecture.id")
    List<countOfStudentProjectionInterface> CountStudentByLectureId(LocalDateTime start, LocalDateTime end);

    List<Lecture> findByIdIsNotInAndInstructor_IdAndDeletedEqualsAndStartTimeIsAfter (@PathVariable("id")List<Integer> lectureIds, @PathVariable("instructorId")Integer instructorId, Boolean deleted, LocalDateTime localDateTime);
}