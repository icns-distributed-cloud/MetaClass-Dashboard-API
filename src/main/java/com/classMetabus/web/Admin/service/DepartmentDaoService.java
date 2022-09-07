package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.advice.DuplicateException;
import com.classMetabus.web.Admin.domain.Department;
import com.classMetabus.web.Admin.domain.Student;
import com.classMetabus.web.Admin.repository.DepartmentRepository;
import com.classMetabus.web.Admin.repository.StudentLoginRepository;
import com.classMetabus.web.Admin.dto.department.CreateDepartmentRequest;
import com.classMetabus.web.Admin.dto.department.DeleteDepartmentRequest;
import com.classMetabus.web.Admin.dto.department.DepartmentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentDaoService {
    private final DepartmentRepository departmentRepository;
    private final StudentLoginRepository studentLoginRepository;

    @Transactional
    public boolean create(CreateDepartmentRequest request){
        departmentRepository.findByName(request.getName()).ifPresent(m->{
            String message = "이미 등록된 부서입니다.";
            throw new DuplicateException(message);
        });

        Department department = Department.builder()
                .name(request.getName())
                .deleted(false)
                .build();

        departmentRepository.save(department);
        return true;
    }

    @Transactional
    public List<DepartmentListResponse> departmentList(){
        return departmentRepository.findByDeletedEquals(false).stream().map(DepartmentListResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public boolean deleteById(DeleteDepartmentRequest request){
        Optional<Department> department = departmentRepository.findById(request.getId());
        List<Student> student = studentLoginRepository.findByDepartmentId(request.getId());
        if(department.get().getDeleted() == true || student.size() > 0)
            return false;

        Department deletedDepartment = department.get();
        deletedDepartment.setDeleted(true);
        deletedDepartment.setName(department.get().getName()+"_"+ LocalDateTime.now());
        departmentRepository.save(deletedDepartment);
        return true;
    }
}
