package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.domain.Instructor;
import com.classMetabus.web.Admin.domain.Lecture;
import com.classMetabus.web.Admin.domain.Quiz;
import com.classMetabus.web.Admin.domain.StudentList;
import com.classMetabus.web.Admin.dto.content.ContentListResponse;
import com.classMetabus.web.Admin.dto.quiz.*;
import com.classMetabus.web.Admin.dto.user.InstructorIdcntProjectionInterface;
import com.classMetabus.web.Admin.repository.LectureRepository;
import com.classMetabus.web.Admin.repository.QuizRepository;
import com.classMetabus.web.Admin.repository.StudentListRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizDaoService {
    private final QuizRepository quizRepository;
    private final LectureRepository lectureRepository;
    private final StudentListRepository studentListRepository;

    @Autowired
    private  ObjectMapper mapper;
    @Transactional
    public boolean create(CreateQuizRequest request)  {
        try {
            String json = mapper.writeValueAsString(request.getData()) ;
            Instructor instructor = Instructor.builder().id(request.getInstructorId()).build();
            Quiz quiz = Quiz.builder()
                    .name(request.getName())
                    .data(json)
                    .instructor(instructor)
                    .build();
            quizRepository.save(quiz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Transactional
    public boolean deleteById(Integer id){
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Lecture> lecture = lectureRepository.findByQuizId(id);
        if(lecture.size() > 0) return false;

        Quiz deletedQuiz = quiz.get();
        quizRepository.delete(deletedQuiz);
        return  true;
    }

    @Transactional
    public List<QuizListResponse> listByInstructorId (Integer instructorId){
        List<Quiz> ops = quizRepository.findByInstructor_Id(instructorId);

        return ops.stream().map(QuizListResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public List<QuizList> infoByQuizId (Integer quizid){
        Optional<Quiz> ops = quizRepository.findById(quizid);
        List<QuizList> list = new ArrayList<>();

        try {
            String data = ops.get().getData();
            List<QuizList> quiz = Arrays.asList(mapper.readValue(data,QuizList[].class));

            int i =0;
            for(QuizList quizLists : quiz) {
                quiz.get(i).setAnswerYN(null);
                i++;
            }

            quiz.get(0).setAnswerYN(null);
            list = quiz;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    @Transactional
    public boolean updateById(UpdateQuizRequest request){

        Optional<Quiz> quiz = quizRepository.findById(request.getId());
        if(quiz.isEmpty()) return false;
        try {
            String json = mapper.writeValueAsString(request.getData()) ;

            Quiz updatedQuiz = quiz.get();
            updatedQuiz.setName(request.getName());
            updatedQuiz.setData(json);
            quizRepository.save(updatedQuiz);
            return  true;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public ScoringResponse scoring(ScoringRequest request){
        try {
            ScoringResponse response = new ScoringResponse();
            Optional<Quiz> ops = quizRepository.findById(request.getQuizId());
            if (ops.isEmpty()) {response.setSuccess(false); response.setQuizScore(0); return response;}

            // 학생이 제출한 정답 저장
            String json = mapper.writeValueAsString(request.getAnswers()) ;
            Optional<StudentList> studentList = studentListRepository.findById(request.getStudentListId());
            StudentList updatedStudentList = studentList.get();
            updatedStudentList.setQuizAnswer(json);
            studentListRepository.save(updatedStudentList);

            // DB에서 해당 문제 정답 불러오기
            String data = ops.get().getData();
            List<QuizList> quiz = Arrays.asList(mapper.readValue(data,QuizList[].class));

            // 채점
            Integer score = 0;
            Integer i;
            Boolean PNF = false;
            for(i = 0; i < quiz.size(); i++){
                List<Boolean> answer = quiz.get(i).getAnswerYN();
                List<Boolean> stuAnswer = request.getAnswers().get(i).getAnswer();

                for(int j = 0; j< quiz.get(i).getAnswerYN().size() ; j++) {
                    PNF = false;
                    if(answer.get(j).equals(stuAnswer.get(j))){
                        PNF = true;
                    }
                    else{
                        break;
                    }
                }
                if(PNF.equals(true)){
                    score += quiz.get(i).getScore();
                }
            }
            // 채점 결과 저장
            updatedStudentList.setQuizScore(score);
            studentListRepository.save(updatedStudentList);
            response.setSuccess(true);
            response.setQuizScore(score);

            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}