package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.domain.Certificate;
import com.classMetabus.web.Admin.domain.Instructor;
import com.classMetabus.web.Admin.dto.certificate.*;
import com.classMetabus.web.Admin.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateDaoService {
    private final CertificateRepository certificateRepository;
    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    @Transactional
    public CreateCertificateResponse create(MultipartFile file, String directory){
        Optional<Certificate> opt = certificateRepository.findByName(file.getOriginalFilename());
        CreateCertificateResponse response = new CreateCertificateResponse();
        if(opt.isPresent())
            return response;
        Instructor instructor = new Instructor();
        instructor.setId(1);

        Certificate certificate = Certificate.builder()
                .name(file.getOriginalFilename())
                .deleted(false)
                .directory(directory.replace(uploadDir,""))
                .instructor(instructor)
                .build();
        certificateRepository.save(certificate);

        Optional<Certificate> afterSaved = certificateRepository.findByName(file.getOriginalFilename());
        if(afterSaved.isPresent())
        {
            response.setCertificateId(afterSaved.get().getId());
            response.setDirectory(afterSaved.get().getDirectory());
            return response;
        }
        return response;
    }
    @Transactional
    public boolean deleteById(DeleteCertificateRequest request){
        Optional<Certificate> certificate = certificateRepository.findById(request.getId());
        if(certificate.get().getDeleted() == true)
            return false;

        Certificate deletedCertificate = certificate.get();
        deletedCertificate.setDeleted(true);
        deletedCertificate.setName(certificate.get().getName()+ "_"+ LocalDateTime.now());
        certificateRepository.save(deletedCertificate);
        return true;
    }
    @Transactional
    public List<CertificateListResponse> certificateList(CertificateListRequest request) {
        return certificateRepository.findByInstructor_IdAndDeletedEquals(request.getInstructorId(),false).stream().map(CertificateListResponse::new).collect(Collectors.toList());
    }
    @Transactional
    public Boolean updateInstuructorIdByCertificateId(UpdateCetificateByIdRequest request){
        Optional<Certificate> ops = certificateRepository.findById(request.getCertificateId());
        if(ops.isEmpty() || request.getCertificateName().equals(""))
            return false;

        Certificate updatedCertificate = ops.get();
        Instructor instructor = new Instructor();
        instructor.setId(request.getInstructorId());
        updatedCertificate.setInstructor(instructor);
        updatedCertificate.setName(request.getCertificateName());

        certificateRepository.save(updatedCertificate);
        return true;
    }
}
