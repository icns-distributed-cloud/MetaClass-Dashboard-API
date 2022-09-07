package com.classMetabus.web.Admin.repository;

import com.classMetabus.web.Admin.domain.AbsentClassInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface AbsentClassInfoRepository extends JpaRepository<AbsentClassInfo,Integer> {
    Optional<AbsentClassInfo> findByStudentListId (Integer studentListId);
    Optional<AbsentClassInfo> findByStudentList_Lecture_IdAndStudentList_Student_IdAndStudentList_Lecture_DeletedEqualsAndStudentList_Lecture_DeletedEquals(@PathVariable("lectureId")Integer lectureId, @PathVariable("studentId")Integer studentId, Boolean deletedLecture, Boolean deletedStudent);
}
