package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.advice.DuplicateException;
import com.classMetabus.web.Admin.domain.AbsentClassInfo;
import com.classMetabus.web.Admin.domain.StudentList;
import com.classMetabus.web.Admin.repository.AbsentClassInfoRepository;
import com.classMetabus.web.Admin.repository.LectureRepository;
import com.classMetabus.web.Admin.repository.StudentListRepository;
import com.classMetabus.web.Admin.dto.absentParticipation.GetAbsentLectureRequest;
import com.classMetabus.web.Admin.dto.absentParticipation.GetParticipationLevelRequest;
import com.classMetabus.web.Admin.dto.absentParticipation.StudentLectureRequest;
import com.classMetabus.web.Admin.dto.absentParticipation.JoinLectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipationDaoService {
    private final StudentListRepository studentListRepository;
    private final AbsentClassInfoRepository absentClassInfoRepository;
    private final LectureRepository lectureRepository;
    @Transactional
    public JoinLectureResponse findByStudentListId(StudentLectureRequest request){
        JoinLectureResponse response = JoinLectureResponse.builder()
                .quizId(lectureRepository.findById(request.getLectureId()).get().getQuiz().getId())
                .studentListId(studentListRepository.findIdByStudent_IdAndLecture_id(request.getStudentId(),request.getLectureId()).get().getId())
                .build();
        return response;
    }

    @Transactional
    public Boolean getAbsentLecture(GetAbsentLectureRequest request){
        absentClassInfoRepository.findByStudentListId(request.getStudentListId()).ifPresent(m->{
            String message = "학생 출결 등록을 실패했습니다.";
            throw new DuplicateException(message);
        });

        StudentList studentList = StudentList.builder()
                .id(request.getStudentListId())
                .build();
        AbsentClassInfo absentClassInfo = AbsentClassInfo.builder()
                .absentDateTime(request.getAbsentDateTime())
                .participationLevel(0)
                .late(request.getLate())
                .studentList(studentList)
                .build();

        absentClassInfoRepository.save(absentClassInfo);
        return true;
    }
    @Transactional
    public Boolean updateParticipationLevel(GetParticipationLevelRequest request){
        Optional<AbsentClassInfo> info =  absentClassInfoRepository.findByStudentListId(request.getStudentListId());
        if(info.isEmpty()){
            return false;
        }
        AbsentClassInfo absentClassInfo = info.get();
        absentClassInfo.setParticipationLevel(request.getParticipationLevel());

        absentClassInfoRepository.save(absentClassInfo);
        return true;
    }
}
