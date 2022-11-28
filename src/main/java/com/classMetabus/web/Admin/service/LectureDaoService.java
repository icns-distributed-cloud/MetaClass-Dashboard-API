package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.domain.*;
import com.classMetabus.web.Admin.dto.absentParticipation.StudentLectureRequest;
import com.classMetabus.web.Admin.dto.lecture.*;
import com.classMetabus.web.Admin.dto.user.*;
import com.classMetabus.web.Admin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class LectureDaoService {
    private final LectureRepository lectureRepository;
    private final InstructorLoginRepository instructorLoginRepository;
    private final MapRepository mapRepository;
    private final StudentListRepository studentListRepository;
    private final AbsentClassInfoRepository absentClassInfoRepository;
    @Transactional
    public Integer create(CreateLectureRequest request){
        Optional<Lecture> ops = lectureRepository.findByName(request.getName());
        Optional<Instructor> check4instructorId = instructorLoginRepository.findById(request.getInstructorId());
        Optional<Map> check4mapId = mapRepository.findById(request.getMapId());
        if(ops.isPresent()){return 2;}
        if(check4instructorId.isEmpty() || check4mapId.isEmpty()){
            return 0;
        }

        Instructor instructor = Instructor.builder().id(request.getInstructorId()).build();

        Map map = Map.builder().id(request.getMapId()).build();

        Lecture lecture = Lecture.builder()
                .name(request.getName())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .deleted(false)
                .isAutoClass(request.getIsAutoClass())
                .map(map)
                .instructor(instructor)
                .build();

        if(!(request.getContentId()==null)){
            Content content = Content.builder().id(request.getContentId()).build();
            lecture.setContent(content);
        }
        if(!(request.getQuizId()==null)){
            Quiz quiz = Quiz.builder().id(request.getQuizId()).build();
            lecture.setQuiz(quiz);
        }

        lectureRepository.save(lecture);

        int i = 0;
        List<StudentList> lists = new ArrayList<>();

        Optional<Lecture> findlectureId = lectureRepository.findByName(request.getName());
        for(CreateLectureRequest.StudentIdList list : request.getStulist()) {
            if(!request.getStulist().isEmpty()){

                Student student = Student.builder()
                        .id(request.getStulist().get(i).studentId)
                        .build();

                lecture.setId(findlectureId.get().getId());

                StudentList studentList= StudentList.builder()
                        .student(student)
                        .lecture(lecture)
                        .totalScore(0)
                        .build();

                lists.add(studentList);
            }
            i++;
        }
        // 저장
        studentListRepository.saveAll(lists);
        return 1;
    }
    @Transactional
    public boolean updateByclassRoomName(UpdateLectureRequest request){
        Optional<Lecture> info4masterId = lectureRepository.findByIdAndDeletedEquals(request.getId(),false);
        Optional<Lecture> info4classname = lectureRepository.findByNameAndIdIsNot(request.getName(),request.getId());
        Optional<Instructor> check4id = instructorLoginRepository.findByIdAndDeletedEquals(request.getInstructorId(),false);

        if(info4masterId.isEmpty() ||
                info4classname.isPresent() ||
                check4id.isEmpty())
            return false;

        Instructor instructor = Instructor.builder()
                .id(request.getInstructorId())
                .build();

        Map map = Map.builder()
                .id(request.getMapId())
                .build();

        Lecture updatedInfo = info4masterId.get();
        updatedInfo.setIsAutoClass(request.getIsAutoClass());
        updatedInfo.setName(request.getName());
        updatedInfo.setInstructor(instructor);
        updatedInfo.setStartTime(request.getStartTime());
        updatedInfo.setEndTime(request.getEndTime());

        updatedInfo.setMap(map);

        if(request.getContentId() != null){
            Content content = Content.builder()
                    .id(request.getContentId())
                    .build();
            updatedInfo.setContent(content);
        }
        else{ updatedInfo.setContent(null); }//임시

        if(request.getQuizId() != null){
            Quiz quiz = Quiz.builder().id(request.getQuizId()).build();

            updatedInfo.setQuiz(quiz);
        }
        else{ updatedInfo.setQuiz(null); }//임시
        lectureRepository.save(updatedInfo);
        return true;
    }
    @Transactional
    public boolean deleteById(DeleteLectureRequest request){
        Optional<Lecture> lecture = lectureRepository.findByIdAndDeletedEquals(request.getId(),false);
        Optional<StudentList> studentList = studentListRepository.findByLectureId(request.getId());

        if(studentList.isPresent())
            return false;

        Lecture deletedInfo = lecture.get();
        deletedInfo.setDeleted(true);
        deletedInfo.setName(lecture.get().getName()+"_"+ LocalDateTime.now());
        lectureRepository.save(deletedInfo);
        return true;
    }
    @Transactional
    public ArrayList<LectureListResponse> listByInstructorNDate(LectureListRequest request){
        LocalDateTime startDatetime = LocalDateTime.of(request.getStartDate(),LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(request.getEndDate(), LocalTime.of(23,59,59));

        ArrayList<LectureListResponse> lists = new ArrayList<>();
        List<InstructorIdcntProjectionInterface> lectures
                = lectureRepository.findAllCntByInstructorId(startDatetime,endDatetime,request.getInstructorId());
        List<countOfStudentProjectionInterface> cnts = lectureRepository.CountStudentByLectureId(startDatetime,endDatetime);
        List<StudentList> studentLists = studentListRepository.findByLecture_Instructor_Id(request.getInstructorId());
        int i = 0;
        for(InstructorIdcntProjectionInterface list : lectures){
            LectureListResponse response = new LectureListResponse();
            response.setId(lectures.get(i).getId());
            response.setName(lectures.get(i).getName());
            response.setStartTime(lectures.get(i).getStartTime());
            response.setEndTime(lectures.get(i).getEndTime());
            response.setContentId(lectures.get(i).getContentId());
            response.setContentName(lectures.get(i).getContentName());
            response.setInstructorId(lectures.get(i).getInstructorId());
            response.setInstructorName(lectures.get(i).getInstructorName());
            response.setMapId(lectures.get(i).getMapId());
            response.setMapMaxUser(lectures.get(i).getMapMaxUser());
            response.setMapType(lectures.get(i).getMapType());
            response.setMapName(lectures.get(i).getMapName());
            response.setQuizId(lectures.get(i).getQuizId());
            response.setQuizName(lectures.get(i).getQuizName());
            response.setIsAutoClass(lectures.get(i).getIsAutoClass());
            //response.setCountUser(lectures.get(i).getCountUser());

            int j = 0;
            for(countOfStudentProjectionInterface count: cnts){
                if(lectures.get(i).getId().equals(cnts.get(j).getLectureId())){
                    response.setCountUser(cnts.get(j).getCountStudent());
                    break;
                }
                else{
                    response.setCountUser(0);
                }
                j++;
            }

            int k = 0;
            List<Students> studentslist = new ArrayList<>();
            for(StudentList studentList : studentLists){
                if(lectures.get(i).getId().equals(studentLists.get(k).getLecture().getId())){
                    Students students = Students.builder()
                            .LoginId(studentLists.get(k).getStudent().getLoginId())
                            .studentName(studentLists.get(k).getStudent().getName())
                            .studentId(studentLists.get(k).getStudent().getId())
                            .build();

                    studentslist.add(students);
                }

                k++;
            }
            response.setStudents(studentslist);
            lists.add(response);
            i++;
        }
        return lists;
    }
    @Transactional
    public ArrayList<CheckStudentByLectureResponse> checkStudentByLecture(StudentLectureRequest request) {
        ArrayList<CheckStudentByLectureResponse> students = new ArrayList<>();
        List<AbsentClassInfoProjectionInterface> studentList = studentListRepository.CheckStudentByLecture(request.getLectureId());

        int i = 0;
        for (AbsentClassInfoProjectionInterface list : studentList) {
            {
                CheckStudentByLectureResponse student = new CheckStudentByLectureResponse();
                student.setId(studentList.get(i).getId());
                student.setName(studentList.get(i).getName());
                student.setLoginId(studentList.get(i).getLoginId());
                student.setEmail(studentList.get(i).getEmail());
                student.setDepartment(studentList.get(i).getDepartment());

                LocalDateTime ldt = studentList.get(i).getAbsentDateTime();
                if(ldt==null)
                {
                    student.setAbsentYN(false);
                    student.setLateYN(false);
                    student.setParticipationLevel(0);
                }
                else {
                    student.setParticipationLevel(studentList.get(i).getParticipationLevel());
                    student.setAbsentYN(true);
                    student.setLateYN(studentList.get(i).getLateYN());

                }
                students.add(student);
                i++;
            }
        }
        return students;
    }
    @Transactional
    public ArrayList<StudentLectureListResponse> listByStudentNDate(StudentLecturListRequest request){
        LocalDateTime startDatetime = LocalDateTime.of(request.getStartDate(),LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(request.getEndDate(), LocalTime.of(23,59,59));

        ArrayList<StudentLectureListResponse> lists = new ArrayList<>();
        List<InstructorIdcntProjectionInterface> lectures
                = lectureRepository.findAllCntByStudentId(startDatetime,endDatetime,request.getStudentId());
        List<countOfStudentProjectionInterface> cnts = lectureRepository.CountStudentByLectureId(startDatetime,endDatetime);
        int i = 0;

        for(InstructorIdcntProjectionInterface list : lectures){

            StudentLectureListResponse response = new StudentLectureListResponse();
            response.setId(lectures.get(i).getId());
            response.setName(lectures.get(i).getName());
            response.setStartTime(lectures.get(i).getStartTime());
            response.setEndTime(lectures.get(i).getEndTime());
            response.setContentId(lectures.get(i).getContentId());
            response.setContentName(lectures.get(i).getContentName());
            response.setInstructorId(lectures.get(i).getInstructorId());
            response.setInstructorName(lectures.get(i).getInstructorName());
            response.setMapId(lectures.get(i).getMapId());
            response.setMapMaxUser(lectures.get(i).getMapMaxUser());
            response.setMapType(lectures.get(i).getMapType());
            response.setMapName(lectures.get(i).getMapName());
            response.setQuizId(lectures.get(i).getQuizId());
            response.setQuizName(lectures.get(i).getQuizName());
            response.setQuizScore(lectures.get(i).getQuizScore());
            response.setParticipationLevel(lectures.get(i).getParticipationLevel());
            response.setAbsentTime(lectures.get(i).getAbsentTime());
            response.setLateYN(Objects.isNull(lectures.get(i).getAbsentTime()));
            response.setIsAutoClass(lectures.get(i).getIsAutoClass());

            if(cnts.isEmpty()){
                response.setCountUser(0);
            }
            else{
                int j = 0;

                for(countOfStudentProjectionInterface count: cnts){

                    if(lectures.get(i).getId().equals(cnts.get(j).getLectureId())){
                        response.setCountUser(cnts.get(j).getCountStudent());
                        break;
                    }
                    j++;
                }
            }
            lists.add(response);

            i++;
        }
        return lists;
    }
    @Transactional
    public ArrayList<LectureListResponse> totalLectureList(StudentLecturListRequest request){
        LocalDateTime startDatetime = LocalDateTime.of(request.getStartDate(),LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(request.getEndDate(), LocalTime.of(23,59,59));

        ArrayList<LectureListResponse> lists = new ArrayList<>();
        List<InstructorIdcntProjectionInterface> lectures
                = lectureRepository.findByStudentIdCodeNot(startDatetime,endDatetime,request.getStudentId());
        List<countOfStudentProjectionInterface> cnts = lectureRepository.CountStudentByLectureId(startDatetime,endDatetime);

        int i = 0;
        for(InstructorIdcntProjectionInterface list : lectures){

            LectureListResponse response = new LectureListResponse();
            response.setId(lectures.get(i).getId());
            response.setName(lectures.get(i).getName());
            response.setStartTime(lectures.get(i).getStartTime());
            response.setEndTime(lectures.get(i).getEndTime());
            response.setContentId(lectures.get(i).getContentId());
            response.setContentName(lectures.get(i).getContentName());
            response.setInstructorId(lectures.get(i).getInstructorId());
            response.setInstructorName(lectures.get(i).getInstructorName());
            response.setMapMaxUser(lectures.get(i).getMapMaxUser());
            response.setMapType(lectures.get(i).getMapType());
            response.setMapName(lectures.get(i).getMapName());
            response.setCountUser(lectures.get(i).getCountUser());
            response.setIsAutoClass(lectures.get(i).getIsAutoClass());

            int j = 0;
            for(countOfStudentProjectionInterface count: cnts){
                if(lectures.get(i).getId().equals(cnts.get(j).getLectureId())){
                    response.setCountUser(cnts.get(j).getCountStudent());
                    break;
                }
                else{
                    response.setCountUser(0);
                }
                j++;
            }

            lists.add(response);
            i++;
        }
        return lists;
    }

    @Transactional
    public boolean deleteLectureByStudentId(StudentLectureRequest request){
        List<StudentList> info = studentListRepository.findByStudentId(request.getStudentId());
        int i = 0;
        for(StudentList list : info){
            if(info.get(i).getLecture().getId().equals(request.getLectureId())) {
                studentListRepository.delete(list);
                return true;}
            i++;
        }
        return false;
    }
    @Transactional
    public boolean joinLecture(StudentLectureRequest request){
/*        Optional<Student> studentCheck = studentLoginRepository.findById(request.getStudentId());
        Optional<Lecture> lectureCheck = lectureRepository.findById(request.getLectureId());*/
        Optional<StudentList> info = studentListRepository.findIdByStudent_IdAndLecture_id(request.getStudentId(),request.getLectureId());

/*        if(studentCheck.isEmpty() || lectureCheck.isEmpty() || info.isPresent())
            return false;*/
        if(info.isPresent())
            return false;

        Lecture lecture = Lecture.builder()
                .id(request.getLectureId())
                .build();

        Student student = Student.builder()
                .id(request.getStudentId())
                .build();

        StudentList studentList = StudentList.builder()
                .totalScore(0)
                .lecture(lecture)
                .student(student)
                .quizScore(0)
                .build();

        studentListRepository.save(studentList);
        return true;
    }
    @Transactional
    public GetParticipationInfoResponse getParticipationInfo(StudentLectureRequest request){
        Optional<AbsentClassInfo> info =   absentClassInfoRepository.findByStudentList_Lecture_IdAndStudentList_Student_IdAndStudentList_Lecture_DeletedEqualsAndStudentList_Lecture_DeletedEquals(request.getLectureId(),request.getStudentId(),false,false);

        GetParticipationInfoResponse response = new GetParticipationInfoResponse();

        response.setLectureName(info.get().getStudentList().getLecture().getName());
        response.setInstructorName(info.get().getStudentList().getLecture().getInstructor().getName());

        if(info.get().getAbsentDateTime()==null)
        {
            response.setAbsentTime(null);
            response.setLateYN(false);
            response.setParticipationLevel(0);
        }
        else{
            response.setAbsentTime(info.get().getAbsentDateTime());
            response.setParticipationLevel(info.get().getParticipationLevel());
            response.setLateYN(info.get().getLate());
        }

        return response;
    }
}
