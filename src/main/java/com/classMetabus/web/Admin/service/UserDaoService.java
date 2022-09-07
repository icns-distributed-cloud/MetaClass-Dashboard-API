package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.advice.DuplicateException;
import com.classMetabus.web.Admin.domain.Admin;
import com.classMetabus.web.Admin.domain.Department;
import com.classMetabus.web.Admin.domain.Instructor;
import com.classMetabus.web.Admin.domain.Student;
import com.classMetabus.web.Admin.dto.user.*;
import com.classMetabus.web.Admin.repository.AdminRepository;
import com.classMetabus.web.Admin.repository.InstructorLoginRepository;
import com.classMetabus.web.Admin.repository.StudentLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDaoService {
    private final StudentLoginRepository studentLoginRepository;
    private final InstructorLoginRepository instructorLoginRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public boolean register(RegisterRequest request){
        if(request.getUserMode().equals(0) ){
            instructorLoginRepository.findByLoginId(request.getLoginId()).ifPresent(m->{
                String message = "회원가입을 실패했습니다.";
                throw new DuplicateException(message);
            });

            Instructor instructor = Instructor.builder()
                            .loginId(request.getLoginId())
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(request.getPassword())
                            .phone(request.getPhone().replace("-","").replace(".",""))
                            .build();
            instructorLoginRepository.save(instructor);
            return true;
        }
        else if(request.getUserMode().equals(1)){
            studentLoginRepository.findByLoginId(request.getLoginId()).ifPresent(m->{
                String message = "회원가입을 실패했습니다.";
                throw new DuplicateException(message);
            });
            Department department =Department.builder()
                    .id(request.getDepartmentId())
                    .build();
            Student student = Student.builder()
                    .loginId(request.getLoginId())
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .phone(request.getPhone())
                    .password(request.getPassword())
                    .department(department)
                    .build();

            studentLoginRepository.save(student);
            return true;
        }
        return false;
    }
    @Transactional
    public LoginResponse findOne(LoginRequest request){
        LoginResponse response = new LoginResponse();
        if(request.getUserMode().equals(0)){
            Optional<Instructor> ins = instructorLoginRepository.findByLoginId(request.getLoginId());
            if(ins.isEmpty() || ins.get().getDeleted() == true)
                return response;
            if (ins.get().getPassword().equals(request.getPassword())){
                response.setId(ins.get().getId());
                response.setName(ins.get().getName());
                response.setLoginId(ins.get().getLoginId());
                response.setEmail(ins.get().getEmail());
                response.setPhone(ins.get().getPhone());
                return response;
            }
        }
        else if(request.getUserMode().equals(1)) {
            Optional<Student> stu = studentLoginRepository.findByLoginId(request.getLoginId());
            if(stu.isEmpty() || stu.get().getDeleted() == true )
                return response;
            else if (stu.get().getPassword().equals(request.getPassword())){
                response.setId(stu.get().getId());
                response.setName(stu.get().getName());
                response.setLoginId(stu.get().getLoginId());
                response.setEmail(stu.get().getEmail());
                response.setPhone(stu.get().getPhone());
                response.setDepartmentName(stu.get().getDepartment().getName());
                return response;
            }
        }
        else if(request.getUserMode().equals(2)){
            Optional<Admin> admin = adminRepository.findByLoginIdAndDeletedEquals(request.getLoginId(),false);
            if(admin.isEmpty())
                return response;
            if (admin.get().getPassword().equals(request.getPassword())){
                response.setId(admin.get().getId());
                response.setName(admin.get().getName());
                return response;
            }
        }
        return response;
    }
    @Transactional
    public List<AllStudentListResponse> findStudentList(){
        return studentLoginRepository.findByDeletedEqualsOrderByJoinDate(false).stream().map(AllStudentListResponse::new).collect(Collectors.toList());
    }
    @Transactional
    public boolean updateById(UpdateRequest request){
        if(request.getUserMode().equals(0)){
            Optional<Instructor> ins = instructorLoginRepository.findById(request.getId());
            if(ins.isEmpty() || ins.get().getDeleted()) return false;
            Instructor instructor = ins.get();
            instructor.setName(request.getName());
            instructor.setEmail(request.getEmail());
            instructor.setPhone(request.getPhone());
            instructor.setPassword(request.getPassword());

            instructorLoginRepository.save(instructor);
            return true;
        }
        else if(request.getUserMode().equals(1)){
            Optional<Student> stu = studentLoginRepository.findById(request.getId());
            if(stu.isEmpty() || stu.get().getDeleted()) return false;
            Department department = Department.builder().id(request.getDepartmentId()).build();
            Student student = stu.get();
            student.setName(request.getName());
            student.setEmail(request.getEmail());
            student.setPhone(request.getPhone());
            student.setPassword(request.getPassword());
            student.setDepartment(department);

            studentLoginRepository.save(student);
            return true;
        }
        return false;
    }
    @Transactional
    public boolean deleteById(DeleteRequest deleteRequest){
        if(deleteRequest.getUserMode().equals(0)){
            Optional<Instructor> ins = instructorLoginRepository.findByLoginId(deleteRequest.getLoginId());
            if (ins.isEmpty()
                    || (ins.get().getDeleted()))
                return false;

            Instructor deletedInstructor = ins.get();
            deletedInstructor.setDeleted(true);
            instructorLoginRepository.save(deletedInstructor);
            return true;
        }
        else if(deleteRequest.getUserMode().equals(1)) {
            Optional<Student> stu = studentLoginRepository.findByLoginId(deleteRequest.getLoginId());
            if (stu.isEmpty()
                    || (stu.get().getDeleted()))
                return false;

            Student deletedStu = stu.get();
            deletedStu.setDeleted(true);
            studentLoginRepository.save(deletedStu);
            return true;
        }
        return false;
    }
    @Transactional
    public List<AllInstructorListResponse> findLectureList(){
        return instructorLoginRepository.findByDeletedEqualsOrderByJoinDate(false).stream().map(AllInstructorListResponse::new).collect(Collectors.toList());
    }
    @Transactional
    public List<StudentListByDepartmentResponse> studentListByDepartment(StudentListByDepartmentRequest request){
        return studentLoginRepository.findByDepartmentIdAndDeletedEqualsOrderByJoinDate(request.getDepartmentId(),false).stream().map(StudentListByDepartmentResponse::new).collect(Collectors.toList());
    }
    //checkLoginId
    @Transactional
    public boolean checkLoginId (checkLoginIdRequest request){
        if(instructorLoginRepository.findByLoginId(request.getLoginId()).isPresent()){
            return false;
        }
        else if (studentLoginRepository.findByLoginId(request.getLoginId()).isPresent()){
            return false;
        }
        return true;
    }
}

