package com.classMetabus.web.Admin.service;


import com.classMetabus.web.Admin.domain.Instructor;
import com.classMetabus.web.Admin.domain.MailLog;
import com.classMetabus.web.Admin.domain.Student;
import com.classMetabus.web.Admin.repository.InstructorLoginRepository;
import com.classMetabus.web.Admin.repository.MailLogRepository;
import com.classMetabus.web.Admin.repository.StudentLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailService {

    private final StudentLoginRepository studentLoginRepository;
    private final InstructorLoginRepository instructorLoginRepository;
    private final MailLogRepository mailLogRepository;

    private final JavaMailSender javaMailSender;

    public List<String> sendMail(Integer instructorId, String context){
        List<Student> studentList = studentLoginRepository.findAll();
        Optional<Instructor> instructor = instructorLoginRepository.findById(instructorId);

        ArrayList<String> toUserList = new ArrayList<>();

        for(Student student : studentList){
            toUserList.add(student.getEmail());
        }

        int toUserSize = toUserList.size();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(instructor.get().getEmail());

        simpleMailMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));

        simpleMailMessage.setSubject("[메타버스 대시보드]: 테스트 안내");

        simpleMailMessage.setText(context);


        javaMailSender.send(simpleMailMessage);

        for(Student contact : studentList){

            MailLog mailLog = MailLog.builder()
                    .send(true)
                    .sender(instructor.get().getName())
                    .receiver(contact.getName())
                    .text(context)
                    .build();
            mailLogRepository.save(mailLog);


        }
        return toUserList;
    }

    /*
    // 미사용
    @Transactional(readOnly = true)
    public Page<MailLog> findAll(Pageable pageable){
        Page<MailLog> mailLogList = mailLogRepository.findAll(pageable);
        return mailLogList;
    }*/

}
