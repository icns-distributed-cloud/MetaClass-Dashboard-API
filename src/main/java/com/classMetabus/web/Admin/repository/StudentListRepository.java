package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.StudentList;
import com.classMetabus.web.Admin.dto.user.AbsentClassInfoProjectionInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface StudentListRepository extends JpaRepository<StudentList,Integer> {
    List<StudentList> findByStudentId (Integer studentId);
    Optional<StudentList> findByLectureId(Integer lectureId);
    Optional<StudentList> findIdByStudent_IdAndLecture_id (@PathVariable("studentId")Integer studentId, @PathVariable("lectureId")Integer lectureId);
    @Query(value = "select a.student.id as id, a.student.name as name, a.student.loginId as loginId, a.student.email as email, a.student.department.name as department, nullif(b.participationLevel,0) as participationLevel,b.absentDateTime as absentDateTime, nullif(b.late,false)  as lateYN " +
            "from StudentList as a " +
            "left join AbsentClassInfo as b on a.id = b.studentList.id " +
            "where a.lecture.id = :lectureId and a.student.deleted = false")
    List<AbsentClassInfoProjectionInterface> CheckStudentByLecture(Integer lectureId);

    List<StudentList> findByLecture_Instructor_Id(Integer instructorId);
}
