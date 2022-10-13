package com.classMetabus.web.Admin.service;

import com.classMetabus.web.Admin.domain.MessageLog;
import com.classMetabus.web.Admin.domain.Student;
import com.classMetabus.web.Admin.repository.MessageLogRepository;
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

@Service
@RequiredArgsConstructor
public class MessageService {

    private final StudentLoginRepository studentLoginRepository;
    private final MessageLogRepository messageLogRepository;

    @Value("${icns.app.coolsms.apikey}")
    private String apiKey;

    @Value("${icns.app.coolsms.apisecret}")
    private String apiSecret;

    @Value("${icns.app.coolsms.phone}")
    private String phone;

    public List<String> sendMessage(String name, String context){
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
    }

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
