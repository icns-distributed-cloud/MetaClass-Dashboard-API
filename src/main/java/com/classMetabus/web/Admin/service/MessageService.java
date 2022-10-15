package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.domain.MessageLog;
import com.classMetabus.web.Admin.domain.Student;
import com.classMetabus.web.Admin.domain.StudentList;
import com.classMetabus.web.Admin.dto.mail.SendMailRequest;
import com.classMetabus.web.Admin.repository.MessageLogRepository;
import com.classMetabus.web.Admin.repository.StudentListRepository;
import com.classMetabus.web.Admin.repository.StudentLoginRepository;
import lombok.RequiredArgsConstructor;

import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import net.nurigo.java_sdk.api.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final StudentLoginRepository studentLoginRepository;
    private final StudentListRepository studentListRepository;
    private final MessageLogRepository messageLogRepository;

    @Value("${icns.app.coolsms.apikey}")
    private String apiKey;

    @Value("${icns.app.coolsms.apisecret}")
    private String apiSecret;

    @Value("${icns.app.coolsms.phone}")
    private String phone;

//    public Boolean sendMessage(SendMessageRequest request){
//        Optional<StudentList> stuList = studentListRepository.findIdByStudent_IdAndLecture_id(request.getStudentId(), request.getLectureId());
//
//        if (stuList.isPresent()){
//            String toPhone = stuList.get().getStudent().getPhone();
//            String studentName = stuList.get().getStudent().getName();
//            String lectureName = stuList.get().getLecture().getName();
//            Message coolsms = new Message(apiKey, apiSecret);
//
//            HashMap<String,String> params = new HashMap<String,String>();
//            params.put("to",toPhone);
//            params.put("from",phone); // 사전에 사이트에서 번호를 인증하고 등록해야함
//            params.put("type","SMS");
//            params.put("text",studentName+"님 '"+lectureName+"' 과목의 수강신청이 완료 되었습니다."); // 메세지 내용
//            //params.put("app_version","test app1.2");
//
//            System.out.println("전송완료");
////            try{
////                JSONObject obj = (JSONObject) coolsms.send(params);
////                System.out.println(obj.toString()); // 전송 결과 출력
////            }catch (CoolsmsException e){
////                System.out.println(e.getMessage());
////                System.out.println(e.getCode());
////            }
//            return true;
//        }else {return false;}
//    }
    /*public List<String> sendMessage(String name, String context){
        List<Student> studentList = studentLoginRepository.findAll();
        List<String> phoneList = new ArrayList<>();

        for(Student student : studentList){
            String api_key = apiKey;
            String api_secret = apiSecret;
            Message coolsms = new Message(api_key, api_secret);

            phoneList.add(student.getPhone());

            HashMap<String, String> params = new HashMap<String, String>();

            params.put("to", student.getPhone());
            params.put("from", phone);
            params.put("type", "SMS");
            params.put("text", context);
            params.put("app_version", "test app 1.2");

            try {
                //JSONObject obj = (JSONObject) coolsms.send(params);

                JSONObject result = coolsms.send(params);

                System.out.println(result.toString());
                MessageLog messageLog = MessageLog.builder()
                        .send(true)
                        .sender(name)
                        .receiver(student.getName())
                        .text(context)
                        .build();
                messageLogRepository.save(messageLog);

            } catch (CoolsmsException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getCode());
                MessageLog messageLog = MessageLog.builder()
                        .send(false)
                        .sender(name)
                        .receiver(student.getName())
                        .text(context)
                        .build();
                messageLogRepository.save(messageLog);
            }
        }


        return phoneList;
    }*/

    /*
    //미사용
    @Transactional(readOnly = true)
    public Page<MessageLog> findAll(Pageable pageable){
        Page<MessageLog> messageLogList = messageLogRepository.findAll(pageable);
        return messageLogList;
    }
   @Transactional(readOnly = true)
    public List<MessageLog> findAll(){
        List<MessageLog> messageLogList = messageLogRepository.findAll();
        return messageLogList;
    }*/
}
